package gov.usgs.earthquake.eqxml;

import gov.usgs.ansseqmsg.EQMessage;
import gov.usgs.ansseqmsg.Event;
import gov.usgs.ansseqmsg.EventAction;
import gov.usgs.ansseqmsg.Magnitude;
import gov.usgs.ansseqmsg.Origin;
import gov.usgs.earthquake.util.ISO8601;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

/**
 * Utility for EIDS Server storage format.
 * 
 * Reads QWEvents format (EIDS/QWServer storage directory file format) from
 * STDIN, prints summary for each parsed line.
 * 
 * @author jmfee
 */
public class QWEventsDump {

	/**
	 * Read QWEvents format from STDIN.
	 * 
	 * @param args
	 *            no arguments used.
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;

		System.err.println(String.format(
				"%-10s %-7s %-24s %-8s %-9s %-5s %-9s %-24s", "EventID",
				"Version", "Origin Time", "Latitude", "Longitude", "Depth",
				"Magnitude", "Sent"));

		while ((line = br.readLine()) != null) {
			int startIndex = line.indexOf("<EQMessage");
			if (startIndex == -1) {
				// not an eqmessage
				continue;
			}
			int endIndex = line.indexOf("</EQMessage>", startIndex + 1)
					+ "</EQMessage>".length();

			String xml = line.substring(startIndex, endIndex);
			try {
				EQMessage eq = EQMessageParser.parse(xml);

				Date sent = eq.getSent();

				Iterator<Event> eventIter = eq.getEvent().iterator();
				while (eventIter.hasNext()) {
					Event event = eventIter.next();
					String datasource = event.getDataSource().toLowerCase();
					String eventid = event.getEventID().toLowerCase();
					String version = event.getVersion();
					EventAction action = event.getAction();
					if (action != null && action == EventAction.DELETE) {
						System.err.println(String.format(
								"%10s %7s %24s %8s %9s %5s %9s %24s",
								datasource + eventid, version, "DELETE", "",
								"", "", "", ISO8601.format(sent)));
						break;
					}

					Iterator<Origin> originIter = event.getOrigin().iterator();
					while (originIter.hasNext()) {
						Origin origin = originIter.next();
						BigDecimal latitude = origin.getLatitude();
						BigDecimal longitude = origin.getLongitude();
						BigDecimal depth = origin.getDepth();
						Date time = origin.getTime();
						if (time == null) {
							continue;
						}

						Iterator<Magnitude> magIter = origin.getMagnitude()
								.iterator();
						while (magIter.hasNext()) {
							Magnitude mag = magIter.next();
							/* Boolean isPreferred = mag.isPreferredFlag(); */
							Boolean isPreferred = mag.getPreferredFlag();
							if (isPreferred != null && isPreferred) {
								System.err.println(String.format(
										"%10s %7s %24s %8s %9s %5s %9s %24s",
										datasource + eventid, version,
										ISO8601.format(time), latitude,
										longitude, depth, mag.getValue(),
										ISO8601.format(sent)));
								break;
							}
						}
						break;
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
