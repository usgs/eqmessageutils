package gov.usgs.earthquake.quakeml;

import java.io.File;

import org.quakeml_1_2.Quakeml;

/**
 * Interface for parsers to generate Quakeml objects.
 * 
 * @author jmfee
 */
public interface FileToQuakemlConverter {

	/**
	 * Read a File and translate to Quakeml.
	 * 
	 * File may be a directory, depending on the implementor.
	 * 
	 * @param file
	 *            the file to parse, may be a directory.
	 * @return Quakeml equivalent of file.
	 */
	public Quakeml parseFile(final File file) throws Exception;

}
