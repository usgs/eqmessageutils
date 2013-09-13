package gov.usgs.earthquake.event;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import gov.usgs.ansseqmsg.EQMessage;
import gov.usgs.earthquake.cube.CubeMessage;
import gov.usgs.earthquake.eqxml.EQMessageParser;
import gov.usgs.earthquake.quakeml.Quakeml_1_2_Parser;
import gov.usgs.earthquake.util.IOUtil;

import org.quakeml_1_2.Quakeml;
import org.xml.sax.SAXException;

/**
 * Factory for parsing and converting between formats.
 * 
 * Methods on this object will either throw an exception(potentially unchecked)
 * or return null if problems occur. This may be because there is no valid
 * conversion, or because something is wrong with the object being converted.
 * 
 * @author jmfee
 */
public class Converter {

	/**
	 * Jaxb logging is noisy without this
	 */
	static {
		Logger.getLogger("com.sun.xml.bind").setLevel(Level.INFO);
	}

	/**
	 * Convert a Quakeml input stream to a Quakeml object, without validation.
	 * 
	 * @param quakeml
	 *            InputStream containing Quakeml.
	 * @return parsed Quakeml object, or null if unable to parse.
	 * @throws SAXException
	 *             when validation errors occur.
	 * @throws JAXBException
	 *             when unable to create unmarshaller.
	 * @throws IOException
	 */
	public Quakeml getQuakeml(final InputStream quakeml) throws IOException,
			JAXBException, SAXException {
		return getQuakeml(quakeml, false);
	}

	/**
	 * Convert a Quakeml input stream to a Quakeml object, without validation.
	 * 
	 * @param quakeml
	 *            InputStream containing Quakeml.
	 * @param validate
	 *            whether to validate while parsing.
	 * @return parsed Quakeml object, or null if unable to parse.
	 * @throws SAXException
	 *             when validation errors occur.
	 * @throws JAXBException
	 *             when unable to create unmarshaller.
	 * @throws IOException
	 */
	public Quakeml getQuakeml(final InputStream quakeml, final boolean validate)
			throws IOException, JAXBException, SAXException {
		return Quakeml_1_2_Parser.parse(quakeml, validate);
	}

	/**
	 * Convert a Quakeml XML string to a Quakeml, without validation.
	 * 
	 * @param quakeml
	 *            String containing Quakeml.
	 * @return parsed Quakeml object, or null if unable to parse.
	 * @throws SAXException
	 *             when validation errors occur.
	 * @throws JAXBException
	 *             when unable to create unmarshaller.
	 * @throws IOException
	 */
	public Quakeml getQuakeml(final String quakeml) throws IOException,
			JAXBException, SAXException {
		return getQuakeml(quakeml, false);
	}

	/**
	 * Convert a Quakeml XML string to a Quakeml.
	 * 
	 * @param quakeml
	 *            String containing Quakeml.
	 * @param validate
	 *            whether to validate while parsing.
	 * @return parsed Quakeml object, or null if unable to parse.
	 * @throws SAXException
	 *             when validation errors occur.
	 * @throws JAXBException
	 *             when unable to create unmarshaller.
	 * @throws IOException
	 */
	public Quakeml getQuakeml(final String quakeml, final boolean validate)
			throws IOException, JAXBException, SAXException {
		return Quakeml_1_2_Parser.parse(quakeml, validate);
	}

	/**
	 * Convert a CubeMessage to a Quakeml.
	 * 
	 * @param message
	 *            CubeMessge to convert.
	 * @return Quakeml object, or null if unable to convert.
	 * @throws Exception
	 */
	public Quakeml getQuakeml(final CubeMessage message) throws Exception {
		return new CubeToQuakemlConverter().convertCubeMessage(message);
	}

	/**
	 * Convert an EQMessage to Quakeml.
	 * 
	 * Converts EQMessage to CubeMessage, then CubeMessage to Quakeml.
	 * 
	 * @param message
	 *            EQMessage to convert.
	 * @return Quakeml object, or null if unable to convert.
	 * @throws Exception
	 */
	public Quakeml getQuakeml(final EQMessage message) throws Exception {
		CubeMessage cube = new EQMessageToCubeConverter()
				.convertEQMessage(message);
		if (cube == null) {
			return null;
		}
		return getQuakeml(cube);
	}

	/**
	 * Parse an EQXML input stream to a EQMessage object, without validation.
	 * 
	 * @param eqxml
	 *            InputStream containing EQXML.
	 * @return parsed EQMessage object, or null if unable to parse.
	 * @throws SAXException
	 *             when validation errors occur.
	 * @throws JAXBException
	 *             if unable to create unmarshaller.
	 * @throws IOException
	 */
	public EQMessage getEQMessage(final InputStream eqxml) throws IOException,
			JAXBException, SAXException {
		return getEQMessage(eqxml, false);
	}

	/**
	 * Parse an EQXML input stream to a EQMessage object.
	 * 
	 * @param eqxml
	 *            InputStream containing EQXML.
	 * @param validate
	 *            whether to validate while parsing.
	 * @return parsed EQMessage object, or null if unable to parse.
	 * @throws SAXException
	 *             when validation errors occur.
	 * @throws JAXBException
	 *             if unable to create unmarshaller.
	 * @throws IOException
	 */
	public EQMessage getEQMessage(final InputStream eqxml,
			final boolean validate) throws IOException, JAXBException,
			SAXException {
		return EQMessageParser.parse(eqxml, validate);
	}

	/**
	 * Convert an XML string to an EQMessage.
	 * 
	 * @param eqxml
	 *            String containing EQXML.
	 * @return parsed EQMessage object, or null if unable to parse.
	 * @throws SAXException
	 *             when validation errors occur.
	 * @throws JAXBException
	 *             if unable to create unmarshaller.
	 * @throws IOException
	 */
	public EQMessage getEQMessage(final String eqxml) throws IOException,
			JAXBException, SAXException {
		return getEQMessage(eqxml, false);
	}

	/**
	 * Parse an EQXML String to a EQMessage object.
	 * 
	 * @param eqxml
	 *            String containing EQXML.
	 * @param validate
	 *            whether to validate while parsing.
	 * @return parsed EQMessage object, or null if unable to parse.
	 * @throws SAXException
	 *             when validation errors occur.
	 * @throws JAXBException
	 *             if unable to create unmarshaller.
	 * @throws IOException
	 */
	public EQMessage getEQMessage(final String eqxml, final boolean validate)
			throws IOException, JAXBException, SAXException {
		return EQMessageParser.parse(eqxml, validate);
	}

	/**
	 * Convert a CubeMessage to an EQMessage.
	 * 
	 * @param message
	 *            CubeMessage to convert.
	 * @return EQMessage corresponding to CubeMessage.
	 */
	public EQMessage getEQMessage(final CubeMessage message) {
		return new CubeToEQMessageConverter().convertCubeMessage(message);
	}

	/**
	 * Convert a Quakeml to an EQMessage.
	 * 
	 * Converts Quakeml to CubeMessage, then CubeMessage to EQMessage.
	 * 
	 * @param message
	 *            Quakeml to convert.
	 * @return EQMessage corresponding to Quakeml.
	 * @throws Exception
	 */
	public EQMessage getEQMessage(final Quakeml message) throws Exception {
		CubeMessage cube = getCubeMessage(message);
		if (cube == null) {
			return null;
		}
		return getEQMessage(cube);
	}

	/**
	 * Convert a CUBE string to a CubeMessage.
	 * 
	 * @param cube
	 *            String format of CubeMessage.
	 * @return CubeMessage parsed from string
	 */
	public CubeMessage getCubeMessage(final String cube) {
		return CubeMessage.parse(cube);
	}

	/**
	 * Convert an EQMessage to a CubeMessage.
	 * 
	 * @param message
	 *            EQMessage to convert.
	 * @return CubeMessage corresponding to EQMessage.
	 */
	public CubeMessage getCubeMessage(final EQMessage message) {
		return new EQMessageToCubeConverter().convertEQMessage(message);
	}

	/**
	 * Convert a Quakeml to a CubeMessage.
	 * 
	 * @param message
	 *            Quakeml to convert.
	 * @return CubeMessage corresponding to Quakeml.
	 * @throws Exception
	 */
	public CubeMessage getCubeMessage(Quakeml message) throws Exception {
		return new QuakemlToCubeConverter().convertQuakeml(message);
	}

	/**
	 * Convert a CubeMessage to a CUBE string.
	 * 
	 * @param message
	 *            CubeMessage to convert.
	 * @return String format of CubeMessage.
	 */
	public String getString(final CubeMessage message) {
		return message.toCUBE();
	}

	/**
	 * Convert an EQMessage to an XML string, without validation.
	 * 
	 * @param message
	 *            EQMessage to convert.
	 * @return String format of EQMessage.
	 * @throws JAXBException
	 *             if unable to create marshaller.
	 * @throws SAXException
	 *             if validation errors occur.
	 */
	public String getString(final EQMessage message) throws JAXBException,
			SAXException {
		return getString(message, false);
	}

	/**
	 * Convert an EQMessage to an XML string.
	 * 
	 * @param message
	 *            EQMessage to convert.
	 * @param validate
	 *            whether to validate EQMessage.
	 * @return String format of EQMessage.
	 * @throws JAXBException
	 *             if unable to create marshaller.
	 * @throws SAXException
	 *             if validation errors occur.
	 */
	public String getString(final EQMessage message, final boolean validate)
			throws JAXBException, SAXException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		EQMessageParser.serialize(message, baos);
		return baos.toString();
	}

	/**
	 * Convert a Quakeml to an XML string, without validation.
	 * 
	 * @param message
	 *            Quakeml to convert.
	 * @return String format of Quakeml.
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public String getString(final Quakeml message) throws JAXBException,
			SAXException {
		return getString(message, false);
	}

	/**
	 * Convert a Quakeml to an XML string.
	 * 
	 * @param message
	 *            Quakeml to convert.
	 * @param validate
	 *            whether to perform validation (true) or not (false).
	 * @return String format of Quakeml.
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public String getString(final Quakeml message, final boolean validate)
			throws JAXBException, SAXException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Quakeml_1_2_Parser.serialize(message, baos, validate);
		return baos.toString();
	}

	public static String USAGE = "Usage: Converter --inputFormat=FORMAT --outputFormat=FORMAT [--validate]\n\n"
			+ "Where FORMAT is one of 'cube', 'eqxml', or 'quakeml' (without quotes).\n\n"
			+ "Converts between one message format and another, reading inputFormat from "
			+ "standard input, converting, then writing outputFormat to standard output\n";

	public static String INFORMAT_ARGUMENT = "--inputFormat=";
	public static String OUTFORMAT_ARGUMENT = "--outputFormat=";
	public static String VALIDATE_ARGUMENT = "--validate";

	/**
	 * Main method for conversion using command line.
	 * 
	 * @param args
	 * @throws JAXBException
	 * @throws Exception
	 */
	public static void main(final String[] args) throws JAXBException,
			Exception {
		String informat = null;
		String outformat = null;
		boolean validate = false;
		boolean unknownArguments = false;

		for (String arg : args) {
			if (arg.startsWith(INFORMAT_ARGUMENT)) {
				informat = arg.replace(INFORMAT_ARGUMENT, "");
			} else if (arg.startsWith(OUTFORMAT_ARGUMENT)) {
				outformat = arg.replace(OUTFORMAT_ARGUMENT, "");
			} else if (arg.equals(VALIDATE_ARGUMENT)) {
				validate = true;
			} else {
				System.err.println("Unknown argument '" + arg + "'");
				unknownArguments = true;
			}
		}

		if (informat == null || outformat == null || unknownArguments) {
			printUsage();
		}

		Converter converter = new Converter();

		String input = new String(IOUtil.readStream(System.in));

		if (informat.equals("cube")) {
			CubeMessage in = converter.getCubeMessage(input);
			if (outformat.equals("cube")) {
				System.out.println(converter.getString(in));
			} else if (outformat.equals("quakeml")) {
				System.out.println(converter.getString(
						converter.getQuakeml(in), validate));
			} else if (outformat.equals("eqxml")) {
				System.out.println(converter.getString(
						converter.getEQMessage(in), validate));
			} else {
				printUsage();
			}
		} else if (informat.equals("eqxml")) {
			EQMessage in = converter.getEQMessage(input, validate);
			if (outformat.equals("cube")) {
				System.out.println(converter.getString(converter
						.getCubeMessage(in)));
			} else if (outformat.equals("quakeml")) {
				System.out.println(converter.getString(
						converter.getQuakeml(in), validate));
			} else if (outformat.equals("eqxml")) {
				System.out.println(converter.getString(in));
			} else {
				printUsage();
			}
		} else if (informat.equals("quakeml")) {
			Quakeml in = converter.getQuakeml(input, validate);
			if (outformat.equals("cube")) {
				System.out.println(converter.getString(converter
						.getCubeMessage(in)));
			} else if (outformat.equals("quakeml")) {
				System.out.println(converter.getString(in, validate));
			} else if (outformat.equals("eqxml")) {
				System.out.println(converter.getString(
						converter.getEQMessage(in), validate));
			} else {
				printUsage();
			}
		}
	}

	/**
	 * Print the usage and exit.
	 */
	public static void printUsage() {
		System.err.println(USAGE);
		System.exit(1);
	}

}
