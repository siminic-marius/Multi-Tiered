package org.app.service.ejb.test;
import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.ejb.EventService;
import org.app.service.ejb.EventServiceEJB;
import org.app.service.entities.Event;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
public class TestEventDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestEventDataServiceEJBArq.class.getName());
	
	@EJB //EJB DataService Ref - injectin of tested ejb componenet
	private static EventService service;
	
	@Deployment //infrastructura Arquilian
	public static Archive<?> createDeployment(){
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(Event.class.getPackage())
				.addClass(EventService.class)
				.addClass(EventServiceEJB.class)
				.addClass(EntityRepository.class)
				.addClass(EntityRepositoryBase.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: getMessage ...");
		String response = service.sayRest();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUg: EJB Response ..." + response);
	}
	
	@Test
	public void test3_AddEvent() {
		logger.info("DEBUG: Junit TESTING: testAddEvent ...");
		
		Date startDate = new Date();
		Long interval =  12l * 30 /*zile*/ * 24 /*ore*/ * 60 /*min*/ * 60 /*sec*/ * 1000 /*milisec*/;
		
		Integer eventToAdd = 5;
		for(int i = 0; i < eventToAdd; i++) {
			service.add(new Event (i, "Event: " + (i + 100), "Event: Iasi: " + (i + 100), 
					new Date(startDate.getTime() + i * interval), "Event -- " + (i + 100), 150 + i));
		}
		
		Collection <Event> events = service.toCollection();
		assertTrue("Fail to add events!", events.size() == eventToAdd);
	}
	
	@Test
	public void test4_getEvents() {
		logger.info("DEBUG: Junit TESTING: testgetEvent ...");
		
		Collection<Event> events = service.toCollection();
		assertTrue("Fail to read events!", events.size() > 0);
	}
	
	@Test
	public void test2_RemoveEvent() {
		logger.info("DEBUG: Junit TESTING: testRemoveEvent ...");
		
		Collection<Event> events = service.toCollection();
		for(Event e: events) {
			service.remove(e);
		}
		Collection<Event> eventsAfterRemove = service.toCollection();
		assertTrue("Fail to read features!", eventsAfterRemove.size() == 0);
	}
	
}
