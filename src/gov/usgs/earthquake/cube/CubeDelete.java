package gov.usgs.earthquake.cube;

/**
 * CUBE delete message.
 * 
 * <pre>
 * DE - Delete Earthquake
 *     The DE format is used to delete an earthquake. A delete message deletes the same, and all lower, versions of an earthquake. The format is:
 * 
 *     TpEidnumbrScV message........ to 80 characters
 *     123456789012345678901234567890
 *              1         2         3
 * 
 *     a2 * Tp  = Message type = "DE" = delete seismic event
 *     a8 * Eid = Event identification number of event to delete
 *     a2 * Sc  = Data Source
 *     a1 * V   = Version, if version is blank all current versions are deleted.
 *     a    ... = Optional text message
 * 
 *     Event identification and data source must be the same as
 *     specified for "E " message (format 2, above).  The
 *     identified event will be deleted by the receiver.
 * 
 *     Example:
 *     DE09081845CI2 EVENT CANCELLED:  (LKH)
 * 
 *     Note: when stored in the catalog directory a 20 column time stamp is put at the
 *     beginning of the line.
 * </pre>
 * 
 * @author jmfee
 */
public class CubeDelete extends CubeMessage {
	public static final String EXAMPLE = "DE09081845CI2 EVENT CANCELLED:  (LKH)";

	public static final String TYPE = "DE";

	private String message;

	public CubeDelete() {
	}

	/**
	 * Construct a CUBEDelete from a CUBEEvent.
	 * 
	 * @param e
	 *            event to delete
	 * @param message
	 *            terse message describing delete
	 */
	public CubeDelete(CubeEvent e, final String message) {
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
		if (!TYPE.equals(type)) {
			// not a cube delete
			throw new IllegalArgumentException(
					"Unexpected CUBE type, expected '" + TYPE + "', got '"
							+ type + "'");
		}

		String code = line.substring(2, 10).trim().toLowerCase();
		String source = line.substring(10, 12).trim().toLowerCase();
		String version = line.substring(12, 13).trim();
		String message = line.substring(13).trim();

		CubeDelete delete = new CubeDelete();
		delete.setCode(code.equals("") ? null : code);
		delete.setSource(source.equals("") ? null : source);
		delete.setVersion(version.equals("") ? null : version);
		delete.setMessage(message.equals("") ? null : message);
		return delete;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
