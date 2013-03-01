package gov.usgs.earthquake.event;

import java.io.ByteArrayOutputStream;
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
	 * Convert a Quakeml input stream to a Quakeml object.
	 * 
	 * @param quakeml
	 *            InputStream containing Quakeml.
	 * @return parsed Quakeml object, or null if unable to parse.
	 */
	public Quakeml getQuakeml(final InputStream quakeml) {
		return Quakeml_1_2_Parser.parse(quakeml);
	}

	/**
	 * Convert a Quakeml XML string to a Quakeml.
	 * 
	 * @param quakeml
	 *            String containing Quakeml.
	 * @return parsed Quakeml object, or null if unable to parse.
	 */
	public Quakeml getQuakeml(final String quakeml) {
		return Quakeml_1_2_Parser.parse(quakeml);
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
	 * Parse an EQXML input stream to a EQMessage object.
	 * 
	 * @param eqxml
	 *            InputStream containing EQXML.
	 * @return parsed EQMessage object, or null if unable to parse.
	 */
	public EQMessage getEQMessage(final InputStream eqxml) {
		return EQMessageParser.parse(eqxml);
	}

	/**
	 * Convert an XML string to an EQMessage.
	 * 
	 * @param eqxml
	 *            String containing EQXML.
	 * @return parsed EQMessage object, or null if unable to parse.
	 */
	public EQMessage getEQMessage(final String eqxml) {
		return EQMessageParser.parse(eqxml);
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
	 * Convert an EQMessage to an XML string.
	 * 
	 * @param message
	 *            EQMessage to convert.
	 * @return String format of EQMessage.
	 * @throws JAXBException
	 */
	public String getString(final EQMessage message) throws JAXBException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		EQMessageParser.serialize(message, baos);
		return baos.toString();
	}

	/**
	 * Convert a Quakeml to an XML string.
	 * 
	 * @param message
	 *            Quakeml to convert.
	 * @return String format of Quakeml.
	 * @throws JAXBException
	 */
	public String getString(final Quakeml message) throws JAXBException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Quakeml_1_2_Parser.serialize(message, baos);
		return baos.toString();
	}

	public static String USAGE = "Usage: Converter --inputFormat=FORMAT --outputFormat=FORMAT\n\n"
			+ "Where FORMAT is one of 'cube', 'eqxml', or 'quakeml' (without quotes).\n\n"
			+ "Converts between one message format and another, reading inputFormat from "
			+ "standard input, converting, then writing outputFormat to standard output\n";

	public static String INFORMAT_ARGUMENT = "--inputFormat=";
	public static String OUTFORMAT_ARGUMENT = "--outputFormat=";

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

		for (String arg : args) {
			if (arg.startsWith(INFORMAT_ARGUMENT)) {
				informat = arg.replace(INFORMAT_ARGUMENT, "");
			} else if (arg.startsWith(OUTFORMAT_ARGUMENT)) {
				outformat = arg.replace(OUTFORMAT_ARGUMENT, "");
			}
		}

		if (informat == null || outformat == null) {
			printUsage();
		}

		Converter converter = new Converter();

		String input = new String(IOUtil.readStream(System.in));

		if (informat.equals("cube")) {
			CubeMessage in = converter.getCubeMessage(input);
			if (outformat.equals("cube")) {
				System.out.println(converter.getString(in));
			} else if (outformat.equals("quakeml")) {
				System.out
						.println(converter.getString(converter.getQuakeml(in)));
			} else if (outformat.equals("eqxml")) {
				System.out.println(converter.getString(converter
						.getEQMessage(in)));
			} else {
				printUsage();
			}
		} else if (informat.equals("eqxml")) {
			EQMessage in = converter.getEQMessage(input);
			if (outformat.equals("cube")) {
				System.out.println(converter.getString(converter
						.getCubeMessage(in)));
			} else if (outformat.equals("quakeml")) {
				System.out
						.println(converter.getString(converter.getQuakeml(in)));
			} else if (outformat.equals("eqxml")) {
				System.out.println(converter.getString(in));
			} else {
				printUsage();
			}
		} else if (informat.equals("quakeml")) {
			Quakeml in = converter.getQuakeml(input);
			if (outformat.equals("cube")) {
				System.out.println(converter.getString(converter
						.getCubeMessage(in)));
			} else if (outformat.equals("quakeml")) {
				System.out.println(converter.getString(in));
			} else if (outformat.equals("eqxml")) {
				System.out.println(converter.getString(converter
						.getEQMessage(in)));
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
