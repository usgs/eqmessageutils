package gov.usgs.earthquake.focalmechanism;

import gov.usgs.earthquake.quakeml.Quakeml_1_2rc3_Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import org.quakeml_1_2rc3.Quakeml;
import org.junit.Test;
import org.junit.Assert;

public class VaxTest {
	@Test
	public void testVax(){
		File folder = new File("etc/vax2/");
		if (!folder.isDirectory()){
			folder = new File("etc/vax/");
		}
		File file;
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()){
				file = listOfFiles[i];
				String fname = file.getAbsolutePath();
				String ext = getExtension(fname);
				if (ext.equals("xml")){
					continue;					
				}
				String outfilename = createFile(file.getAbsolutePath(),"xml");
				try{
					FileOutputStream outstream = new FileOutputStream(outfilename);
					VaxToQuakemlConverter vaxreader = new VaxToQuakemlConverter();
					try{
						System.out.format("Parsing Vax format from file %s\n",file.getName());
						Quakeml quake = vaxreader.parseFile(file);
						System.out.format("Serializing Vax format...\n");
						Quakeml_1_2rc3_Parser.serialize(quake, outstream);
						System.out.format("\nDone serializing Vax format...\n");
						outstream.close();
					}
					catch (Exception e){
						System.out.format("Caught Exception: '%s' on file '%s'\n", e.getMessage(),file.getAbsolutePath());
						Assert.fail(e.getMessage());
					}
				}
				catch (FileNotFoundException fnfe){
					continue;
				}
				
			}
		}
//		String vaxfilename = "etc/vax/b000dc6x.wcmt";
//		File file = new File(vaxfilename);
//		VaxToQuakemlConverter vaxreader = new VaxToQuakemlConverter();
//		try{
//			System.out.format("Parsing Vax format...\n");
//			Quakeml quake = vaxreader.parseFile(file);
//			System.out.format("Serializing Vax format...\n");
//			Quakeml_1_2rc3_Parser.serialize(quake, System.out);
//			System.out.format("\nDone serializing Vax format...\n");
//		}
//		catch (Exception e){
//			System.out.format("Caught Exception: '%s'\n", e.getMessage());
//			Assert.fail(e.getMessage());
//		}
	}
	//Let's create some basic file handling methods, because Java doesn't provide them!
	public String getExtension(String filename){
		int dot = filename.lastIndexOf(".");
		return filename.substring(dot+1);
	}
	public String getFileBase(String filename){
		int dot = filename.lastIndexOf(".");
	    int sep = filename.lastIndexOf(File.pathSeparator);
	    return filename.substring(sep + 1, dot);
	}
	public String createFile(String filename,String extension){
		String base = getFileBase(filename);
		return base + "." + extension;
	}
}