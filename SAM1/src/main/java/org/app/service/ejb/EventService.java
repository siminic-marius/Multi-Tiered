package org.app.service.ejb;

import javax.ejb.Remote;
import java.util.*;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Event;;

@Remote
public interface EventService extends EntityRepository<Event>{
	
//	//Create
//	Event addEvent(Event eventToAdd);
//	
//	//Delete
//	String removeEvent(Event eventToDelete);
//	
//	//Read
//	Event getEventByEventID(Integer evId);
//	Collection<Event> getEvents();
//	
//	//Costume the query
//	Event getEventByName(String eventName);
	
	//Everything else
	String sayRest();
}
