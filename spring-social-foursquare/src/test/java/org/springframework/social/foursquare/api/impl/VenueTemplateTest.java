package org.springframework.social.foursquare.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.social.test.client.RequestMatchers.body;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.social.foursquare.api.Venue;

public class VenueTemplateTest extends AbstractFoursquareApiTest {
	@Test
	public void getVenue() {
		mockServer.expect(requestTo("https://api.foursquare.com/v2/venues/VENUE_ID/?access_token=ACCESS_TOKEN"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("testdata/venue.json", getClass()), responseHeaders));
		
		Venue venue = foursquare.venueOperations().getVenue("VENUE_ID");
		assertEquals("3fd66200f964a520dbe91ee3", venue.getId());
		mockServer.verify();
	}
	
	@Test
	public void addVenue() {
		mockServer.expect(requestTo("https://api.foursquare.com/v2/venues/add/?access_token=ACCESS_TOKEN"))
			.andExpect(method(POST))
			.andExpect(body("name=NAME&address=ADDRESS&crossStreet=CROSSSTREET&city=CITY&state=STATE&zip=ZIP&phone=PHONE&ll=1.0%2C1.0&primaryCategoryId=CATEGORYID"))
			.andRespond(withResponse(new ClassPathResource("testdata/venue.json", getClass()), responseHeaders));
		
		Venue venue = foursquare.venueOperations().addVenue( "NAME", "ADDRESS", "CROSSSTREET",
				"CITY", "STATE", "ZIP", "PHONE", 1, 1, "CATEGORYID");
		assertEquals("3fd66200f964a520dbe91ee3", venue.getId());
		mockServer.verify();
	}
}