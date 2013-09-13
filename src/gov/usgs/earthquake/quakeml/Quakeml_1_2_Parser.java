package gov.usgs.earthquake.quakeml;

import gov.usgs.earthquake.util.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.quakeml_1_2.Quakeml;
import org.xml.sax.SAXException;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Parse Quakeml 1.2rc3 messages.
 * 
 * @see org.quakeml_1_2rc3.Quakeml
 */
public class Quakeml_1_2_Parser extends NamespacePrefixMapper {

	/**
	 * Use "q" namespace prefix for quakeml.
	 * 
	 * @see #getPreferredPrefix(String, String, boolean)
	 */
	private static final NamespacePrefixMapper NAMESPACE_MAPPER = new Quakeml_1_2_Parser();

	/** Quakeml BED namespace. */
	public static final String QUAKEML_1_2_BED_NAMESPACE = "http://quakeml.org/xmlns/bed/1.2";
	public static final String QUAKEML_1_2_BED_PREFIX = "";

	/** Quakeml namespace. */
	public static final String QUAKEML_1_2_NAMESPACE = "http://quakeml.org/xmlns/quakeml/1.2";
	public static final String QUAKEML_1_2_PREFIX = "q";

	/** Catalog namespace. */
	public static final String ANSS_EVENTID_NAMESPACE = "http://anss.org/xmlns/catalog/0.1";
	public static final String ANSS_EVENTID_PREFIX = "catalog";

	/** JAXB Context for parsing. */
	public static final JAXBContext CONTEXT;
	static {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance("org.quakeml_1_2");
		} catch (Exception e) {
			System.err.println("Unable to create org.quakeml JAXBContext");
			e.printStackTrace();
			context = null;
		}
		CONTEXT = context;
	}

	/** Path to the EQXML schema. */
	public static String SCHEMA_RESOURCE_PATH = "quakeml_1.2/QuakeML-1.2_jaxb.xsd";

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
			SCHEMA = schemaFactory.newSchema(Quakeml_1_2_Parser.class
					.getClassLoader().getResource(SCHEMA_RESOURCE_PATH));
		}
		return SCHEMA;
	}

	/**
	 * Parse a Quakeml 1.2 from an InputStream-able object, without validation.
	 * 
	 * @param source
	 *            InputStream-able object, as defined by Utility.getInputStream.
	 * @return an EQMessage object, or null if an error occurred.
	 * @throws SAXException
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static synchronized Quakeml parse(final Object source)
			throws IOException, JAXBException, SAXException {
		return parse(source, false);
	}

	/**
	 * Parse a Quakeml 1.2 from an InputStream-able object.
	 * 
	 * @param source
	 *            InputStream-able object, as defined by Utility.getInputStream.
	 * @param validate
	 *            Whether to validate while parsing (true) or not (false).
	 * @return an EQMessage object, or null if an error occurred.
	 * @throws IOException
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static synchronized Quakeml parse(final Object source,
			final boolean validate) throws IOException, JAXBException,
			SAXException {
		Quakeml message = null;
		InputStream in = null;

		try {
			in = IOUtil.getInputStream(source);
			// to be thread safe, must create unmarshaller per call
			Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
			if (unmarshaller != null) {
				if (validate) {
					unmarshaller.setSchema(getSchema());
				}
				message = (Quakeml) unmarshaller.unmarshal(in);
			}
		} finally {
			IOUtil.close(in);
		}

		return message;
	}

	/**
	 * Convert a Quakeml 1.2 to XML, without validation.
	 * 
	 * @param message
	 *            the message to serialize.
	 * @param out
	 *            the outputstream where xml is written.
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static synchronized void serialize(final Quakeml message,
			final OutputStream out) throws JAXBException, SAXException {
		serialize(message, out, false);
	}

	/**
	 * Convert a Quakeml 1.2 to XML.
	 * 
	 * @param message
	 *            the message to serialize.
	 * @param out
	 *            the outputstream where xml is written.
	 * @param validate
	 *            whether to validate the message being serialized (true) or not
	 *            (false).
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static synchronized void serialize(final Quakeml message,
			final OutputStream out, final boolean validate)
			throws JAXBException, SAXException {
		// to be thread safe, must create marshaller per call
		Marshaller marshaller = CONTEXT.createMarshaller();

		// use #getPreferredPrefix to override default JAXB namespaces
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				NAMESPACE_MAPPER);

		// don't include <?xml?>
		marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", false);

		// because of how the quakeml schema is written, we have to do this bs
		// to convince JAXB marshall that quakeml can be used as a root element
		XmlType quakemlXmlType = Quakeml.class.getAnnotation(XmlType.class);
		JAXBElement<Quakeml> element = new JAXBElement<Quakeml>(new QName(
				quakemlXmlType.namespace(), "quakeml"), Quakeml.class, message);

		if (validate) {
			marshaller.setSchema(getSchema());
		}

		marshaller.marshal(element, out);
	}

	/**
	 * Explicitly set namespace prefixes to use.
	 */
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion,
			boolean requirePrefix) {
		if (QUAKEML_1_2_NAMESPACE.equals(namespaceUri)) {
			return QUAKEML_1_2_PREFIX;
		} else if (QUAKEML_1_2_BED_NAMESPACE.equals(namespaceUri)) {
			return QUAKEML_1_2_BED_PREFIX;
		} else if (ANSS_EVENTID_NAMESPACE.equals(namespaceUri)) {
			return ANSS_EVENTID_PREFIX;
		} else {
			return suggestion;
		}
	}
}
