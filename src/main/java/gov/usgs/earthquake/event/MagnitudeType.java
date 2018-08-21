package gov.usgs.earthquake.event;

import java.math.BigDecimal;

/**
 * An enumeration of magnitude types.
 *
 * @author jmfee
 */
public enum MagnitudeType {

	/** Mb */
	MB("Mb", "Body wave", new BigDecimal("3.5"), new BigDecimal("6.2")),

	/** Mp */
	MP("Mp", "P-wave (Doug Neuhauser, UCB)", new BigDecimal("3.5"),
			new BigDecimal("6.2")),

	/** MbLg */
	MBLG("MbLg", "\"Nuttli\" surface wave", null, new BigDecimal("4.8")),

	/** Md */
	MD("Md", "Coda duration", null, new BigDecimal("4.7")),

	/** Mc */
	MC("Mc", "Coda amplitude", null, new BigDecimal("4.7")),

	/** Me - this is an invalid type, should this be removed? */
	ME("Me", "Energy", new BigDecimal("-99"), new BigDecimal("-99")),

	/** Mh */
	MH("Mh", "\"Human\" assigned", null, null),

	/** Mi */
	MI("Mi", "\"Tsuboi\" moment", new BigDecimal("4.6"), null),

	/** Ml */
	ML("Ml", "Local", null, new BigDecimal("6.6")),

	/** Mgn */
	MGN("Mgn", "MAGNUM (psuedo-empirical local)", null, new BigDecimal("6.6")),

	/** Ms */
	MS("Ms", "Surface wave", new BigDecimal("4.2"), new BigDecimal("8")),

	/** Mt */
	MT("Mt", "Teleseismic moment", new BigDecimal("5.5"), new BigDecimal("7.5")),

	/** Mw */
	MW("Mw", "Moment", new BigDecimal("5.8"), null),

	/** Mwp */
	MWP("Mwp", "P-wave moment", null, null),

	/** Mwr */
	MWR("Mwr", "Regional moment", null, new BigDecimal("7")),

	/** Mww */
	MWW("Mww", "W-phase moment", new BigDecimal("5.8"), null);


	/** Abbreviation for this type. */
	private final String abbreviation;
	/** Full name for this type. */
	private final String description;
	/** suggested minimum value. */
	private final BigDecimal minimum;
	/** suggested maximum value. */
	private final BigDecimal maximum;

	/**
	 * Internal constructor used by enumeration.
	 *
	 * @param abbreviation
	 * @param description
	 * @param minimum
	 * @param maximum
	 */
	private MagnitudeType(final String abbreviation, final String description,
			final BigDecimal minimum, final BigDecimal maximum) {
		this.abbreviation = abbreviation;
		this.description = description;
		this.minimum = minimum;
		this.maximum = maximum;
	}

	/**
	 * @return abbreviation for this type.
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @return full name for this type.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return suggested minimum for this type, or null if no suggested minimum.
	 */
	public BigDecimal getMinimum() {
		return minimum;
	}

	/**
	 * @return suggested maximum for this type, or null if no suggested maximum.
	 */
	public BigDecimal getMaximum() {
		return maximum;
	}

	/**
	 * Whether value is between the suggested minimum and maximum for this type.
	 *
	 * @param value
	 *            the magnitude to test
	 * @return true if within range, or no range specified; false if below
	 *         minimum or above maximum.
	 */
	public boolean valueInRange(final BigDecimal value) {
		if (minimum != null && minimum.compareTo(value) > 0) {
			// too small
			return false;
		}
		if (maximum != null && maximum.compareTo(value) < 0) {
			// too large
			return false;
		}
		return true;
	}

}
