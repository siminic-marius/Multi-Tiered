package org.app.service.entities;

import javax.ejb.Singleton;
import java.util.*;

@Singleton
public class EventFactory {
	
	public Event buildEvent(Integer eventId, String eventName, Date startDate) {
		
		Event event = new Event (eventId, eventName + "." + eventId, eventName, startDate, eventName, eventId);
		List<Invitatie> eventInvitation = new ArrayList<>();
		
		
		Date startEvent = new Date();
		int interval =  301 * 24 * 60 * 60 * 1000;
		
		
		for(int i = 0; i  <= 20 - 1; i++ ) {
			eventInvitation.add(new Invitatie (null, "I: " + event.getEventID() + "." + 1, "X-mas invitation baby!" + i,
					new Date (startEvent.getTime() + i * interval), null ));
		}
		
		return event;
		
	}
	
}
