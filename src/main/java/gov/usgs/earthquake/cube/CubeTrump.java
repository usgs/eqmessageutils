package gov.usgs.earthquake.cube;

/**
 * CUBE trump message.
 * 
 * NOTE: trump messages are ONLY used by QDM. The indexer no longer supports
 * these messages, and provides finer-grained, trump-like functionality using
 * "trump" type products send from an admin page.
 * 
 * <pre>
 * TR - Trump Record
 * The TR format allows a network operator to declare that the event with this event ID has priority over (or "trumps") any other event which might be considered a duplicate, even if the latter would otherwise be authoritative. This is used when, for example, an automatic, authoritative event is submitted by one network but another network has a reviewed solution that is better. The format is:
 * 
 * TpEidnumbrScV message........ to 80 characters
 * 123456789012345678901234567890
 *          1         2         3
 * 
 * a2 * Tp  = Message type = "TR" = trump corresponding events
 * a8 * Eid = Event identification number of event to become a "trump" event.
 * a2 * Sc  = Data Source
 * a1 * V   = Version; if blank, any version trumps all corresponding 
 *            events from other networks, past present and future.
 * a    ... = Optional text message
 * 
 * Event identification and data source must be the same as
 * specified for "E " message (format 2, above).
 * 
 * Example:
 * TR09081845US2 NEIC is trumping all solutions (DHO)
 * 
 * Note: when stored in the catalog directory a 20 column time stamp is put at the
 * beginning of the line.
 * </pre>
 * 
 * @author jmfee
 */
public class CubeTrump extends CubeMessage {

	public static final String TYPE = "TR";

	private String message;

	public CubeTrump() {
	}

	/**
	 * Construct a CUBETrump from a CUBEEvent.
	 * 
	 * @param e
	 *            event that is adding trump flag
	 * @param message
	 *            terse message describing trump
	 */
	public CubeTrump(CubeEvent e, final String message) {
		this.setCode(e.getCode());
		this.setSource(e.getSource());
		this.setVersion(e.getVersion());
		this.setMessage(message);
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String toCUBE() {
		StringBuffer buf = new StringBuffer();
		buf.append(super.toCUBE());

		if (message != null) {
			buf.append(" ").append(message);
		}

		return buf.toString();
	}

	public static CubeMessage parseCUBE(String line) {
		String type = line.substring(0, 2);
		if (TYPE.equals(type)) {
			// not a cube delete
			throw new IllegalArgumentException(
					"Unexpected CUBE type, expected '" + TYPE + "', got '"
							+ type + "'");
		}

		String code = line.substring(2, 10).trim();
		String source = line.substring(10, 12).trim();
		String version = line.substring(12, 13).trim();
		String message = line.substring(13).trim();

		CubeTrump trump = new CubeTrump();
		trump.setCode("".equals(code) ? null : code);
		trump.setSource("".equals(source) ? null : source);
		trump.setVersion("".equals(version) ? null : version);
		trump.setMessage("".equals(message) ? null : message);
		return trump;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
