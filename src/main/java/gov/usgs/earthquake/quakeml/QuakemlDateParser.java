package gov.usgs.earthquake.quakeml;

import gov.usgs.earthquake.util.ISO8601;

import java.util.Date;

/**
 * Quakeml datetime parser/formatter.
 * 
 * Quakeml defines dates without a timezone to be UTC; while xml schema defines
 * dates without a timezone to be system time.
 * 
 * @author jmfee
 */
public class QuakemlDateParser {

	/**
	 * Parse a date string as UTC if it doesn't specify an offset, otherwise use
	 * the specified offset.
	 * 
	 * @param toParse
	 *            the xml date time string to parse.
	 * @return the parsed Date object.
	 */
	public static Date getDate(final String toParse) {
		Date date = null;

		// time separator
		int tIndex = toParse.indexOf("T");
		if (toParse.indexOf("Z", tIndex) > -1
				|| toParse.indexOf("+", tIndex) > -1
				|| toParse.indexOf("-", toParse.indexOf("T")) > -1) {
			// has timezone offset, can parse as ISO8601
			date = ISO8601.parse(toParse);
		} else {
			// no timezone offset specified, force UTC
			date = ISO8601.parse(toParse + "Z");
		}
		return date;
	}

	/**
	 * Format a date as UTC.
	 * 
	 * @param toFormat
	 *            date to format
	 * @return ISO 8601 formatted date.
	 */
	public static String formatDate(final Date toFormat) {
		return ISO8601.format(toFormat);
	}

}
