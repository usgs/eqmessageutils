package gov.usgs.earthquake.focalmechanism;

import gov.usgs.earthquake.quakeml.Quakeml_1_2rc3_Parser;

import java.io.File;

import org.junit.Test;
import org.junit.Assert;
import org.quakeml_1_2rc3.Quakeml;

public class NDKTest {
	@Test
	public void testNDK(){
		String ndkfilename = "etc/ndk/sample.ndk";
		File file = new File(ndkfilename);
		NDKToQuakemlConverter ndkreader = new NDKToQuakemlConverter();
		try{
			Quakeml quake = ndkreader.parseFile(file);
			System.out.format("Serializing NDK format...\n");
			Quakeml_1_2rc3_Parser.serialize(quake, System.out);
			System.out.format("\nDone serializing NDK format...\n");
		}
		catch (Exception e){
			System.out.format("Caught Exception: '%s'\n", e.getMessage());
			Assert.fail(e.getMessage());
		}
	}
		
}
