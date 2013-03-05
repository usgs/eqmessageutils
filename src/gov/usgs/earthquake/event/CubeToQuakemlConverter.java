package gov.usgs.earthquake.event;

import gov.usgs.earthquake.cube.CubeDelete;
import gov.usgs.earthquake.cube.CubeEvent;
import gov.usgs.earthquake.cube.CubeMessage;
import gov.usgs.earthquake.quakeml.QuakemlPublicId;

import java.math.BigDecimal;

import java.util.Date;

import org.quakeml_1_2.Comment;
import org.quakeml_1_2.CreationInfo;
import org.quakeml_1_2.EvaluationMode;
import org.quakeml_1_2.Event;
import org.quakeml_1_2.EventParameters;
import org.quakeml_1_2.EventType;
import org.quakeml_1_2.Magnitude;
import org.quakeml_1_2.OriginDepthType;
import org.quakeml_1_2.OriginQuality;
import org.quakeml_1_2.OriginUncertainty;
import org.quakeml_1_2.OriginUncertaintyDescription;
import org.quakeml_1_2.Quakeml;
import org.quakeml_1_2.Origin;
import org.quakeml_1_2.RealQuantity;
import org.quakeml_1_2.TimeQuantity;

/**
 * Convert from CubeMessage to Quakeml.
 * 
 * @author jmfee
 */
public class CubeToQuakemlConverter {

	// quakeml uses meters, cube uses kilometers
	public static final BigDecimal METERS_PER_KILOMETER = new BigDecimal("1000");
	public static final String CUBE_COMMENT_TYPE = "cube";

	public static final String CUBE_LOCATION_METHOD_PUBLICID_PREFIX = "quakeml:anss.org/cube/locationMethod/";
	public static final String CUBE_MAGNITUDE_TYPE_PUBLICID_PREFIX = "quakeml:anss.org/cube/magnitudeType/";

	/**
	 * Convert a CubeMessage object to a Quakeml object.
	 * 
	 * @param message
	 * @return Quakeml object corresponding to CubeMessage, or null if no
	 *         Quakeml representation.
	 * @throws Exception
	 */
	public Quakeml convertCubeMessage(final CubeMessage message)
			throws Exception {
		if (message instanceof CubeEvent) {
			return convertCubeEvent((CubeEvent) message);
		} else if (message instanceof CubeDelete) {
			return convertCubeDelete((CubeDelete) message);
		} else {
			return null;
		}
	}

	/**
	 * Convert a CubeDelete object to a Quakeml object.
	 * 
	 * @param message
	 * @return Quakeml object corresponding to CubeDelete.
	 * @throws Exception
	 */
	private Quakeml convertCubeDelete(CubeDelete message) throws Exception {
		Quakeml quakeml = new Quakeml();

		// if message already has sent time, use that
		Date created = message.getSent();
		if (created == null) {
			created = new Date();
		}

		// event parameters id is unique to this message
		EventParameters eventParameters = new EventParameters();
		eventParameters.setPublicID(getQuakemlId(message.getSource(),
				message.getCode(), "eventParamters",
				Long.toString(created.getTime())));
		quakeml.setEventParameters(eventParameters);

		Event event = new Event();
		event.setPublicID(getQuakemlId(message.getSource(), message.getCode(),
				"event", null));
		event.setType(EventType.NOT_EXISTING);

		// set catalog info
		event.setDatasource(message.getSource());
		event.setEventsource(message.getSource());
		event.setEventid(message.getCode());

		eventParameters.getEvents().add(event);

		// who and when was this message sent
		CreationInfo creationInfo = new CreationInfo();
		creationInfo.setAgencyID(message.getSource());
		creationInfo.setVersion(message.getVersion());
		creationInfo.setCreationTime(created);
		event.setCreationInfo(creationInfo);

		// update time comes from event parameters
		creationInfo = new CreationInfo();
		creationInfo.setCreationTime(created);
		eventParameters.setCreationInfo(creationInfo);

		String deleteMessage = message.getMessage();
		if (deleteMessage != null) {
			Comment deleteComment = new Comment();
			deleteComment.setText(deleteMessage);
			event.getComments().add(deleteComment);
		}

		return quakeml;
	}

	/**
	 * Convert a CubeEvent object to a Quakeml object.
	 * 
	 * @param message
	 * @return Quakeml object corresponding to CubeEvent.
	 * @throws Exception
	 */
	public Quakeml convertCubeEvent(CubeEvent message) throws Exception {
		if (message.isInternal()) {
			// quakeml does not differentiate between internal/public
			// TODO: remove this and send internal-origin type products?
			return null;
		}

		Quakeml quakeml = new Quakeml();

		// if message already has sent time, use that
		Date created = message.getSent();
		if (created == null) {
			created = new Date();
		}

		// event parameters id is unique to this message
		EventParameters eventParameters = new EventParameters();
		eventParameters.setPublicID(getQuakemlId(message.getSource(),
				message.getCode(), "eventParameters",
				Long.toString(created.getTime())));
		quakeml.setEventParameters(eventParameters);

		// event container
		Event event = new Event();
		event.setPublicID(getQuakemlId(message.getSource(), message.getCode(),
				"event", null));

		// set catalog info
		event.setDatasource(message.getSource());
		event.setEventsource(message.getSource());
		event.setEventid(message.getCode());

		eventParameters.getEvents().add(event);

		// version and sent timestamp in event creation info
		CreationInfo creationInfo = new CreationInfo();
		creationInfo.setAgencyID(message.getSource());
		creationInfo.setVersion(message.getVersion());
		creationInfo.setCreationTime(created);
		event.setCreationInfo(creationInfo);

		// update time comes from event parameters
		creationInfo = new CreationInfo();
		creationInfo.setCreationTime(created);
		eventParameters.setCreationInfo(creationInfo);

		// mark quarry events
		if (message.isQuarry()) {
			event.setType(EventType.QUARRY_BLAST);
		} else {
			event.setType(EventType.EARTHQUAKE);
		}

		// origin container
		Origin origin = new Origin();
		event.getOrigins().add(origin);

		origin.setPublicID(getQuakemlId(message.getSource(), message.getCode(),
				"origin", null));

		// preferred signals this is an "origin" type message instead of, for
		// example, a moment-tensor derived origin
		event.setPreferredOriginID(origin.getPublicID());

		// location method
		origin.setMethodID(getQuakemlLocationMethod(message.getLocationMethod()));

		// origin time
		TimeQuantity time = new TimeQuantity();
		time.setValue(message.getTime());
		origin.setTime(time);

		// origin latitude
		RealQuantity latitude = new RealQuantity();
		latitude.setValue(message.getLatitude());
		origin.setLatitude(latitude);

		// origin longitude
		RealQuantity longitude = new RealQuantity();
		longitude.setValue(message.getLongitude());
		origin.setLongitude(longitude);

		// origin depth
		RealQuantity depth = new RealQuantity();
		depth.setValue(kilometersToMeters(message.getDepth()));
		origin.setDepth(depth);

		// vertical error
		BigDecimal verticalError = message.getVerticalError();
		if (verticalError != null) {
			if (verticalError.compareTo(BigDecimal.ZERO) == 0) {
				// if vertical error 0, depth was fixed
				origin.setDepthType(OriginDepthType.OPERATOR_ASSIGNED);
			} else {
				depth.setUncertainty(kilometersToMeters(message
						.getVerticalError()));
			}
		}

		// horizontal error
		BigDecimal horizontalError = message.getHorizontalError();
		if (horizontalError != null) {
			OriginUncertainty originUncertainty = new OriginUncertainty();
			originUncertainty
					.setPreferredDescription(OriginUncertaintyDescription.HORIZONTAL_UNCERTAINTY);
			originUncertainty
					.setHorizontalUncertainty(kilometersToMeters(horizontalError));
			origin.setOriginUncertainty(originUncertainty);
		}

		OriginQuality originQuality = new OriginQuality();
		origin.setQuality(originQuality);

		// num stations for location
		originQuality.setUsedStationCount(message.getNumLocationStations());

		// num phases for location
		originQuality.setUsedPhaseCount(message.getNumLocationPhases());

		// azimuthal gap
		originQuality.setAzimuthalGap(message.getAzimuthalGap());

		// distance to first station
		originQuality
				.setMinimumDistance(message.getMinStationDistanceDegrees());

		// rms time error (standard error)
		originQuality.setStandardError(message.getRmsTimeError());

		Magnitude magnitude = new Magnitude();
		event.getMagnitudes().add(magnitude);

		// append magnitude type on extended id, in case agency generates
		// multiple types
		String magnitudeExtendedId = null;
		MagnitudeType magnitudeType = CubeMessage.getMagnitudeType(message
				.getMagnitudeType());
		if (magnitudeType != null) {
			magnitudeExtendedId = magnitudeType.getAbbreviation();
			// also set plain text type
			magnitude.setType(magnitudeType.getAbbreviation());
		}
		magnitude.setPublicID(getQuakemlId(message.getSource(),
				message.getCode(), "magnitude", magnitudeExtendedId));

		// tie to the origin
		magnitude.setOriginID(origin.getPublicID());

		// num stations for magnitude
		magnitude.setStationCount(message.getNumMagnitudeStations());

		// magnitude type
		magnitude.setMethodID(getQuakemlMagnitudeType(message
				.getMagnitudeType()));

		// preferred signals this is the magnitude to use for the "origin" type
		// message instead of, for example, a moment-tensor derived magnitude
		event.setPreferredMagnitudeID(magnitude.getPublicID());

		// magnitude value
		RealQuantity mag = new RealQuantity();
		mag.setValue(message.getMagnitude());

		// magnitude error
		mag.setUncertainty(message.getMagnitudeError());
		magnitude.setMag(mag);

		// review status
		if (message.isReviewed()) {
			origin.setEvaluationMode(EvaluationMode.MANUAL);
			magnitude.setEvaluationMode(EvaluationMode.MANUAL);
		} else {
			origin.setEvaluationMode(EvaluationMode.AUTOMATIC);
			magnitude.setEvaluationMode(EvaluationMode.AUTOMATIC);
		}

		return quakeml;
	}

	/**
	 * Get a Quakeml LocationMethod string from a Cube Location Method.
	 * 
	 * @param locationMethod
	 * @return locationMethod as a quakeml public id.
	 */
	private String getQuakemlLocationMethod(String locationMethod) {
		if (locationMethod == null || locationMethod.trim().equals("")) {
			return null;
		}
		return CUBE_LOCATION_METHOD_PUBLICID_PREFIX + locationMethod;
	}

	/**
	 * Get a Quakeml MagnitudeType string from a Cube Magnitude Type.
	 * 
	 * @param magnitudeType
	 * @return magnitude type as a quakeml public id.
	 */
	private String getQuakemlMagnitudeType(String magnitudeType) {
		if (magnitudeType == null || magnitudeType.trim().equals("")) {
			return null;
		}
		return CUBE_MAGNITUDE_TYPE_PUBLICID_PREFIX + magnitudeType;
	}

	/**
	 * Get a QuakeML publicID attribute.
	 * 
	 * @param dataSource
	 * @param eventId
	 * @param type
	 * @param other
	 * @return quakeml public id containing parameters.
	 * @throws Exception
	 */
	private String getQuakemlId(final String dataSource, final String eventId,
			final String type, final String other) throws Exception {
		return new QuakemlPublicId(dataSource.toLowerCase(), type,
				eventId.toLowerCase(), other).getPublicId();
	}

	/**
	 * Convert kilometers to meters by multiplying by 1000.
	 * 
	 * @param kilometers
	 * @return null if kilometers is null, or a meters equivalent of kilometers.
	 */
	private BigDecimal kilometersToMeters(final BigDecimal kilometers) {
		if (kilometers == null) {
			return null;
		}
		BigDecimal meters = kilometers.multiply(new BigDecimal("1000"));
		// cube format doesn't offer this precision, so don't imply it...
		return meters.stripTrailingZeros();
	}

}
