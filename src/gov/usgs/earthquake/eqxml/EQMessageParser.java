package gov.usgs.earthquake.eqxml;

import gov.usgs.ansseqmsg.EQMessage;
import gov.usgs.earthquake.util.IOUtil;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

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

	/** Path to the EQXML schema. */
	public static String SCHEMA_RESOURCE_PATH = "eqxml/ANSS_EQ_XML_doc.xsd";

	/** Only load the schema once. */
	private static Schema SCHEMA = null;

	/**
	 * Get an EQXML Schema object, for use with validation.
	 * 
	 * @return Schema object.
	 * @throws SAXException
	 *             if unable to load schema.
	 */
	public static synchronized Schema getSchema() throws SAXException {
		if (SCHEMA == null) {
			SchemaFactory schemaFactory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			SCHEMA = schemaFactory.newSchema(EQMessageParser.class
					.getClassLoader().getResource(SCHEMA_RESOURCE_PATH));
		}
		return SCHEMA;
	}

	/**
	 * Parse and EQMessage from an InputStream-able object, without validation.
	 * 
	 * @param source
	 *            InputStream-able object, as defined by Utility.getInputStream.
	 * @return an EQMessage object, or null if an error ocurred.
	 */
	public static synchronized EQMessage parse(final Object source) {
		return parse(source, false);
	}

	/**
	 * Parse an EQMessage from an InputStream-able object.
	 * 
	 * @param source
	 *            InputStream-able object, as defined by Utility.getInputStream.
	 * @param validate
	 *            whether to use validation (true) or not (false).
	 * @return an EQMessage object, or null if an error occurred.
	 */
	public static synchronized EQMessage parse(final Object source,
			final boolean validate) {
		EQMessage message = null;
		InputStream in = null;

		try {
			in = IOUtil.getInputStream(source);
			Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
			if (unmarshaller != null) {
				if (validate) {
					unmarshaller.setSchema(getSchema());
				}
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
	 * Convert an EQMessage to XML without validation.
	 * 
	 * @param message
	 *            the message to serialize.
	 * @param out
	 *            the outputstream where xml is written.
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static synchronized void serialize(final EQMessage message,
			final OutputStream out) throws JAXBException, SAXException {
		serialize(message, out, false);
	}

	/**
	 * Convert an EQMessage to XML.
	 * 
	 * @param message
	 *            the message to serialize.
	 * @param out
	 *            the outputstream where xml is written.
	 * @param validate
	 *            whether to use validation (true) or not (false).
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static synchronized void serialize(final EQMessage message,
			final OutputStream out, final boolean validate)
			throws JAXBException, SAXException {
		Marshaller marshaller = CONTEXT.createMarshaller();

		// don't include <?xml?>
		marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", false);

		if (validate) {
			marshaller.setSchema(getSchema());
		}

		marshaller.marshal(message, out);
	}

}
