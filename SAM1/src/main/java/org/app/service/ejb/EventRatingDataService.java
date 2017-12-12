package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Event;
import org.app.service.entities.Rating;;

@Remote
public interface EventRatingDataService extends EntityRepository<Event>{
	//Aggregate Entity
	Event createNewEvent(Integer id);
	
	//Query method
	Rating getRatingById (Integer ratingId);
	
	String getMessage();
}
