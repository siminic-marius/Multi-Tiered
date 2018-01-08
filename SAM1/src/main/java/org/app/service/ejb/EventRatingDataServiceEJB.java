package org.app.service.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Event;
import org.app.service.entities.Rating;


@Stateless @LocalBean
public class EventRatingDataServiceEJB extends EntityRepositoryBase<Event> implements EventRatingDataService, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4135575035967348860L;

	private static Logger logger = Logger.getLogger(EventRatingDataServiceEJB.class.getName());
	
	@EJB
	private RatingService ratingService;
	
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		logger.info("POSTCONSTRUCT-INIT ratingService: " + this.ratingService);
	}
	
	public Event createNewEvent(Integer id) {
		//create event aggregate
		
		Date startDate = new Date();
		Long interval =  12l * 30  * 24  * 60  * 60  * 1000;
	
		Event event = new Event (id, "Event: " + (id + 100), "Event: Iasi: " + (id + 100), 
				new Date(startDate.getTime() + 4 * interval), "Event -- " + (id + 100), 150 + id);
		
		List<Rating> ratingEvent = new ArrayList<>();
		LocalDateTime comentedDate = LocalDateTime.of(2016, Month.AUGUST, 21, 20, 6, 20);
		
		Integer ratingCount = 5;
		for(int i = 0; i < ratingCount ; i++) {
			ratingEvent.add(new Rating(432 + i, "Rat:" + event.getEventID() + "/" + i, comentedDate.plusMonths(i), "Very nice x-mas event " + i + "!", (8 + i), event));
		}
		
		event.setRating(ratingEvent);
		
		//saveAggregate
		this.add(event);
		
		//return aggregate
		return event;
		
	}

	@Override
	public Rating getRatingById(Integer ratingId) {
		return ratingService.getRatingByratingID(ratingId);
	}

	@Override
	public String getMessage() {
		return "EventRating DataService is working....";
	}
	
}
