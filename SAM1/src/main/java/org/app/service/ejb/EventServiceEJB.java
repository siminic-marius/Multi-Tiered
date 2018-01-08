package org.app.service.ejb;
import org.app.service.entities.Event;


import javax.ejb.Stateless;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Path("events")
@Stateless @LocalBean
public class EventServiceEJB implements EventService{
	private static Logger logger = Logger.getLogger(EventServiceEJB.class.getName());
	
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	
	public EventServiceEJB() {
	}
	// Init after constructor
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}		
	/* CRUD operations implementation*/
	//Create or Update
	
	
	@Override
	@PUT @Path("/{id}") 	/* SCRUM/data/features/{id} 	REST-resource: Feature-entity */	
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Event addEvent(Event eventToAdd) {
		em.persist(eventToAdd);
		em.flush();
		//transactions are managed by  default by container
		em.refresh(eventToAdd);
		return eventToAdd;
	}
	
	//READ
	@GET @Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Event getEventByEventID( @PathParam("id") Integer eventID) {
		logger.info("**** DEBUG REST getEventByID(): id = " + eventID);
		return (Event) em.find(Event.class, eventID);
	}
	
	@Override
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Event> getEvents() {
		List<Event> events = em.createQuery("SELECT e FROM Event e ", Event.class).getResultList();
		List<Event> eventsDTO = new ArrayList<>();
		for(Event ev: events){
			eventsDTO.add(new Event(ev.getEventID(), ev.getEventName(), ev.getEventLocation(), ev.getStartDate(), ev.getDescription(), ev.getNrPlaces()));
		}
		logger.info("**** DEBUG REST events.size()= " + events.size());
		return eventsDTO;
	}
	
	//JOIN FETCH e.rating r
	
	//Remove
	@DELETE 					/* SCRUM/data/features		REST-resource: Feature-entity */
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public String removeEvent(Event eventToDelete) {
		eventToDelete = em.merge(eventToDelete);
		em.remove(eventToDelete);
		em.flush();
		return "True";
	}
	
	
	
	//Custom Read: here you costume query
	@GET @Path("/{name}")		/* SCRUM/data/features 		REST-resource: Feature-entity */
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })		
	@Override
	public Event getEventByName(@PathParam("name") String eventName) {
		return em.createQuery("SELECT e FROM Event e where e.eventName = :name", Event.class).setParameter("name", eventName).getSingleResult();
	}
	
	@Override
	public String sayRest() {
		return "Event Service is on...?";
	}
	
}
