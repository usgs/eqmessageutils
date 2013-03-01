package gov.usgs.earthquake.quakeml;

import gov.usgs.earthquake.util.IOUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.Test;
import org.quakeml_1_2rc3.Quakeml;

/**
 * Tests for Quakeml_1_2rc3_Parser.
 * 
 * @author jmfee
 */
public class Quakeml_1_2rc3_Test {

	/**
	 * Verify the quakeml example message parses. Also verify the event time is
	 * parsed following Quakeml rules.
	 * 
	 * @throws JAXBException
	 * @throws IOException
	 */
	@Test
	@SuppressWarnings("deprecation")
	public void fromSchemaExample() throws JAXBException, IOException {
		byte[] example = IOUtil.readFile(new File(
				"etc/quakeml_1.2rc3/qml-example-1.2-RC3.xml"));
		System.err.println("Read\n" + new String(example));
		Quakeml message = Quakeml_1_2rc3_Parser.parse(example);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Quakeml_1_2rc3_Parser.serialize(message, baos);
		System.err.println("After\n" + new String(baos.toByteArray()));

		// before, when testing on a non-UTC system, the date would be
		// interpreted as local time (per the ISO8601 spec, which is different
		// than the QuakeML spec), and the hour would change.
		Assert.assertTrue(
				"Date hour not changed after interpreting time without timezone",
				new String(baos.toByteArray())
						.indexOf("2007-10-10T14:40:39.055") != -1);
	}
}
