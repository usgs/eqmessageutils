package gov.usgs.earthquake.cube;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the CubeEvent and CubeDelete (and CubeMessage) parsing methods.
 * 
 * This test does not verify the cube message is parsed correctly, and requires
 * human verification because CUBE allows identical values to be formatted in
 * various ways.
 * 
 * @author jmfee
 */
public class CubeTest {

	public static final String CUBE_EVENT_EXAMPLE1 = "E 09082344CI21999040217051050339860-1169945017316000014001800120009004332C0002hP";
	public static final String CUBE_EVENT_EXAMPLE2 = "E meav    US3199904021838195-201884 1681247 33054 19 192283 062 387  00  B 8   v";
	public static final String CUBE_DELETE_EXAMPLE = "DE09081845CI2 EVENT CANCELLED:  (LKH)";

	@Test
	public void roundtrip() {
		System.err.println(CubeEvent.HUMAN_PARSING_GUIDE);

		testMessage(CubeEvent.EXAMPLE1);
		testMessage(CubeEvent.EXAMPLE2);
		testMessage(CubeEvent.EXAMPLE3);
		testMessage(CubeDelete.EXAMPLE);
	}

	public void testMessage(final String message) {
		CubeMessage parsed = CubeMessage.parse(message);
		// actual message may vary, but content should be equivalent
		String generated = parsed.toCUBE();

		System.err.println(message);
		System.err.println(generated);

		String regenerated = CubeMessage.parse(generated).toCUBE();
		Assert.assertEquals("Message equivalent", generated, regenerated);
	}

}
