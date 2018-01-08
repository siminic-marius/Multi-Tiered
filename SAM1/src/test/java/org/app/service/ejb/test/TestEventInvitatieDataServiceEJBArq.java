package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.ejb.EJB;

import org.app.patterns.EntityRepository;
import org.app.service.ejb.EventInvitationDataService;
import org.app.service.ejb.EventInvitationDataServiceEJB;
import org.app.service.ejb.InvitationService;
import org.app.service.ejb.InvitationServiceEJB;
import org.app.service.entities.Event;
import org.app.service.entities.Invitatie;
import org.app.service.entities.Persoane;
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
public class TestEventInvitatieDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestEventInvitatieDataServiceEJBArq.class.getName());
	
	@Deployment
	public static Archive<?> createDeployment(){
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(EntityRepository.class.getPackage()).addPackage(Event.class.getPackage())
				.addClass(InvitationService.class).addClass(InvitationServiceEJB.class)
				.addClass(EventInvitationDataService.class).addClass(EventInvitationDataServiceEJB.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@EJB
	private static EventInvitationDataService service;
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: testGetMessage ...");
		String response = service.getMessage();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}
	
	@Test
	public void test2_deleteEvent() {
		logger.info("DEBUG Junit TESTING: testDeleteEvent 11111 ");
		Event event = service.getById(1050);
		
		if(event != null) {
			service.remove(event);
		}
		
		event = service.getById(1050);
		assertNull("Fail to delete Event 1050!", event);
	}
	
	@Test
	public void test3_createEvent() {
		Event event = service.createNewEvent(1050);
		assertNotNull("Fail to create new event in repository!'", event);
		//update project
		event.setEventName(event.getEventName() + " - New Year party");
		List<Invitatie> invitatii = event.getInvitatie();
		
		for(Invitatie i: invitatii) {
			i.setPersons(new Persoane(1050 + i.getinvitationId(), "Persoana: " + 1050 + i.getinvitationId(), "Iasi: " + 1050 + i.getinvitationId(), 
					"F", "persoana" + 1050 + i.getinvitationId() + "@gmail.com"));
		}
		
		event = service.add(event);
		assertNotNull("Fail to save new event in repository!'", event);
		
		logger.info("DEBUG createNewEvent: event changed: " + event);
	}
	
	@Test
	public void test4_getEvent() {
		logger.info("DEBUG: Junit TESTING: testGetEvent: 1050 ...");
		Event event = service.getById(1050);
		assertNotNull("Fail to get event 1050!", event);
	}
}
