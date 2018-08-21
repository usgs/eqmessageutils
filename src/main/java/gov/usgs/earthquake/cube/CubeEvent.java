package gov.usgs.earthquake.cube;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * CUBE earthquake message.
 * 
 * <pre>{@code
 *  E - Earthquake
 *     The E format messages are used to add an earthquake to the catalog or modify the summary information about and earthquake by issuing a new version. The highest version or most recently received information of the same version is used. The format is:
 * 
 *     TpEidnumbrSoVYearMoDyHrMnSecLatddddLongddddDeptMgNstNphDminRmssErhoErzzGpMNmEmLC
 *     12345678901234567890123456789012345678901234567890123456789012345678901234567890
 *              1         2         3         4         5         6         7         8
 * 
 *     a2 * Tp   = Message type = "E " (seismic event)
 *     a8 * Eid  = Event identification number  (any string)
 *     a2 * So   = Data Source =  regional network designation
 *     a1 * V    = Event Version     (ASCII char, except [,])
 *     i4 * Year = Calendar year                (GMT) (-999-6070)
 *     i2 * Mo   = Month of the year            (GMT) (1-12)
 *     i2 * Dy   = Day of the month             (GMT) (1-31)
 *     i2 * Hr   = Hours since midnight         (GMT) (0-23)
 *     i2 * Mn   = Minutes past the hour        (GMT) (0-59)
 *     i3 * Sec  = Seconds past the minute * 10 (GMT) (0-599)
 *     i7 * Lat  = Latitude:  signed decimal degrees*10000 north>0
 *     i8 * Long = Longitude: signed decimal degrees*10000 west <0
 *     i4   Dept = Depth below sea level, kilometers * 10
 *     i2   Mg   = Magnitude * 10
 *     i3   Nst  = Number of stations used for location
 *     i3   Nph  = Number of phases used for location
 *     i4   Dmin = Distance to 1st station;   kilometers * 10
 *     i4   Rmss = Rms time error; sec * 100
 *     i4   Erho = Horizontal standard error; kilometers * 10
 *     i4   Erzz = Vertical standard error;   kilometers * 10
 *     i2   Gp   = Azimuthal gap, percent of circle; degrees/3.6
 *     a1   M    = Magnitude type
 *     i2   Nm   = Number of stations for magnitude determination
 *     i2   Em   = Standard error of the magnitude * 10
 *     a1   L    = Location method
 *     a1 * C    = Menlo Park check character, defined below
 * 
 *     "Message Type" field:
 *          The second character, following the 'E', is no
 *          longer used for magnitude validation.  Prior "En"
 *          format that used the 2nd char for magnitude
 *          validation is no longer supported.
 * 
 *     "Event Identification Number" field:
 *          Any string, excluding '[' and ']' characters.
 *          Must be a unique identifier for the event.
 *          Two events with the same identifier from the same
 *          source will be the same event.  Format varies by
 *          source - either numeric or alpha-numeric.
 * 
 *     "Data Source" field: regional seismic network designation:
 *          See http://www.iris.washington.edu/FDSN/networks.txt
 * 
 *     "Version Number" field:
 *          May have any value (ASCII 32-126, except 91 or 93).
 *          Meaning varies by source. Used to distinguish between
 *          different versions of the same event.
 * 
 *     "Location Method" field: varies by source (in parentheses):
 *          Upper-case indicates an unconfirmed event,
 *          Lower-case indicates event is confirmed by human review
 * 
 *          A = Binder (AK)
 *          D = Antelope (NN)
 *          F = nonNEIC-furnished (US)
 *          H = Hypoinverse (CI,UU,UW)
 *          L = Earthworm "local" event (NC)
 *          M = macroseismic or "felt" (US)
 *          R = NEIC-furnished (US)
 * 
 *     "Magnitude Type" field:
 *          B = body magnitude (Mb)
 *          C = duration magnitude (Md)
 *          D = duration magnitude (Md)
 *          E = energy magnitude (Me)
 *          G = local magnitude (Ml)
 *          I = "Tsuboi" moment magnitude (Mi)
 *          L = local magnitude (Ml)
 *          N = "Nuttli" surface wave magnitude (MbLg)
 *          O = moment magnitude (Mw)
 *          P = body magnitude (Mb)
 *          S = surface wave magnitude (Ms)
 *          T = teleseismic moment magnitude (Mt)
 *          W = regional moment magnitude (Mw)
 * 
 *     "Menlo Park Check Character" field:
 *          Menlo-Park checksum, calculated 1st through 79th
 *          char in the message.  Checksum method defined by
 *          C language source code, below.
 *          NB: Square bracket characters ARE ACCEPTED in this
 *              field.
 * 
 *     // -------------------------------------------------------
 *     // Menlo Park(USGS) check char (Nov.94) cleaned up by PTG
 *     // Argument:  pch = null terminated string
 *     // Returns:   Menlo Park check char, excess 36 modulo 91
 *     int MenloCheckChar( char* pch )
 *     {
 *          unsigned short sum;
 * 
 *          for( sum=0; *pch; pch++ )
 *              sum = ((sum&01)?0x8000:0) + (sum>>1) + *pch;
 * 
 *          return (int)(36+sum%91);
 *     }
 * 
 *     Examples:
 *     E 09082344CI21999040217051050339860-1169945017316000014001800120009004332C0002hP
 *     E meav    US3199904021838195-201884 1681247 33054 19 192283 062 387  00  B 8   v
 * 
 * 
 *     Note: when stored in the catalog directory a 20 column time stamp is
 *     put at the beginning of the line and the source code is changed to
 *     upper case if the event is in the networks authoritative region and
 *     lower case if it is not in the networks authoritative region.
 * }
 * </pre>
 * 
 * @author jmfee
 */
public class CubeEvent extends CubeMessage {

	/**
	 * Three lines that make reading cube format easier (though still not easy).
	 */
	public static final String HUMAN_PARSING_GUIDE = ""
			+ "TpEidnumbrSoVYearMoDyHrMnSecLatddddLongddddDeptMgNstNphDminRmssErhoErzzGpMNmEmLC\n"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890\n"
			+ "         1         2         3         4         5         6         7         8";

	public static final String EXAMPLE1 = "E 09082344CI21999040217051050339860-1169945017316000014001800120009004332C0002hP";
	public static final String EXAMPLE2 = "E meav    US3199904021838195-201884 1681247 33054 19 192283 062 387  00  B 8   v";
	public static final String EXAMPLE3 = "E 71767785NC2201204200434279 376357-1188813  89 4    22  20   4   4   426D2002hJ";

	/** Cube event messages start with "E ". */
	public static final String TYPE = "E ";

	/** Used to identify an internal event that should not be listed. */
	public static final String INTERNAL_LOCATION_METHOD = "Z";
	/** Used to identify a quarry event. */
	public static final String QUARRY_LOCATION_METHOD = "Q";

	/** Earth radius in km, used to compute kilometers per degree. */
	public static final BigDecimal EARTH_RADIUS_KM = new BigDecimal("6378.137");

	/** some prefer degrees for minimum station distance, cube uses kilometers */
	public static final BigDecimal KILOMETERS_PER_DEGREE = BigDecimal.ONE
			.multiply(new BigDecimal(Math.toRadians(1))).multiply(
					EARTH_RADIUS_KM);

	private Integer year;
	private Integer month;
	private Integer day;
	private Integer hour;
	private Integer minute;
	private BigDecimal second;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private BigDecimal depth;
	private BigDecimal magnitude;
	private BigInteger numLocationStations;
	private BigInteger numLocationPhases;
	private BigDecimal minStationDistance;
	private BigDecimal rmsTimeError;
	private BigDecimal horizontalError;
	private BigDecimal verticalError;
	private BigDecimal azimuthalGap;
	private String magnitudeType;
	private BigInteger numMagnitudeStations;
	private BigDecimal magnitudeError;
	private String locationMethod;
	private String checksum;

	private boolean reviewed;
	private boolean internal;
	private boolean quarry;

	public CubeEvent() {
		// set defaults
		reviewed = false;
		internal = false;
		quarry = false;
	}

	public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000");
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
	public static final BigDecimal TEN = BigDecimal.TEN;
	public static final BigDecimal PERCENT_OF_CIRCLE = BigDecimal.ONE.divide(
			new BigDecimal("3.6"), MathContext.DECIMAL32);

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String toCUBE() {
		StringBuffer buf = new StringBuffer();
		buf.append(super.toCUBE());
		buf.append(CubeMessage.leftPad(year.toString(), 4, '0'));
		buf.append(CubeMessage.leftPad(month.toString(), 2, '0'));
		buf.append(CubeMessage.leftPad(day.toString(), 2, '0'));
		buf.append(CubeMessage.leftPad(hour.toString(), 2, '0'));
		buf.append(CubeMessage.leftPad(minute.toString(), 2, '0'));
		buf.append(CubeMessage.leftPad(
				(second == null) ? "" : second.multiply(TEN).toBigInteger()
						.toString(), 3));
		buf.append(CubeMessage.leftPad((latitude == null) ? "" : latitude
				.multiply(TEN_THOUSAND).toBigInteger().toString(), 7));
		buf.append(CubeMessage.leftPad((longitude == null) ? "" : longitude
				.multiply(TEN_THOUSAND).toBigInteger().toString(), 8));
		buf.append(CubeMessage.leftPad(
				(depth == null) ? "" : depth.multiply(TEN).toBigInteger()
						.toString(), 4));
		buf.append(CubeMessage.leftPad((magnitude == null) ? "" : magnitude
				.multiply(TEN).toBigInteger().toString(), 2));
		buf.append(CubeMessage.leftPad((numLocationStations == null) ? ""
				: numLocationStations.toString(), 3));
		buf.append(CubeMessage.leftPad((numLocationPhases == null) ? ""
				: numLocationPhases.toString(), 3));
		buf.append(CubeMessage
				.leftPad((minStationDistance == null) ? "" : minStationDistance
						.multiply(TEN).toBigInteger().toString(), 4));
		buf.append(CubeMessage.leftPad((rmsTimeError == null) ? ""
				: rmsTimeError.multiply(ONE_HUNDRED).toBigInteger().toString(),
				4));
		buf.append(CubeMessage.leftPad((horizontalError == null) ? ""
				: horizontalError.multiply(TEN).toBigInteger().toString(), 4));
		buf.append(CubeMessage.leftPad((verticalError == null) ? ""
				: verticalError.multiply(TEN).toBigInteger().toString(), 4));
		buf.append(CubeMessage.leftPad((azimuthalGap == null) ? ""
				: azimuthalGap.multiply(PERCENT_OF_CIRCLE).toBigInteger()
						.toString(), 2));
		buf.append(CubeMessage.leftPad(magnitudeType, 1));
		buf.append(CubeMessage.leftPad((numMagnitudeStations == null) ? ""
				: numMagnitudeStations.toString(), 2));
		buf.append(CubeMessage.leftPad((magnitudeError == null) ? ""
				: magnitudeError.multiply(TEN).toBigInteger().toString(), 2));

		// may need to override for special meanings
		String locMethod = locationMethod;
		if (isQuarry()) {
			locMethod = QUARRY_LOCATION_METHOD;
		} else if (isInternal()) {
			locMethod = INTERNAL_LOCATION_METHOD;
		}
		if (locMethod != null) { // if null, invalid, but docs provide example
									// without...
			// review status determines case
			if (isReviewed()) {
				locMethod = locMethod.toLowerCase();
			} else {
				locMethod = locMethod.toUpperCase();
			}
		}
		buf.append(CubeMessage.leftPad(locMethod, 1));

		// compute checksum and append
		buf.append(CubeMessage.leftPad(
				CubeChecksum.strChecksum(buf.toString()), 1));

		return buf.toString();
	}

	public static CubeEvent parseCUBE(final String line) {
		String type = line.substring(0, 2);
		if (!TYPE.equals(type)) {
			// not a cube event
			throw new IllegalArgumentException(
					"Unexpected CUBE type, expected '" + TYPE + "', got '"
							+ type + "'");
		}

		String expectedChecksum = CubeChecksum.strChecksum(line
				.substring(0, 79));
		if (!expectedChecksum.equals(line.substring(79, 80))) {
			// bad checksum
			throw new IllegalArgumentException("Invalid checksum, expected '"
					+ expectedChecksum + "'");
		}

		String toParse = line;
		if (toParse.indexOf("*") != -1) {
			// simplify parsing
			toParse.replace("*", " ");
		}

		String code = toParse.substring(2, 10).trim().toLowerCase();
		String source = toParse.substring(10, 12).trim().toLowerCase();
		String version = toParse.substring(12, 13).trim();
		String year = toParse.substring(13, 17).trim();
		String month = toParse.substring(17, 19).trim();
		String day = toParse.substring(19, 21).trim();
		String hour = toParse.substring(21, 23).trim();
		String minute = toParse.substring(23, 25).trim();
		String second = toParse.substring(25, 28).trim();
		String latitude = toParse.substring(28, 35).trim();
		String longitude = toParse.substring(35, 43).trim();
		String depth = toParse.substring(43, 47).trim();
		String magnitude = toParse.substring(47, 49).trim();
		String numLocationStations = toParse.substring(49, 52).trim();
		String numLocationPhases = toParse.substring(52, 55).trim();
		String minStationDistance = toParse.substring(55, 59).trim();
		String rmsTimeError = toParse.substring(59, 63).trim();
		String horizontalError = toParse.substring(63, 67).trim();
		String verticalError = toParse.substring(67, 71).trim();
		String azimuthalGap = toParse.substring(71, 73).trim();
		String magnitudeType = toParse.substring(73, 74).trim();
		String numMagnitudeStations = toParse.substring(74, 76).trim();
		String magnitudeError = toParse.substring(76, 78).trim();
		String locationMethod = toParse.substring(78, 79).trim();
		String checksum = toParse.substring(79, 80).trim();

		CubeEvent event = new CubeEvent();
		event.setCode(code.equals("") ? null : code);
		event.setSource(source.equals("") ? null : source);
		event.setVersion(version.equals("") ? null : version);
		event.setYear(year.equals("") ? null : Integer.valueOf(year));
		event.setMonth(month.equals("") ? null : Integer.valueOf(month));
		event.setDay(day.equals("") ? null : Integer.valueOf(day));
		event.setHour(hour.equals("") ? null : Integer.valueOf(hour));
		event.setMinute(minute.equals("") ? null : Integer.valueOf(minute));
		event.setSecond(second.equals("") ? null : new BigDecimal(second)
				.divide(TEN));
		event.setLatitude(latitude.equals("") ? null : new BigDecimal(latitude)
				.divide(TEN_THOUSAND));
		event.setLongitude(longitude.equals("") ? null : new BigDecimal(
				longitude).divide(TEN_THOUSAND));
		event.setDepth(depth.equals("") ? null : new BigDecimal(depth)
				.divide(TEN));
		event.setMagnitude(magnitude.equals("") ? null : new BigDecimal(
				magnitude).divide(TEN));
		event.setNumLocationStations(numLocationStations.equals("") ? null
				: new BigInteger(numLocationStations));
		event.setNumLocationPhases(numLocationPhases.equals("") ? null
				: new BigInteger(numLocationPhases));
		event.setMinStationDistance(minStationDistance.equals("") ? null
				: new BigDecimal(minStationDistance).divide(TEN));
		event.setRmsTimeError(rmsTimeError.equals("") ? null : new BigDecimal(
				rmsTimeError).divide(ONE_HUNDRED));
		event.setHorizontalError(horizontalError.equals("") ? null
				: new BigDecimal(horizontalError).divide(TEN));
		event.setVerticalError(verticalError.equals("") ? null
				: new BigDecimal(verticalError).divide(TEN));
		event.setAzimuthalGap(azimuthalGap.equals("") ? null : new BigDecimal(
				azimuthalGap).divide(PERCENT_OF_CIRCLE, MathContext.DECIMAL128)
				.stripTrailingZeros());
		event.setMagnitudeType(magnitudeType.equals("") ? null : magnitudeType);
		event.setNumMagnitudeStations(numMagnitudeStations.equals("") ? null
				: new BigInteger(numMagnitudeStations));
		event.setMagnitudeError(magnitudeError.equals("") ? null
				: new BigDecimal(magnitudeError).divide(TEN));
		event.setLocationMethod(locationMethod.equals("") ? null
				: locationMethod);
		event.setChecksum(checksum.equals("") ? null : checksum);

		if (!locationMethod.equals("")
				&& Character.isLowerCase(locationMethod.charAt(0))) {
			// reviewed if location method and location method is lower case
			event.setReviewed(true);
		} else {
			event.setReviewed(false);
		}

		event.setInternal(locationMethod
				.equalsIgnoreCase(INTERNAL_LOCATION_METHOD));
		event.setQuarry(locationMethod.equalsIgnoreCase(QUARRY_LOCATION_METHOD));

		return event;
	}

	public boolean isValid() {
		return (
		// id is required
		source != null
				&& code != null
				&& version != null
				// time is required
				&& year != null
				// month between 1 and 12
				&& month != null
				&& month >= 1
				&& month <= 12
				// day between 1 and 31
				&& day != null
				&& day >= 1
				&& day <= 31
				// hour between 0 and 23
				&& hour != null
				&& hour >= 0
				&& hour <= 23
				// minute between 0 and 59
				&& minute != null
				&& minute >= 0
				&& minute <= 59
				// second between 0 and 59.9
				&& second != null
				&& second.compareTo(BigDecimal.ZERO) >= 0
				&& second.compareTo(new BigDecimal("59.9")) <= 0
				// location is required
				// latitude between -90 and 90
				&& latitude != null
				&& latitude.compareTo(new BigDecimal("-90.0")) >= 0
				&& latitude.compareTo(new BigDecimal("90.0")) <= 0
				// longitude between -180 and 180
				&& longitude != null
				&& longitude.compareTo(new BigDecimal("-180.0")) >= 0
				&& longitude.compareTo(new BigDecimal("180.0")) <= 0
				// magnitude is required
				&& magnitude != null
				&& magnitude.compareTo(new BigDecimal("-1.0")) >= 0
				&& magnitude.compareTo(new BigDecimal("11.0")) <= 0
				// magnitude type and location method are required
				&& magnitudeType != null && locationMethod != null
		);
	}

	/**
	 * Convert date parameters to java.util.Date object.
	 * 
	 * This method uses the year, month, day, hour, minute, and second fields.
	 * 
	 * @return when this event occured.
	 */
	public Date getTime() {
		// utc date
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();

		// set seconds to zero, add milliseconds later
		// month is 0-based
		calendar.set(year, month - 1, day, hour, minute, 0);

		// compute seconds as milliseconds to add to above calendar value
		// *10 shift on big decimal to preserve tenths,
		// then do additional *100 as an int
		int milliseconds = second.multiply(BigDecimal.TEN).toBigInteger()
				.intValue() * 100;

		return new Date(calendar.getTimeInMillis() + milliseconds);
	}

	/**
	 * Set cube date parameters using a java.util.Date object.
	 * 
	 * Calling this method will set year, month, day, hour, minute, and second
	 * fields.
	 * 
	 * @param date
	 *            when this event occured.
	 */
	public void setTime(final Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();

		calendar.setTime(date);

		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);

		// used to convert between seconds and milliseconds
		BigDecimal THOUSAND = new BigDecimal("1000");

		// calculate number of milliseconds after minute
		BigDecimal milliseconds = new BigDecimal(calendar.get(Calendar.SECOND))
				.multiply(THOUSAND);
		milliseconds = milliseconds.add(new BigDecimal(calendar
				.get(Calendar.MILLISECOND)));
		// now convert milliseconds to seconds
		second = milliseconds.divide(THOUSAND);
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public BigDecimal getSecond() {
		return second;
	}

	public void setSecond(BigDecimal second) {
		this.second = second;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getDepth() {
		return depth;
	}

	public void setDepth(BigDecimal depth) {
		this.depth = depth;
	}

	public BigDecimal getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(BigDecimal magnitude) {
		this.magnitude = magnitude;
	}

	public BigInteger getNumLocationStations() {
		return numLocationStations;
	}

	public void setNumLocationStations(BigInteger numLocationStations) {
		this.numLocationStations = numLocationStations;
	}

	public BigInteger getNumLocationPhases() {
		return numLocationPhases;
	}

	public void setNumLocationPhases(BigInteger numLocationPhases) {
		this.numLocationPhases = numLocationPhases;
	}

	public BigDecimal getMinStationDistance() {
		return minStationDistance;
	}

	public void setMinStationDistance(BigDecimal minStationDistance) {
		this.minStationDistance = minStationDistance;
	}

	/**
	 * Get min station distance in degrees (instead of km).
	 * 
	 * @return min station distance in degrees.
	 */
	public BigDecimal getMinStationDistanceDegrees() {
		if (minStationDistance == null) {
			return null;
		} else {
			return minStationDistance.divide(KILOMETERS_PER_DEGREE,
					MathContext.DECIMAL32);
		}
	}

	/**
	 * Set min station distance in degrees (instead of km).
	 * 
	 * @param degrees
	 */
	public void setMinStationDistanceDegrees(final BigDecimal degrees) {
		if (degrees == null) {
			minStationDistance = null;
		} else {
			minStationDistance = degrees.multiply(KILOMETERS_PER_DEGREE,
					MathContext.DECIMAL32);
		}
	}

	public BigDecimal getRmsTimeError() {
		return rmsTimeError;
	}

	public void setRmsTimeError(BigDecimal rmsTimeError) {
		this.rmsTimeError = rmsTimeError;
	}

	public BigDecimal getHorizontalError() {
		return horizontalError;
	}

	public void setHorizontalError(BigDecimal horizontalError) {
		this.horizontalError = horizontalError;
	}

	public BigDecimal getVerticalError() {
		return verticalError;
	}

	public void setVerticalError(BigDecimal verticalError) {
		this.verticalError = verticalError;
	}

	public BigDecimal getAzimuthalGap() {
		return azimuthalGap;
	}

	public void setAzimuthalGap(BigDecimal azimuthalGap) {
		this.azimuthalGap = azimuthalGap;
	}

	public String getMagnitudeType() {
		return magnitudeType;
	}

	public void setMagnitudeType(String magnitudeType) {
		this.magnitudeType = magnitudeType;
	}

	public BigInteger getNumMagnitudeStations() {
		return numMagnitudeStations;
	}

	public void setNumMagnitudeStations(BigInteger numMagnitudeStations) {
		this.numMagnitudeStations = numMagnitudeStations;
	}

	public BigDecimal getMagnitudeError() {
		return magnitudeError;
	}

	public void setMagnitudeError(BigDecimal magnitudeError) {
		this.magnitudeError = magnitudeError;
	}

	public String getLocationMethod() {
		return locationMethod;
	}

	public void setLocationMethod(String locationMethod) {
		this.locationMethod = locationMethod;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public boolean isReviewed() {
		return reviewed;
	}

	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public boolean isQuarry() {
		return quarry;
	}

	public void setQuarry(boolean quarry) {
		this.quarry = quarry;
	}

}
