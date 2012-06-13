package gov.usgs.earthquake.focalmechanism;

import gov.usgs.earthquake.quakeml.Quakeml_1_2rc3_Parser;

import java.io.File;
import org.quakeml_1_2rc3.Quakeml;
import org.junit.Test;
import org.junit.Assert;

public class VaxTest {
	@Test
	public void testVax(){
		String vaxfilename = "etc/vax/c0006zwa.wcmt";
		File file = new File(vaxfilename);
		VaxToQuakemlConverter vaxreader = new VaxToQuakemlConverter();
		try{
			System.out.format("Parsing Vax format...\n");
			Quakeml quake = vaxreader.parseFile(file);
			System.out.format("Serializing Vax format...\n");
			Quakeml_1_2rc3_Parser.serialize(quake, System.out);
			System.out.format("\nDone serializing Vax format...\n");
		}
		catch (Exception e){
			System.out.format("Caught Exception: '%s'\n", e.getMessage());
			Assert.fail(e.getMessage());
		}
	}
		
}