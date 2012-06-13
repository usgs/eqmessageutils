package gov.usgs.earthquake.eqxml;

import gov.usgs.ansseqmsg.EQMessage;
import gov.usgs.earthquake.util.IOUtil;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Parse EQMessage messages.
 * 
 * @see gov.usgs.ansseqmsg.EQMessage
 * @author jmfee
 */
public class EQMessageParser {

	private static final JAXBContext CONTEXT;

	static {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance("gov.usgs.ansseqmsg");
		} catch (Exception e) {
			System.err.println("Unable to create JAXBContext");
			e.printStackTrace();
			context = null;
		}
		CONTEXT = context;
	}

	/**
	 * Parse an EQMessage from an InputStream-able object.
	 * 
	 * @param source
	 *            InputStream-able object, as defined by Utility.getInputStream.
	 * @return an EQMessage object, or null if an error occurred.
	 */
	public static synchronized EQMessage parse(final Object source) {
		EQMessage message = null;
		InputStream in = null;

		try {
			in = IOUtil.getInputStream(source);
			Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
			if (unmarshaller != null) {
				message = (EQMessage) unmarshaller.unmarshal(in);
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
	 * Convert an EQMessage to XML.
	 * 
	 * @param message
	 *            the message to serialize.
	 * @param out
	 *            the outputstream where xml is written.
	 * @throws JAXBException
	 */
	public static synchronized void serialize(final EQMessage message,
			final OutputStream out) throws JAXBException {
		Marshaller marshaller = CONTEXT.createMarshaller();

		// don't include <?xml?>
		marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", false);

		marshaller.marshal(message, out);
	}
}
