package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

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
 * Convert from CubeMessage to EQMessage.
 * 
 * Converter provides simpler interfaces to this class.
 * 
 * @see Converter
 * @author jmfee
 */
public class CubeToEQMessageConverter {

	public static final String DELETE_MESSAGE_COMMENT_TYPEKEY = "Info";
	public static final String CUBE_CODE_TYPEKEY = "CUBE_Code";
	public static final String CUBE_CODE_PREFIX = "CUBE_Code ";

	public static final String DEPTH_METHOD_FIXED = "Fixed";
	public static final String LOCATION_METHOD_UNKNOWN = "Unknown";

	/**
	 * Convert either a CubeEvent or CubeDelete object to an EQMessage.
	 * 
	 * @param message
	 * @return EQMessage corresponding to CubeMessage.
	 */
	public EQMessage convertCubeMessage(final CubeMessage message) {
		if (message instanceof CubeEvent) {
			return convertCubeEvent((CubeEvent) message);
		} else if (message instanceof CubeDelete) {
			return convertCubeDelete((CubeDelete) message);
		} else if (message instanceof CubeAddon) {
			return convertCubeAddon((CubeAddon) message);
		} else {
			return null;
		}
	}

	/**
	 * Convert a CubeDelete to an EQMessage.
	 * 
	 * @param message
	 * @return EQMessage corresponding to Cube Delete.
	 */
	public EQMessage convertCubeAddon(CubeAddon message) {
		EQMessage eqMessage = new EQMessage();

		// if message already has sent time, use that
		Date created = message.getSent();
		if (created == null) {
			created = new Date();
		}

		eqMessage.setSent(created);
		eqMessage.setSource(message.getSource().toLowerCase());

		Event event = new Event();
		event.setDataSource(message.getSource().toLowerCase());
		event.setEventID(message.getCode().toLowerCase());
		eqMessage.getEvent().add(event);

		ProductLink productLink = new ProductLink();
		productLink.setSourceKey(message.getSource().toLowerCase());
		productLink.setCode(message.getAddonType());
		productLink.setLink(message.getAddonUrl());
		productLink.setVersion(message.getVersion());
		if (message.isDeleted()) {
			productLink.setAction(Action.DELETE);
		} else {
			productLink.setAction(Action.UPDATE);
			productLink.setNote(message.getAddonText());
		}
		event.getProductLink().add(productLink);

		return eqMessage;
	}

	/**
	 * Convert a CubeDelete to an EQMessage.
	 * 
	 * @param message
	 * @return EQMessage corresponding to Cube Delete.
	 */
	public EQMessage convertCubeDelete(CubeDelete message) {
		EQMessage eqMessage = new EQMessage();

		// if message already has sent time, use that
		Date created = message.getSent();
		if (created == null) {
			created = new Date();
		}

		eqMessage.setSent(created);
		eqMessage.setSource(message.getSource().toLowerCase());

		Event event = new Event();
		event.setAction(EventAction.DELETE);
		event.setDataSource(message.getSource().toLowerCase());
		event.setEventID(message.getCode().toLowerCase());
		event.setVersion(message.getVersion());
		eqMessage.getEvent().add(event);

		String deleteMessage = message.getMessage();
		if (deleteMessage != null) {
			Comment comment = new Comment();
			comment.setTypeKey(DELETE_MESSAGE_COMMENT_TYPEKEY);
			comment.setText(deleteMessage);
			event.getComment().add(comment);
		}

		return eqMessage;
	}

	/**
	 * Convert a CubeEvent to an EQMessage.
	 * 
	 * @param message
	 * @return EQMessage corresponding to CubeEvent.
	 */
	public EQMessage convertCubeEvent(CubeEvent message) {
		EQMessage eqMessage = new EQMessage();

		// if message already has sent time, use that
		Date created = message.getSent();
		if (created == null) {
			created = new Date();
		}

		eqMessage.setSent(created);
		eqMessage.setSource(message.getSource());

		Event event = new Event();
		event.setAction(EventAction.UPDATE);
		event.setDataSource(message.getSource().toLowerCase());
		event.setEventID(message.getCode().toLowerCase());
		event.setVersion(message.getVersion());
		eqMessage.getEvent().add(event);

		// all cube messages are actual events (no scenarios)
		event.setUsage(EventUsage.ACTUAL);

		if (message.isQuarry()) {
			event.setType(EventType.QUARRY);
		} else {
			event.setType(EventType.EARTHQUAKE);
		}

		if (message.isInternal()) {
			event.setScope(EventScope.INTERNAL);
		} else {
			event.setScope(EventScope.PUBLIC);
		}

		Origin origin = new Origin();
		origin.setTime(message.getTime());
		origin.setLatitude(message.getLatitude());
		origin.setLongitude(message.getLongitude());
		origin.setDepth(message.getDepth());
		origin.setStdError(message.getRmsTimeError());
		origin.setAzimGap(message.getAzimuthalGap());
		origin.setMinDist(message.getMinStationDistanceDegrees());
		origin.setErrh(message.getHorizontalError());

		BigDecimal verticalError = message.getVerticalError();
		if (verticalError != null) {
			if (verticalError.equals(BigDecimal.ZERO)) {
				origin.setDepthMethod(DEPTH_METHOD_FIXED);
			}
			origin.setErrz(verticalError);
		}

		origin.setNumPhaUsed(message.getNumLocationPhases());
		origin.setNumStaUsed(message.getNumLocationStations());

		if (message.isReviewed()) {
			origin.setStatus(OriginStatus.REVIEWED);
		} else {
			origin.setStatus(OriginStatus.AUTOMATIC);
		}

		origin.setPreferredFlag(true);
		event.getOrigin().add(origin);

		Magnitude magnitude = new Magnitude();
		magnitude.setValue(message.getMagnitude());
		magnitude.setError(message.getMagnitudeError());
		magnitude.setNumStations(message.getNumMagnitudeStations());
		magnitude.setPreferredFlag(true);
		origin.getMagnitude().add(magnitude);

		MagnitudeType magnitudeType = CubeMessage.getMagnitudeType(message
				.getMagnitudeType());
		if (magnitudeType != null) {
			magnitude.setTypeKey(magnitudeType.getAbbreviation());

			Comment magnitudeTypeComment = getCubeCodeComment(message
					.getMagnitudeType());
			if (magnitudeTypeComment != null) {
				magnitude.getComment().add(magnitudeTypeComment);
			} else {
				// TODO: should this just return null?
			}
		}

		Method locationMethod = new Method();
		Comment comment = getCubeCodeComment(message.getLocationMethod());
		if (comment != null) {
			locationMethod.setAlgorithm(message.getLocationMethod());
			locationMethod.getComment().add(comment);
			origin.getMethod().add(locationMethod);
		} else {
			// TODO: should this just return null?
		}

		return eqMessage;
	}

	/**
	 * Create an EQMessage "CUBE_Code" comment.
	 * 
	 * Introduced by ISTI as a way to preserve the original Cube location method
	 * and magnitude type characters, which did not uniformly map to EQXML
	 * equivalents.
	 * 
	 * <pre>
	 * &lt;Comment&gt;
	 * &lt;TypeKey&gt;CUBE_Code&lt;/TypeKey&gt;
	 * &lt;Text&gt;CUBE_Code X&lt;/Text&gt;
	 * &lt;/Comment&gt;
	 * </pre>
	 * 
	 * where "X" is the cube character.
	 * 
	 * 
	 * @param cubecode
	 * @return Comment EQXML fragment that represents cube code.
	 */
	public Comment getCubeCodeComment(final String cubecode) {
		if (cubecode == null) {
			return null;
		}
		Comment comment = new Comment();
		comment.setTypeKey(CUBE_CODE_TYPEKEY);
		comment.setText(CUBE_CODE_PREFIX + cubecode);
		return comment;
	}

}
