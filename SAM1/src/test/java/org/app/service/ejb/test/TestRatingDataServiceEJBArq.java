package org.app.service.ejb.test;
import org.app.service.ejb.RatingService;
import org.app.service.ejb.RatingServiceEJB;
import org.app.service.entities.Rating;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

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
	public void test2_getRating() {
		logger.info("DEBUG: Junit TESTING: testGetRating");
		
		Collection<Rating> rating = service.getRating();
		assertTrue("Fail to read Rating!", rating.size() == 0);
	}
	
	@Test
	public void test3_addRating() {
		logger.info("DEBUG Junit TESTING: testAddRating ...");
		
		Integer ratingToAdd = 5;
		for(int i = 0; i < ratingToAdd; i++) {
			service.AddRating(new Rating(null, "asasda" + (90+i), "asadasdasdasd" + (80 + i), 50 + i));
		}
		
		Collection<Rating> rating = service.getRating();
		assertTrue("Fail to add a new rating!", rating.size() == ratingToAdd);
	}
	
	@Test
	public void test4_deleteRating() {
		logger.info("DEBUG: Junit TESTING: testDeleteRating ...");
		
		Collection<Rating> rating = service.getRating();
		for(Rating r: rating) {
			service.removeRating(r);
		}
		Collection<Rating> ratingAfterDelete = service.getRating();
		assertTrue("Fail to read rating!", ratingAfterDelete.size() == 0);
	}
}