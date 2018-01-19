package org.app.service.ejb;
import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Event;
import org.app.service.entities.Rating;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Path("events")
@Stateless @LocalBean
public class EventServiceEJB extends EntityRepositoryBase<Event> implements EventService{
	private static Logger logger = Logger.getLogger(EventServiceEJB.class.getName());
	
	
	private EntityRepository<Rating> ratingRepository;
	
	@PostConstruct
	public void init() {
		ratingRepository = new EntityRepositoryBase<Rating>(this.em, Rating.class);
				logger.info("POSTCONSTRUCT-INIT ratingRepository: " + this.ratingRepository);
	}
	
	@Override
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Event> toCollection() {
		logger.info("DEBUG REST toCollection()");
		return super.toCollection();
	}
	
	@GET @Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Event getById(@PathParam("id") Integer id){ 
		Event event = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " + event);
		return event;
	}	
	
	@POST 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Collection<Event> addIntoCollection(Event event) {
		// save aggregate
		super.add(event);
		logger.info("**** DEBUG REST save aggregate POST");
		// return updated collection
		return super.toCollection();
	}
	
	@PUT @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Override
	public Event add(Event event) {
		// restore aggregation-relation
		for (Rating r: event.getRating())
			r.setEvent(event);
		logger.info("**** DEBUG REST restore aggregation-relation PUT");
		// save aggregate
		logger.info("**** DEBUG REST save aggregate PUT");
		event = super.add(event);
		// return updated collection	
		return event;
	}	
	
//	@Override
	@DELETE 				/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public Collection<Event> removeFromCollection(Event event) {
		logger.info("DEBUG: called REMOVE - person: " + event);
		super.remove(event);
		return super.toCollection();
	}
	
	@DELETE @Path("/{id}") 		
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remove(@PathParam("id")Integer id) {
		logger.info("DEBUG: called REMOVE - ById() for events >>>>>>>>>>>>>> simplified ! + id");
		Event event = super.getById(id);
		super.remove(event);
	}
	
	// GET method on second repository for Release-type entities
	@GET @Path("/{eventId}/ratings/{ratingId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Rating getRatingById(@PathParam("ratingId") Integer ratingId){
		logger.info("DEBUG: called getInvitatieById() for persoane >>>>>>>>>>>>>> simplified !");
		Rating rating = ratingRepository.getById(ratingId);
		return rating;
	}
	
	@POST @Path("/new/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Event addNewEvent(@PathParam("id")Integer id) {
		
		Date startDate = new Date();
		Long interval =  12l * 30  * 24  * 60  * 60  * 1000;
	
		Event event = new Event (id, "Event: " + (id + 100), "Event: Iasi: " + (id + 100), 
				new Date(startDate.getTime() + 4 * interval), "Event -- " + (id + 100), 150 + id);
		
		List<Rating> ratingEvent = new ArrayList<>();
		LocalDateTime comentedDate = LocalDateTime.of(2016, Month.AUGUST, 21, 20, 6, 20);
		
		Integer ratingCount = 5;
		for(int i = 0; i < ratingCount ; i++) {
			ratingEvent.add(new Rating(800 + i, "Rat:" + event.getEventID() + "/" + i, comentedDate.plusMonths(i), "Very nice new year event! " + i + "!", (12 + i), event));
		}
		
		event.setRating(ratingEvent);
		
		//saveAggregate
		this.add(event);
		
		//return aggregate
		return event;
	}
	
	@GET @Path("/test") // Check if resource is up ...
	@Produces({ MediaType.TEXT_PLAIN})
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Event DataService is working...";
	}	
	
}
