package gov.usgs.earthquake.event;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import gov.usgs.ansseqmsg.EQMessage;
import gov.usgs.earthquake.cube.CubeAddon;
import gov.usgs.earthquake.cube.CubeDelete;
import gov.usgs.earthquake.cube.CubeEvent;
import gov.usgs.earthquake.cube.CubeMessage;
import gov.usgs.earthquake.eqxml.EQMessageParser;

/**
 * Test the CubeToEQMessage and EQMessageToCube converters.
 * 
 * This test does not verify the cube message is parsed correctly, only that the
 * cube representation is consistent before and after converting to and from
 * eqxml.
 * 
 * @author jmfee
 */
public class CubeToEQMessageConverterTest {

	/**
	 * Convert each example event and delete provided in the cube documentation.
	 * 
	 * @throws JAXBException
	 * @throws SAXException 
	 * @throws IOException 
	 */
	@Test
	public void convertExampleCubes() throws JAXBException, SAXException {
		convertCubeString(CubeEvent.EXAMPLE1);
		convertCubeString(CubeEvent.EXAMPLE2);
		convertCubeString(CubeDelete.EXAMPLE);
		convertCubeString(CubeAddon.EXAMPLE);
	}

	/**
	 * Parse a string representation of a cube message, convert to EQXML,
	 * convert back to cube, and print each step.
	 * 
	 * @param cube
	 * @throws JAXBException
	 * @throws SAXException 
	 */
	public void convertCubeString(final String cube) throws JAXBException, SAXException {
		System.err.println(CubeEvent.HUMAN_PARSING_GUIDE);

		// parse cube
		System.err.println("Input CUBE");
		System.err.println(cube);
		CubeMessage event = CubeMessage.parse(cube);

		// save current representation (after parsing)
		String before = event.toCUBE();
		System.err.println("CUBE after parsing");
		System.err.println(before);

		// convert to eqxml
		EQMessage converted = new CubeToEQMessageConverter()
				.convertCubeMessage(event);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		EQMessageParser.serialize(converted, baos, true);
		System.err.println("Converted to EQXML");
		System.err.println(baos.toString());

		// convert eqxml back to cube
		CubeMessage reconverted = new EQMessageToCubeConverter()
				.convertEQMessage(converted);
		System.err.println("Converted back to CUBE");
		System.err.println(reconverted.toCUBE());

		// compare
		String after = reconverted.toCUBE();
		Assert.assertEquals(
				"Cube message is equal before and after EQmessage conversion",
				before, after);

		System.err.println();
	}
}
