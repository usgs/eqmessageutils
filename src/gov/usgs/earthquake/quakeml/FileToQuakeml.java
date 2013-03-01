package gov.usgs.earthquake.quakeml;

import java.io.File;

import org.quakeml_1_2.Quakeml;

/**
 * Command line utility to run the FileToQuakemlConverter interface.
 * 
 * Run for usage: <code>
 * java -cp MessageUtils.jar gov.usgs.earthquake.quakeml.FileToQuakeml
 * </code>
 * 
 * @author jmfee
 */
public class FileToQuakeml {

	public static final String USAGE = "Usage:  FileToQuakeml --parser=PARSER --file=FILE\n\n"
			+ "\tPARSER - fully qualified class name that implements the interface\n"
			+ "\t\t\tgov.usgs.earthquake.quakeml.FileToQuakemlConverter\n"
			+ "\tFILE - path to file or directory (depending on what PARSER expects)\n";

	public static final String FILE_ARGUMENT = "--file=";
	public static final String PARSER_ARGUMENT = "--parser=";

	public static void main(final String[] args) throws Exception {
		File file = null;
		FileToQuakemlConverter parser = null;

		for (String arg : args) {
			if (arg.startsWith(FILE_ARGUMENT)) {
				file = new File(arg.replace(FILE_ARGUMENT, ""));
			} else if (arg.startsWith(PARSER_ARGUMENT)) {
				String parserClass = arg.replace(PARSER_ARGUMENT, "");
				parser = (FileToQuakemlConverter) Class.forName(parserClass)
						.newInstance();
			}
		}

		if (file == null || parser == null) {
			System.err.println(USAGE);
			System.exit(1);
		}

		Quakeml quakeml = parser.parseFile(file);
		Quakeml_1_2_Parser.serialize(quakeml, System.out);
	}

}
