package gov.usgs.earthquake.cube;

import gov.usgs.earthquake.event.MagnitudeType;

import java.util.Date;

/**
 * Base class for CubeMessages.
 *
 * @author jmfee
 */
public abstract class CubeMessage {

	/** When this message was sent, sometimes this is unknown. */
	protected Date sent;

	/** Who sent the message, 2 character max. */
	protected String source;
	/** Event message is about, 8 character max. */
	protected String code;
	/** ASCII version, 1 character max. */
	protected String version;

	/**
	 * Each CUBEMessage has a two character type, "E ", "DE", "TR", etc.
	 *
	 * @return message type prefix.
	 */
	public abstract String getType();

	public String toCUBE() {
		return this.getType() + leftPad(code, 8) + leftPad(source, 2)
				+ leftPad(version, 1);
	}

	/**
	 * When this message was sent.
	 *
	 * CUBE doesn't represent this in the message, but it is useful when
	 * converting between formats that do.
	 *
	 * @return sent time.
	 */
	public Date getSent() {
		return sent;
	}

	public void setSent(Date sent) {
		this.sent = sent;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Parse a cube message based on the first two characters.
	 *
	 * @param line
	 *            line to parse
	 * @return parsed message, or null if unable to parse.
	 */
	public static CubeMessage parse(final String line) {
		String type = line.substring(0, 2);
		if (type.equals(CubeEvent.TYPE)) {
			return CubeEvent.parseCUBE(line);
		} else if (type.equals(CubeDelete.TYPE)) {
			return CubeDelete.parseCUBE(line);
		} else if (type.equals(CubeTrump.TYPE)) {
			return CubeTrump.parseCUBE(line);
		} else if (type.equals(CubeAddon.TYPE)) {
			return CubeAddon.parseCUBE(line);
		}
		return null;
	}

	/**
	 * Left pad a string using spaces.
	 *
	 * See also {@link #leftPad(String, int, char)}.
	 */
	public static String leftPad(final String value, final int length) {
		return leftPad(value, length, ' ');
	}

	/**
	 * Left pad a string.
	 *
	 * Cube is a fixed width record format, so this helps with layout.
	 *
	 * @param value
	 *            value being padded.
	 * @param length
	 *            total desired length.
	 * @param padChar
	 *            character to prepend to value to reach length.
	 * @return a String that is length characters long. NOTE: if value length is
	 *         longer than length, value is truncated.
	 */
	public static String leftPad(final String value, final int length,
			final char padChar) {
		String padded = "";
		if (value != null) {
			padded = value;
		}

		while (padded.length() < length) {
			padded = padChar + padded;
		}

		// original value may have been too long
		if (padded.length() > length) {
			return padded.substring(0, length);
		}
		return padded;
	}

	/**
	 * Translate from Cube magnitude type code to MagnitudeType.
	 *
	 * @param cubeCode
	 *            cube code to translate.
	 * @return magnitude type corresponding to cube code.
	 */
	public static MagnitudeType getMagnitudeType(final String cubeCode) {
		if (cubeCode == null) {
			return null;
		}

		switch (cubeCode.charAt(0)) {
		case 'B':
			return MagnitudeType.MB;
		case 'P':
			return MagnitudeType.MP;
		case 'N':
			return MagnitudeType.MBLG;
		case 'C':
			return MagnitudeType.MC;
		case 'D':
			return MagnitudeType.MD;
		case 'E':
			return MagnitudeType.ME;
		case 'H':
			return MagnitudeType.MH;
		case 'I':
			return MagnitudeType.MI;
		case 'G':
			return MagnitudeType.MGN;
		case 'L':
			return MagnitudeType.ML;
		case 'S':
			return MagnitudeType.MS;
		case 'T':
			return MagnitudeType.MT;
		case 'O':
			return MagnitudeType.MW;
		case 'W':
			return MagnitudeType.MWR;
		default:
			return null;
		}
	}

	/**
	 * Translate from MagnitudeType to Cube magnitude type.
	 *
	 * @param magnitudeType
	 *            magnitude type to translate.
	 * @return magnitude type corresponding to cube code.
	 */
	public static String getCubeCode(final MagnitudeType magnitudeType) {
		if (magnitudeType == null) {
			return null;
		}

		switch (magnitudeType) {
		case MB:
			return "B";
		case MP:
			return "P";
		case MBLG:
			return "N";
		case MC:
			return "C";
		case MD:
			return "D";
		case ME:
			return "E";
		case MH:
			return "H";
		case MI:
			return "I";
		case MGN:
			return "G";
		case ML:
			return "L";
		case MS:
			return "S";
		case MT:
			return "T";
		case MW:
		case MWP:
		case MWW:
			return "O";
		case MWR:
			return "W";
		default:
			return null;
		}
	}

}
