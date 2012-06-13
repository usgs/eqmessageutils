package gov.usgs.earthquake.event;

import java.io.File;

import org.quakeml_1_2rc3.Quakeml;

import gov.usgs.earthquake.cube.CubeDelete;
import gov.usgs.earthquake.cube.CubeEvent;
import gov.usgs.earthquake.quakeml.FileToQuakemlConverter;
import gov.usgs.earthquake.util.IOUtil;

/**
 * FileToQuakemlConverter for CUBE, EQMessage, and Quakeml files.
 * 
 * Supports Cube ("E ", "DE"), EQXML, and QuakeML1.2rc3 formatted files.
 * 
 * @author jmfee
 */
public class EidsToQuakemlConverter implements FileToQuakemlConverter {

	// converter object.
	private static final Converter converter = new Converter();

	/**
	 * Parse a Cube/EQXML/Quakeml file into a Quakeml object.
	 * 
	 * @param file
	 *            the file to parse.
	 */
	@Override
	public Quakeml parseFile(File file) throws Exception {
		Quakeml quakeml = null;
		String contents = new String(IOUtil.readFile(file));

		if (contents.startsWith(CubeEvent.TYPE)
				|| contents.startsWith(CubeDelete.TYPE)) {
			// cube message
			quakeml = converter.getQuakeml(converter.getCubeMessage(contents));
		} else if (contents.startsWith("<")) {
			// xml file
			try {
				// be optimistic that it is quakeml
				quakeml = converter.getQuakeml(contents);
			} catch (Exception e) {
				quakeml = converter
						.getQuakeml(converter.getEQMessage(contents));
			}
		}

		return quakeml;
	}

}
