package org.scrum.client.services;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.scrum.client.dto.Event;

@Named @SessionScoped
public class EventRestServiceConsumer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9113424830946766859L;
	
	private static Logger logger = Logger.getLogger(EventRestServiceConsumer.class.getName());
	
	private static String serviceURL = "http://localhost:8080/SCRUM/data/events";
	
	public List<Event> getEvent() {
		logger.info(">>> REST Consumer: getPersoane.");
		List<Event> event = new ArrayList<Event>();
		event.addAll(this.getAllEvents());
		return event;
	}
	
	@PostConstruct
	public void init() {
//		this.persoane.addAll(this.getAllPersoane());
	}
	
	public Collection<Event> getAllEvents() {
		logger.info("DEBUG: PersoneRESTServiceCONSUMER: getAllPersoane...");
		Collection<Event> events = ClientBuilder.newClient().target(serviceURL).request().get()
								.readEntity(new GenericType<Collection<Event>>() {});
		events.stream().forEach(System.out::println);
		return events;
	}
	
	public List<Event> addPersoane(Event event) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: addProject: " + event);
		Client client = ClientBuilder.newClient();
		Collection<Event> events;
		
		events = client.target(serviceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(event, MediaType.APPLICATION_JSON))
				.readEntity(new GenericType<Collection<Event>>(){});
		
//		persoane.stream().forEach(System.out::println);
		return new ArrayList<> (events);
	}
	
	public Collection<Event> deletePersoane(Event event) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: deleteProject: " + event);
		
		String resourceURL = serviceURL + "/";
		
		Client client = ClientBuilder.newClient();
		client.target(resourceURL + event.getEventID()).request().delete();
		
		Collection<Event> eventAfterDelete = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Event>>(){});	
		
		eventAfterDelete.stream().forEach(System.out::println);
		return eventAfterDelete;
	}
	
	public Event getByID(Integer eventId) {
		String resourceURL = serviceURL + "/" + eventId;
		logger.info("DEBUG: ProjectsRESTServiceConsumer: getByID: " + eventId);
		
		Event event = ClientBuilder.newClient().target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Event.class);
		
		assertNotNull("REST Data Service failed!", event);
		logger.info(">>>>>> DEBUG: REST Response ..." + event);
		
		return event;
	}	
	
	public Event updateEvent(Event event) {
		String resourceURL = serviceURL + "/" + event.getEventID();
		logger.info("DEBUG: ProjectsRESTServiceConsumer: project: " + event);
		Client client = ClientBuilder.newClient();

		// Get persoane server version
		Event serverEvent= client.target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Event.class);
		
		logger.info(">>> Server-side Project: " + serverEvent);
		
		event = client.target(resourceURL)
				//.request().accept(MediaType.APPLICATION_XML).header("Content-Type", "application/xml")
				.request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(event, MediaType.APPLICATION_JSON))
				.readEntity(Event.class);
		
		logger.info(">>> Updated Persoane: " + event);
		return event;
	}
	
	public Event createEvent() {
		String resourceURL = serviceURL + "/new/"; //createNewProject
		logger.info("DEBUG: ProjectsRESTServiceConsumer: CreatePersoane ");

		Event event = ClientBuilder.newClient().target(resourceURL + 2123)
					.request().accept(MediaType.APPLICATION_JSON)
					.post(null).readEntity(Event.class);
		logger.info(">>> Created/posted Persoane: " + event);
		
		return event;
	}
	
}
