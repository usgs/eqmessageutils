package gov.usgs.earthquake.cube;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * CUBE checksum utility.
 * 
 * This is originally from QDMUtils, and kept separate for simplicity.
 */
public class CubeChecksum {

	/**
	 * Method to compute checksum to characterize a character string. Adapted
	 * from Andy Michael's cksum.c.
	 * 
	 * @param s
	 *            String of characters to compute cksum of
	 * @return check sum integer
	 */
	public static int checksum(String s) {
		int c;
		int sum = 0;

		byte bs[] = s.getBytes();
		int len = bs.length;
		for (int i = 0; i < len; i++) {
			if ((sum & 1) != 0) {
				sum = (sum >> 1) + 0x8000;
			} else {
				sum >>= 1;
			}
			c = bs[i];
			sum += c;
			sum &= 0xFFFF;
		}

		return 36 + sum % 91;
	}

	/**
	 * Same as checksum, but return a string.
	 * 
	 * @param s
	 *            String to compute checksum of.
	 * @return computed checksum as string.
	 */
	public static String strChecksum(String s) {
		return Character.valueOf((char) checksum(s)).toString();
	}

	/**
	 * Read cube messages from stdin.
	 * 
	 * Checksums are computed based on the first 79 characters of the input
	 * line, and the line with computed checksum is then output.
	 * 
	 * @param args
	 *            no arguments used.
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		String line;
		while ((line = reader.readLine()) != null) {
			line = line.substring(0, 79);
			System.out.println(line + strChecksum(line));
		}
	}

}