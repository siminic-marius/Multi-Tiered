package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.app.patterns.EntityRepository;
import org.app.service.ejb.PersoaneRatingDataService;
import org.app.service.ejb.PersoaneRatingDataServiceEJB;
import org.app.service.ejb.RatingService;
import org.app.service.ejb.RatingServiceEJB;
import org.app.service.entities.Event;
import org.app.service.entities.Persoane;
import org.app.service.entities.Rating;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import org.junit.Test;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPersoaneRatingDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestPersoaneRatingDataServiceEJBArq.class.getName());
	
	@Deployment
	public static Archive<?> createDeployment(){
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(EntityRepository.class.getPackage()).addPackage(Persoane.class.getPackage())
				.addClass(RatingService.class).addClass(RatingServiceEJB.class)
				.addClass(PersoaneRatingDataService.class).addClass(PersoaneRatingDataServiceEJB.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@EJB
	private static PersoaneRatingDataService service;
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit.....TESTING: test GetMessage");
		String response = service.getMessage();
		assertNotNull("Data service failed", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}
	
	@Test
	public void test2_DeletePersoana() {
		logger.info("DEBUG Junit TESTING: testDeletePerson with id  666 ");
		Persoane persoane = service.getById(666);
		
		if(persoane != null) {
			service.remove(persoane);
		}
		
		persoane = service.getById(666);
		assertNull("Failed to delete persoana with id 666", persoane);	
	}
	
	@Test
	public void test3_CreatePersoana() {
		Persoane persoane = service.createNewPersoane(666);
		assertNotNull("Failed to add a new Person", persoane);
//		persoane.setSex(persoane.getSex() + "New Type - some wierd hybrid");
//		List<Rating> ratinguri = new ArrayList<>();
//		Date startDate = new Date();
//		Long interval =  12l * 30  * 24 * 60  * 60  * 1000;
//		for(Rating r: ratinguri) {
//			r.setEvent(new Event(334 + r.getRatingId(), "Event: " + (r.getRatingId() + 100), "Event: Iasi: " + (r.getRatingId() + 100), 
//					new Date(startDate.getTime() + r.getScoreRating() * interval), "Event -- " + (r.getRatingId() + 100), 150 + r.getRatingId() ));
//		}
		persoane = service.add(persoane);
		assertNotNull("Fail to save new person in repository!'", persoane);
		logger.info("DEBUG createNewPerson: person changed: " + persoane);
		
	}
	
	@Test
	public void test4_GetPersoana() {
		logger.info("Testing Junit get Persoana");
		Persoane persoana = service.getById(666);
		assertNull("Failed to get persoana with id 666, does not exist", persoana);
	}
}
