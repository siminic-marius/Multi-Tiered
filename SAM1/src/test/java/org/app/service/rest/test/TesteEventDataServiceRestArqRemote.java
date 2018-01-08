package org.app.service.rest.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

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
}
