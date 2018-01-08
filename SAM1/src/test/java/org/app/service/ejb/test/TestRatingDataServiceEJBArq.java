package org.app.service.ejb.test;
import org.app.service.ejb.RatingService;
import org.app.service.ejb.RatingServiceEJB;
import org.app.service.entities.Event;
import org.app.service.entities.Rating;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRatingDataServiceEJBArq {
private static Logger logger = Logger.getLogger(TestRatingDataServiceEJBArq.class.getName());
	
	@EJB
	private static RatingService service;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(Rating.class.getPackage())
				.addClass(RatingService.class)
				.addClass(RatingServiceEJB.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

	}
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: getMessage ...");
		String response = service.sayRest();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}
	
	@Test
	public void test4_getRating() {
		logger.info("DEBUG: Junit TESTING: testGetRating");
		
		Collection<Rating> rating = service.getRating();
		assertTrue("Fail to read Rating!", rating.size() > 0);
	}
	
	@Test
	public void test3_addRating() {
		logger.info("DEBUG Junit TESTING: testAddRating ...");
		
		LocalDateTime startDate = LocalDateTime.of(2017, Month.APRIL, 5, 12, 00,00);
		
		Date startDateEvent = new Date();
		
		Long interval = 30l * 24 * 60 * 60 * 1000;
		
		Integer ratingToAdd = 5;
		for(int i = 0; i < ratingToAdd; i++) {
			service.AddRating(new Rating (120 + i, "Rating For Event " + (100 + i), startDate.plusMonths(i), " The Event was ok:  " + (100 + i), 8 + i, 
					new Event( 132 + i, "Event: Demo " + (90 + i), "Event: Demo: Iasi " + (i), new Date(startDateEvent.getTime() + i * interval), "Event: Demo For Product", 150)));
		}
		
		Collection<Rating> rating = service.getRating();
		assertTrue("Fail to add a new rating!", rating.size() == ratingToAdd);
	}
	
	@Test
	public void test2_deleteRating() {
		logger.info("DEBUG: Junit TESTING: testDeleteRating ...");
		
		Collection<Rating> rating = service.getRating();
		for(Rating r: rating) {
			service.removeRating(r);
		}
		Collection<Rating> ratingAfterDelete = service.getRating();
		assertTrue("Fail to read rating!", ratingAfterDelete.size() == 0);
	}
}