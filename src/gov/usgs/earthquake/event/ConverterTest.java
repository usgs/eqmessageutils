package gov.usgs.earthquake.event;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.junit.Assert;

import gov.usgs.ansseqmsg.EQMessage;
import gov.usgs.earthquake.cube.CubeDelete;
import gov.usgs.earthquake.cube.CubeEvent;
import gov.usgs.earthquake.cube.CubeMessage;
import gov.usgs.earthquake.util.IOUtil;

import org.quakeml_1_2.Quakeml;

/**
 * Test the ConverterUtility.
 * 
 * This test does not verify the cube message is parsed correctly, only that the
 * cube representation is consistent before and after converting to and from
 * quakeml.
 */
public class ConverterTest {

	/**
	 * Convert each example event and delete provided in the cube documentation.
	 * 
	 * @throws JAXBException
	 */
	@Test
	public void test() throws Exception {
		convertCubeString(CubeEvent.EXAMPLE1);
		convertCubeString(CubeEvent.EXAMPLE2);
		convertCubeString(CubeEvent.EXAMPLE3);
		convertCubeString(CubeDelete.EXAMPLE);
	}

	/**
	 * Parse a string representation of a cube message, convert to various
	 * formats, convert back to cube, and print each step.
	 * 
	 * @param cube
	 * @throws JAXBException
	 */
	public void convertCubeString(final String cubeString) throws Exception {
		Converter converter = new Converter();

		CubeMessage cubeFromString = converter.getCubeMessage(cubeString);

		// if not set, will be assigned at each conversion...
		cubeFromString.setSent(new Date());

		String cubeStringFromCube = converter.getString(cubeFromString);
		EQMessage eqMessageFromCube = converter.getEQMessage(cubeFromString);
		String eqxmlFromEQMessage = converter.getString(eqMessageFromCube);
		Quakeml quakemlFromCube = converter.getQuakeml(cubeFromString);
		String quakexmlFromCube = converter.getString(quakemlFromCube);
		Quakeml quakemlFromEQMessage = converter.getQuakeml(eqMessageFromCube);
		String quakexmlFromEQMessage = converter
				.getString(quakemlFromEQMessage);
		CubeMessage cubeFromEQMessage = converter
				.getCubeMessage(eqMessageFromCube);
		String cubeStringFromEQMessage = converter.getString(cubeFromEQMessage);
		CubeMessage cubeFromQuakeml = converter.getCubeMessage(quakemlFromCube);
		String cubeStringFromQuakeml = converter.getString(cubeFromQuakeml);

		Assert.assertEquals("Cube before and after eqmessage",
				cubeStringFromCube, cubeStringFromEQMessage);
		Assert.assertEquals("Cube before and after quakeml",
				cubeStringFromCube, cubeStringFromQuakeml);
		Assert.assertEquals("Quakeml direct from cube and via eqmessage",
				quakexmlFromCube, quakexmlFromEQMessage);

		System.err.println(cubeStringFromCube);
		System.err.println(eqxmlFromEQMessage);
		System.err.println(quakexmlFromCube);
	}

	/**
	 * Test using a specific message from nevada.
	 *
	 * This message was expected to convert from EQXML to CUBE and then Quakeml.
	 *
	 * This test is designed to reproduce 2 separate issues: 1) implicit origin
	 * preferredFlag, when omitted should still imply preferred. 2) magnitude
	 * type without Cube_Code comment, should convert.
	 *
	 * @throws Exception
	 */
	@Test
	public void testNevadaEQXML() throws Exception {
		Converter converter = new Converter();
		InputStream in = IOUtil.getInputStream(new File(
				"etc/test_messages/nn00415463.eqxml"));
		try {
			EQMessage eqxml = converter.getEQMessage(in);
			System.err.println(converter.getString(eqxml));
			CubeMessage cube = converter.getCubeMessage(eqxml);
			Assert.assertNotNull(
					"implicit preferredFlag is considered preferred", cube);
			System.err.println(converter.getString(cube));
			Quakeml quakeml = converter.getQuakeml(eqxml);
			Assert.assertNotNull("message converts to quakeml", quakeml);
			System.err.println(converter.getString(quakeml));
		} finally {
			in.close();
		}
	}
}
