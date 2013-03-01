package gov.usgs.earthquake.quakeml;

import gov.usgs.earthquake.util.IOUtil;

import java.io.File;

import org.quakeml_1_2.Quakeml;

/**
 * FileToQuakemlConverter for Quakeml files.
 * 
 * @author jmfee
 */
public class QuakemlToQuakemlConverter implements FileToQuakemlConverter {

	/**
	 * Parses a Quakeml-1.2rc3 message from a file using JAXB classes.
	 * 
	 * @return the parsed Quakeml message.
	 */
	@Override
	public Quakeml parseFile(File file) throws Exception {
		return Quakeml_1_2_Parser.parse(IOUtil.getInputStream(file));
	}

}
