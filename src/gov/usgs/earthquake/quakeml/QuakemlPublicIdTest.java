package gov.usgs.earthquake.quakeml;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for QuakemlPublicId.
 * 
 * @author bkfindley
 */
public class QuakemlPublicIdTest {

	@Test
	public void basicExternalTest() throws Exception {
		String authority = "quakeml:quthority";
		String resourceId = "resourceId/test";

		QuakemlPublicId id = new QuakemlPublicId(authority, resourceId);

		QuakemlPublicId id2 = QuakemlPublicId.parse(authority + "/"
				+ resourceId);

		Assert.assertEquals("authorities equal", id.getAuthority(),
				id2.getAuthority());
		Assert.assertEquals("resource ids equal", id.getResourceId(),
				id2.getResourceId());
		Assert.assertNull("anss network is null", id.getAnssDataSource());
		Assert.assertNull("anss eventid is null", id.getAnssEventId());
	}

	@Test
	public void basicANSSTest() throws Exception {
		String network = "us";
		String eventId = "c0001xgp";
		String type = "event";
		String other = null;

		// Checks if network + type + eventId with no other works
		QuakemlPublicId id = new QuakemlPublicId(network, type, eventId, other);
		Assert.assertTrue(
				"PublicId is correct",
				id.getPublicId().equals(
						"quakeml:us.anss.org/" + type + "/" + eventId));

		// Checks if network + type + eventId + other works
		other = "can/get/pretty/deep";
		id = new QuakemlPublicId(network, type, eventId, other);
		Assert.assertTrue(
				"PublicId with other set does not match",
				id.getPublicId().equals(
						"quakeml:us.anss.org/" + type + "/" + eventId + "/"
								+ other));
		Assert.assertTrue("Networks match",
				id.getAnssDataSource().equals(network));
		Assert.assertTrue("EventIds do not match",
				id.getAnssEventId().equals(eventId));
		Assert.assertTrue("Types do not match", id.getAnssResourceType()
				.equals(type));
		Assert.assertTrue("Others do not match",
				id.getAnssExtension().equals(other));

	}

	@Test
	public void ANSSFromQuakexmlTest() throws Exception {
		String network = "us";
		String eventId = "c0001xgp";
		String type = "event";
		String other = null;
		String publicId = "quakeml:us.anss.org/" + type + "/" + eventId;

		// Checks if network + type + eventId with no other works
		QuakemlPublicId id = QuakemlPublicId.parse(publicId);
		Assert.assertTrue("PublicId is not correct",
				id.getPublicId().equals(publicId));
		Assert.assertTrue("Network does not match", id.getAnssDataSource()
				.equals(network));
		Assert.assertTrue("EventId does not match",
				id.getAnssEventId().equals(eventId));
		Assert.assertTrue("Type does not match", id.getAnssResourceType()
				.equals(type));
		Assert.assertTrue("Other is not null", id.getAnssExtension() == null);

		// Checks if network + type + eventId + other works
		other = "can/get/very/long";
		publicId += "/" + other;
		id = QuakemlPublicId.parse(publicId);
		Assert.assertTrue("PublicId is not correct",
				id.getPublicId().equals(publicId));
		Assert.assertTrue("Network does not match", id.getAnssDataSource()
				.equals(network));
		Assert.assertTrue("EventId does not match",
				id.getAnssEventId().equals(eventId));
		Assert.assertTrue("Type does not match", id.getAnssResourceType()
				.equals(type));
		Assert.assertTrue("Other does not match",
				id.getAnssExtension().equals(other));

		// Checks if an ANSS authority and resourceId works
		String authority = "quakeml:us.anss.org";
		String resouceId = type + "/" + eventId + "/" + other;
		id = new QuakemlPublicId(authority, resouceId);
		Assert.assertTrue("Public Ids don't match",
				publicId.equals(id.getPublicId()));
		Assert.assertTrue("Network does not match", id.getAnssDataSource()
				.equals(network));
		Assert.assertTrue("EventId does not match",
				id.getAnssEventId().equals(eventId));
		Assert.assertTrue("Type does not match", id.getAnssResourceType()
				.equals(type));
		Assert.assertTrue("Other does not match",
				id.getAnssExtension().equals(other));

	}

}
