package gov.usgs.earthquake.cube;

/**
 * CUBE addon message.
 * 
 * <pre>
 * LI - Link to Addon
 *     The LI format is used to issue a link to additional information (addons) about an event that are available on some accessible web site. Addons can be updated by using higher version numbers. The file name gives the data source, the eventid, the version number, the addon type, and is followed by .add or .del (if it is a delete addon message). The format is:
 *     TpEidnumbrScVV message........ must be on one line.
 *     123456789012345678901234567890
 *              1         2         3
 * 
 *     a2 * Tp =  Message type = "LI" text message
 *     a8 * Eid = Event identification number of event to delete
 *     a2 * Sc  = Data Source
 *     a2 * VV  = Version # of the addon comment.
 *     a    ... = message contains three strings separated by spaces:
 *                1. a string that defines the type of addon, this string should be unique amoung
 *                   the addons being used.  See the recenteqs system for more information.
 *                2. the url where the addon information is available
 *                3. the text that will be used to describe what is available at the url.
 *                   This third string only may contain spaces.  If the text is "delete" the
 *                   addon is deleted.
 * 
 *     Example:
 *     LI 006729 NC01 fm http://whatever/whoknows This is a test
 * 
 *     Example as stored in eqaddons/nc006729.01.fm.add:
 *     ::::::::::::::
 *     event addon type fm version 01 issued at 1999/03/31_20:51:41:
 *     "http://whatever/whoknows""This is a test"
 * 
 *     Example:
 *     LI 006729 NC01 fm http://whatever/whoknows delete:
 * 
 *     Example as stored in eqaddons/nc006729.01.fm.del:
 *     event addon type fm version 01 issued at 1999/03/31_21:00:17:
 *     "http://whatever/whoknows""delete"
 * </pre>
 * 
 * @author jmfee
 */
public class CubeAddon extends CubeMessage {
	public static final String EXAMPLE = "LI 006729 NC01 fm http://whatever/whoknows This is a test";

	public static final String TYPE = "LI";
	public static final String DELETE_TEXT = "delete";

	private String addonType;
	private String addonUrl;
	private String addonText;

	public CubeAddon() {
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String toCUBE() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getType() + leftPad(code, 8) + leftPad(source, 2)
				+ leftPad(version, 2, '0'));

		buf.append(" ").append(addonType);
		buf.append(" ").append(addonUrl);
		buf.append(" ").append(addonText);

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
		String version = line.substring(12, 14).trim();

		String message = line.substring(14).trim();
		String[] parts = message.split(" ", 3);
		if (parts.length != 3) {
			throw new IllegalArgumentException(
					"Unexpected cube addon format: '" + line + "'");
		}

		CubeAddon addon = new CubeAddon();
		addon.setCode(code.equals("") ? null : code);
		addon.setSource(source.equals("") ? null : source);
		addon.setVersion(version.equals("") ? null : version);
		addon.setAddonType(parts[0]);
		addon.setAddonUrl(parts[1]);
		addon.setAddonText(parts[2]);

		return addon;
	}

	public String getAddonType() {
		return addonType;
	}

	public void setAddonType(String addonType) {
		this.addonType = addonType;
	}

	public String getAddonUrl() {
		return addonUrl;
	}

	public void setAddonUrl(String addonUrl) {
		this.addonUrl = addonUrl;
	}

	public String getAddonText() {
		return addonText;
	}

	public void setAddonText(String addonText) {
		this.addonText = addonText;
	}

	public boolean isDeleted() {
		return this.addonText.equals(DELETE_TEXT);
	}

	public void setDeleted() {
		this.addonText = DELETE_TEXT;
	}

}
