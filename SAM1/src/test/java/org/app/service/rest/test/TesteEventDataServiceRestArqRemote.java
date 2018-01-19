package org.app.service.rest.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.app.service.entities.Event;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(Arquillian.class) 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteEventDataServiceRestArqRemote {
	private static Logger logger  = Logger.getLogger(TesteEventDataServiceRestArqRemote.class.getName());
	
	private static String serviceURL = "http://localhost:8080/SCRUM/data/events";
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "msd-s4-test.war")
				.addPackage(Event.class.getPackage());
	}
	
	@Test
	public void test1_GetMessage() {
		String resourceURL = serviceURL + "/test";
		logger.info("DEBUG: Junit TESTING: test1_GetMessage ...");
		String response = ClientBuilder.newClient().target(resourceURL).request().get().readEntity(String.class);
		assertNotNull("Data source failed" + response);
		logger.info("DEBUG EJB Response...." +  response);
	}
	
	@Test
	public void test2_deleteEvent() {
		String resourceURL = serviceURL + "/";
		logger.info("DEBUG Junit Testing: test2_deleteProject .." );
		Client client =  ClientBuilder.newClient();
		Collection<Event> event = client.target(serviceURL).request().get().readEntity(new GenericType<Collection<Event>>(){});
		
		for(Event e: event) {
			client.target(resourceURL + e.getEventID()).request().delete();
		}
		Collection<Event> eventAfterDelete = client.target(serviceURL).request().get().readEntity(new GenericType<Collection<Event>>() {});
		
		assertTrue("Fail to read Event",  eventAfterDelete.size()==0);
	}
	
	@Test
	public void test3_AddEvent() {
		// addIntoCollection
		logger.info("DEBUG: Junit TESTING: test3_AddEvent ...");
		Client client = ClientBuilder.newClient();
		Collection<Event> events;
		
		Date startDate = new Date();
		Long interval =  12l * 30 /*zile*/ * 24 /*ore*/ * 60 /*min*/ * 60 /*sec*/ * 1000 /*milisec*/;
		
		Integer eventsToAdd = 3;
		Event event;
		for (int i=1; i <= eventsToAdd; i++){
			event = new Event(i, "Event: " + (i + 200), "Event: Brasov: " + (i + 100), 
					new Date(startDate.getTime() + i * interval), "Event -- " + (i + 100), 150 + i);
			events = client.target(serviceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(event, MediaType.APPLICATION_JSON))
				.readEntity(new GenericType<Collection<Event>>(){});
			assertTrue("Fail to read Events!", events.size() > 0);
		}
		events = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Event>>(){});
		
		assertTrue("Fail to add Events!", events.size() >= eventsToAdd);
		events.stream().forEach(System.out::println);
	}

	@Test
	public void test4_CreateEvent() {
		String resourceURL = serviceURL + "/new/"; //createEvent
		logger.info("DEBUG: Junit TESTING: test3_CreateEvent ...");
		Client client = ClientBuilder.newClient();
		
		Integer eventToAdd = 3;
		Event event;
		for (int i=1; i <= eventToAdd; i++){
			event = ClientBuilder.newClient().target(resourceURL + i)
					.request().accept(MediaType.APPLICATION_JSON)
					.post(null).readEntity(Event.class);
			System.out.println(">>> Created/posted Event: " + event);
		}

		Collection<Event> events = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Event>>(){});
		
		assertTrue("Fail to add Events!", events.size() >= eventToAdd);
	}
	
	@Test
	public void test5_GetEvents() {
		logger.info("DEBUG: Junit TESTING: test4_GetEvents ...");
		Collection<Event> events = ClientBuilder.newClient()
				.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Event>>(){});
		assertTrue("Fail to read Projects!", events.size() > 0);
		events.stream().forEach(System.out::println);
	}
	
	@Test
	public void test6_GetByID() {
		String resourceURL = serviceURL + "/1";
		logger.info("DEBUG: Junit TESTING: test1_GetMessage ...");
		
		Event event = ClientBuilder.newClient().target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Event.class);
		
		assertNotNull("REST Data Service failed!", event);
		logger.info(">>>>>> DEBUG: REST Response ..." + event);
	}	
	
	@Test
	public void test7_UpdateEvent() {
		String resourceURL = serviceURL + "/1"; //create Event
		logger.info("************* DEBUG: Junit TESTING: test5_UpdateEvent ... :" + resourceURL);
		Client client = ClientBuilder.newClient();
		// Get event
		Event event = client.target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Event.class);
		
		assertNotNull("REST Data Service failed!", event);
		logger.info(">>> Initial Event: " + event);
		
		// update and save event
		event.setEventName(event.getEventName() + "JSON_REMOTE");
		event = client.target(resourceURL)
				//.request().accept(MediaType.APPLICATION_XML).header("Content-Type", "application/xml")
				.request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(event, MediaType.APPLICATION_JSON))
				.readEntity(Event.class);
		
		logger.info(">>> Updated Event: " + event);
		
		assertNotNull("REST Data Service failed!", event);
	}	
}
