package gov.usgs.earthquake.event;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;
import org.quakeml_1_2.Quakeml;

import gov.usgs.earthquake.cube.CubeDelete;
import gov.usgs.earthquake.cube.CubeEvent;
import gov.usgs.earthquake.cube.CubeMessage;
import gov.usgs.earthquake.quakeml.Quakeml_1_2_Parser;

/**
 * Test the CubeToQuakeml and EQMessageToQuakeml converters.
 * 
 * This test does not verify the cube message is parsed correctly, only that the
 * cube representation is consistent before and after converting to and from
 * quakeml.
 * 
 * @author jmfee
 */
public class CubeToQuakemlConverterTest {

	/**
	 * Convert each example event and delete provided in the cube documentation.
	 * 
	 * @throws JAXBException
	 */
	@Test
	public void convertExampleCubes() throws Exception {
		convertCubeString(CubeEvent.EXAMPLE1);
		convertCubeString(CubeEvent.EXAMPLE2);
		convertCubeString(CubeDelete.EXAMPLE);
	}

	/**
	 * Parse a string representation of a cube message, convert to Quakeml,
	 * convert back to cube, and print each step.
	 * 
	 * @param cube
	 * @throws JAXBException
	 */
	public void convertCubeString(final String cube) throws Exception {
		System.err.println(CubeEvent.HUMAN_PARSING_GUIDE);

		System.err.println("Input CUBE");
		System.err.println(cube);

		CubeMessage event = CubeEvent.parse(cube);
		String before = event.toCUBE();
		System.err.println("Parsed CUBE");
		System.err.println(before);

		Quakeml converted = new CubeToQuakemlConverter()
				.convertCubeMessage(event);
		System.err.println("Converted to Quakeml");
		Quakeml_1_2_Parser.serialize(converted, System.err, true);
		System.err.println();

		CubeMessage reconverted = new QuakemlToCubeConverter()
				.convertQuakeml(converted);
		String after = reconverted.toCUBE();
		System.err.println("After reconversion");
		System.err.println(after);

		Assert.assertEquals(
				"Cube message is equal before and after conversion to and from Quakeml",
				before, after);
	}
}
