package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import gov.usgs.ansseqmsg.Action;
import gov.usgs.ansseqmsg.Comment;
import gov.usgs.ansseqmsg.EQMessage;
import gov.usgs.ansseqmsg.Event;
import gov.usgs.ansseqmsg.EventAction;
import gov.usgs.ansseqmsg.EventScope;
import gov.usgs.ansseqmsg.EventType;
import gov.usgs.ansseqmsg.EventUsage;
import gov.usgs.ansseqmsg.Magnitude;
import gov.usgs.ansseqmsg.Method;
import gov.usgs.ansseqmsg.Origin;
import gov.usgs.ansseqmsg.OriginStatus;
import gov.usgs.ansseqmsg.ProductLink;
import gov.usgs.earthquake.cube.CubeAddon;
import gov.usgs.earthquake.cube.CubeDelete;
import gov.usgs.earthquake.cube.CubeEvent;
import gov.usgs.earthquake.cube.CubeMessage;

/**
 * Convert from EQMessage to CubeMessage.
 * 
 * @author jmfee
 */
public class EQMessageToCubeConverter {

	/**
	 * Convert an EQMessage to a CubeMessage.
	 * 
	 * @param message
	 *            message to convert.
	 * @return CubeMessage corresponding to EQMessage.
	 */
	public CubeMessage convertEQMessage(final EQMessage message) {
		List<Event> events = message.getEvent();
		Event event = events.get(0);
		EventAction action = event.getAction();

		if (action == null || action == EventAction.UPDATE) {
			if (event.getOrigin().size() == 0) {
				return convertEQMessageToCubeAddon(message, event);
			} else {
				return convertEQMessageToCubeEvent(message, event);
			}
		} else if (action == EventAction.DELETE) {
			return convertEQMessageToCubeDelete(message, event);
		}

		return null;
	}

	/**
	 * Convert an EQMessage that represents an event delete to a CubeDelete.
	 * 
	 * @param message
	 *            EQMessage containing deleted event.
	 * @param event
	 *            deleted event to convert.
	 * @return CubeDelete corresponding to EQMessage.
	 */
	public CubeAddon convertEQMessageToCubeAddon(final EQMessage message,
			final Event event) {
		boolean found = false;

		CubeAddon cubeAddon = new CubeAddon();
		cubeAddon.setSent(message.getSent());
		cubeAddon.setSource(event.getDataSource().toLowerCase());
		cubeAddon.setCode(event.getEventID().toLowerCase());

		Iterator<ProductLink> iter = event.getProductLink().iterator();
		while (iter.hasNext()) {
			found = true;

			ProductLink productLink = iter.next();
			cubeAddon.setVersion(productLink.getVersion());
			cubeAddon.setAddonText(productLink.getNote());
			cubeAddon.setAddonUrl(productLink.getLink());
			cubeAddon.setAddonType(productLink.getCode());
			if (productLink.getAction().equals(Action.DELETE)) {
				cubeAddon.setDeleted();
			}
		}

		if (found) {
			return cubeAddon;
		} else {
			return null;
		}
	}

	/**
	 * Convert an EQMessage that represents an event delete to a CubeDelete.
	 * 
	 * @param message
	 *            EQMessage containing deleted event.
	 * @param event
	 *            deleted event to convert.
	 * @return CubeDelete corresponding to EQMessage.
	 */
	public CubeDelete convertEQMessageToCubeDelete(final EQMessage message,
			final Event event) {
		if (event.getAction() != EventAction.DELETE) {
			return null;
		}

		CubeDelete cubeDelete = new CubeDelete();

		cubeDelete.setSent(message.getSent());
		cubeDelete.setSource(event.getDataSource().toLowerCase());
		cubeDelete.setCode(event.getEventID().toLowerCase());
		cubeDelete.setVersion(event.getVersion());

		if (event.getComment() != null) {
			Iterator<Comment> iter = event.getComment().iterator();
			while (iter.hasNext()) {
				Comment comment = iter.next();
				String typeKey = comment.getTypeKey();
				if (CubeToEQMessageConverter.DELETE_MESSAGE_COMMENT_TYPEKEY
						.equals(typeKey) && comment.getText() != null) {
					cubeDelete.setMessage(comment.getText().trim());
					break;
				}
			}
		}

		return cubeDelete;
	}

	/**
	 * Convert an EQMessage that represents an event update to a CubeEvent.
	 * 
	 * @param message
	 *            EQMessage containing event.
	 * @param event
	 *            event to convert.
	 * @return CubeEvent corresponding to EQMessage.
	 */
	public CubeEvent convertEQMessageToCubeEvent(final EQMessage message,
			final Event event) {
		if (event.getAction() != null
				&& event.getAction() != EventAction.UPDATE) {
			return null;
		}

		CubeEvent cubeEvent = new CubeEvent();

		EventUsage usage = event.getUsage();
		if (usage != null && usage != EventUsage.ACTUAL) {
			// only actual events have a CUBE representation
			return null;
		}

		EventScope scope = event.getScope();
		if (scope != null && scope == EventScope.INTERNAL) {
			cubeEvent.setInternal(true);
		} else {
			cubeEvent.setInternal(false);
		}

		EventType type = event.getType();
		if (type != null && type == EventType.QUARRY) {
			cubeEvent.setQuarry(true);
		} else {
			// maybe return null?
			cubeEvent.setQuarry(false);
		}

		cubeEvent.setSent(message.getSent());
		cubeEvent.setSource(event.getDataSource().toLowerCase());
		cubeEvent.setCode(event.getEventID().toLowerCase());
		cubeEvent.setVersion(event.getVersion());

		Origin origin = event.getOrigin().get(0);

		Boolean preferredFlag = origin.getPreferredFlag();
		if (preferredFlag != null && !preferredFlag) {
			// only preferred origins map to CUBE messages
			return null;
		}

		cubeEvent.setTime(origin.getTime());
		cubeEvent.setLatitude(origin.getLatitude());
		cubeEvent.setLongitude(origin.getLongitude());
		cubeEvent.setDepth(origin.getDepth());
		cubeEvent.setRmsTimeError(origin.getStdError());
		cubeEvent.setAzimuthalGap(origin.getAzimGap());
		cubeEvent.setMinStationDistanceDegrees(origin.getMinDist());
		cubeEvent.setHorizontalError(origin.getErrh());
		cubeEvent.setVerticalError(origin.getErrz());

		String depthMethod = origin.getDepthMethod();
		if (CubeToEQMessageConverter.DEPTH_METHOD_FIXED.equals(depthMethod)) {
			cubeEvent.setVerticalError(BigDecimal.ZERO);
		}

		cubeEvent.setNumLocationPhases(origin.getNumPhaUsed());
		cubeEvent.setNumLocationStations(origin.getNumStaUsed());

		OriginStatus status = origin.getStatus();
		if (status != null && status == OriginStatus.REVIEWED) {
			cubeEvent.setReviewed(true);
		} else {
			cubeEvent.setReviewed(false);
		}

		if (origin.getMethod() != null) {
			Iterator<Method> locationMethodIter = origin.getMethod().iterator();
			if (locationMethodIter.hasNext()) {
				Method locationMethod = locationMethodIter.next();
				cubeEvent.setLocationMethod(getCubeCode(locationMethod
						.getComment()));
				if (cubeEvent.getLocationMethod() == null) {
					// try to reverse engineer algorithm
				}
			}
		}

		Magnitude magnitude = origin.getMagnitude().get(0);
		cubeEvent.setMagnitude(magnitude.getValue());
		cubeEvent.setMagnitudeError(magnitude.getError());
		cubeEvent.setMagnitudeType(getCubeCode(magnitude.getComment()));
		cubeEvent.setNumMagnitudeStations(magnitude.getNumStations());

		if (cubeEvent.getMagnitudeType() == null
				&& magnitude.getTypeKey() != null) {
			// no CUBE_CODE comment, try magnitude type
			try {
				cubeEvent.setMagnitudeType(CubeMessage
						.getCubeCode(MagnitudeType.valueOf(magnitude
								.getTypeKey().toUpperCase())));
			} catch (Exception e) {
				// ignore
			}
		}

		return cubeEvent;
	}

	/**
	 * Extract a CUBE_Code code from a EQXML Comment.
	 * 
	 * @param comments
	 *            list of comments to scan.
	 * @return found cube code, or null if not found.
	 * @see CubeToEQMessageConverter#getCubeCodeComment(String)
	 */
	public String getCubeCode(final List<Comment> comments) {
		Iterator<Comment> iter = comments.iterator();
		while (iter.hasNext()) {
			Comment comment = iter.next();
			String typeKey = comment.getTypeKey();
			if (typeKey != null
					&& typeKey
							.equals(CubeToEQMessageConverter.CUBE_CODE_TYPEKEY)
					&& comment.getText() != null) {
				return comment.getText().replace(
						CubeToEQMessageConverter.CUBE_CODE_PREFIX, "");
			}
		}
		return null;
	}

}
