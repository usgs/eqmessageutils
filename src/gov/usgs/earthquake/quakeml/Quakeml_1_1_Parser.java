package gov.usgs.earthquake.quakeml;

import gov.usgs.earthquake.util.IOUtil;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.quakeml_1_1.Quakeml;

/**
 * Parse Quakeml 1.1 messages.
 * 
 * @see org.quakeml_1_1.Quakeml
 * @author jmfee
 */
public class Quakeml_1_1_Parser {

	private static final JAXBContext CONTEXT;
	static {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance("org.quakeml_1_1");
		} catch (Exception e) {
			System.err.println("Unable to create org.quakeml JAXBContext");
			e.printStackTrace();
			context = null;
		}
		CONTEXT = context;
	}

	/**
	 * Parse a Quakeml object from an InputStream-able object.
	 * 
	 * @param source
	 *            InputStream-able object, as defined by Utility.getInputStream.
	 * @return an EQMessage object, or null if an error occurred.
	 */
	public static synchronized Quakeml parse(final Object source) {
		Quakeml message = null;
		InputStream in = null;

		try {
			in = IOUtil.getInputStream(source);
			Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
			if (unmarshaller != null) {
				message = (Quakeml) unmarshaller.unmarshal(in);
			}
		} catch (Exception e) {
			System.err.println("Error processing source " + e.toString());
			System.err.println(source);
			System.err.println();
		} finally {
			IOUtil.close(in);
		}

		return message;
	}

	/**
	 * Convert a Quakeml object to XML.
	 * 
	 * @param message
	 *            the message to serialize.
	 * @param out
	 *            the outputstream where xml is written.
	 * @throws JAXBException
	 */
	public static synchronized void serialize(final Quakeml message,
			final OutputStream out) throws JAXBException {
		Marshaller marshaller = CONTEXT.createMarshaller();
		marshaller.marshal(message, out);
	}
}
