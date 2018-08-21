package gov.usgs.earthquake.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Convert between java.util.Date and ISO8601 string.
 * 
 * @author jmfee
 */
public class ISO8601 {

	/**
	 * Convenience method to format a Date as an XML DateTime String.
	 * 
	 * @param date
	 *            the date to format.
	 * @return the normalized XML representation as a string.
	 */
	public static String format(final Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(date.getTime());
		try {
			return DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(calendar).normalize()
					.toXMLFormat();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Convenience method to parse an XML Date Time into a Date. Only useful
	 * when the XML Date Time is within the Date object time range.
	 * 
	 * @param toParse
	 *            the xml date time string to parse.
	 * @return the parsed Date object.
	 */
	public static Date parse(final String toParse) {
		XMLGregorianCalendar calendar = null;
		try {
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(
					toParse);
			return new Date(calendar.toGregorianCalendar().getTimeInMillis());
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}

}
