package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.app.service.entities.Event;
import org.app.service.entities.Rating;

import javax.ejb.EJB;
import org.app.patterns.EntityRepository;
import org.app.service.ejb.EventRatingDataService;
import org.app.service.ejb.EventRatingDataServiceEJB;
import org.app.service.ejb.RatingService;
import org.app.service.ejb.RatingServiceEJB;

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
public class TestEventRatingDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestEventRatingDataServiceEJBArq.class.getName());
	
	@Deployment
	public static Archive<?> createDeployment(){
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(EntityRepository.class.getPackage()).addPackage(Event.class.getPackage())
				.addClass(RatingService.class).addClass(RatingServiceEJB.class)
				.addClass(EventRatingDataService.class).addClass(EventRatingDataServiceEJB.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@EJB
	private static EventRatingDataService service;
	
	@Test
	public void test4_getEvent() {
		logger.info("DEBUG: Junit TESTING: testGetEvent: 1111 ...");
		Event event = service.getById(1111);
		assertNotNull("Fail to get event 1111!", event);
	}
	//create aggregate
	@Test
	public void test3_createEvent() {
		Event event = service.createNewEvent(1111);
		assertNotNull("Fail to create new project in repository!'", event);
		//update project
		event.setEventName(event.getEventName() + "- I changed something");
		List<Rating> rating = event.getRating();
		
		for(Rating r: rating) {
			r.setScoreRating(r.getScoreRating());
		}
		event = service.add(event);
		assertNotNull("Fail to save new project in repository!'", event);
		
		logger.info("DEBUG createNewEvent: event changed: " + event);
		
		//check read
		
		event = service.getById(1111);
		assertNotNull("Fail to find changed event in repository!'", event);
		logger.info("DEBUG createNewEvent: queried event: " + event);
	}
	
	@Test
	public void test2_deleteEvent() {
		logger.info("DEBUG Junit TESTING: testDeleteEvent 11111 ");
		Event event = service.getById(1111);
		
		if(event != null) {
			service.remove(event);
		}
		
		event = service.getById(1111);
		assertNull("Fail to delete Event 1111!", event);
	}
	
}
