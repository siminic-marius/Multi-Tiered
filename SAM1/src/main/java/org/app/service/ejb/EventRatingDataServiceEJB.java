package org.app.service.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Event;
import org.app.service.entities.Rating;


@Stateless @LocalBean
public class EventRatingDataServiceEJB extends EntityRepositoryBase<Event> implements EventRatingDataService, Serializable{
	
	private static Logger logger = Logger.getLogger(EventRatingDataServiceEJB.class.getName());
	
	@EJB
	private RatingService ratingService;
	
	private EntityRepository<Rating> ratingRepository;
	
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		ratingRepository = new EntityRepositoryBase<Rating>(this.em,Rating.class);
		logger.info("POSTCONSTRUCT-INIT ratingRepository: " + this.ratingRepository);
	}
	
	
	
	public Event createNewEvent(Integer id) {
		//create event aggregate
		Event event = new Event (id, "New event" + "." + id, new Date());
		List<Rating> ratingEvent = new ArrayList<>();
		Date commentedDate = new Date();
		Long interval = 30l /*zile*/ * 24 /*ore*/ * 60 /*min*/ * 60 /*sec*/ * 1000 /*milisec*/;
		Integer ratingCount = 5;
		for(int i = 0; i < ratingCount -1; i++) {
			ratingEvent.add(new Rating(null, "Rat:" + event.getEventID() + "." + i, new Date (commentedDate.getTime() + i * interval), event));
		}
		
		event.setRating(ratingEvent);
		
		//saveAggregate
		this.add(event);
		
		//return aggregate
		return event;
		
	}

	@Override
	public Rating getRatingById(Integer ratingId) {
		return ratingRepository.getById(ratingId);
	}

	@Override
	public String getMessage() {
		return "EventRating DataService is working....";
	}
	
}
