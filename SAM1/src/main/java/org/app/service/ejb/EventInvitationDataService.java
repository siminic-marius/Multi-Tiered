package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Event;
import org.app.service.entities.Invitatie;

@Remote
public interface EventInvitationDataService extends EntityRepository<Event>{
	Event createNewEvent(Integer Id);
	
	Invitatie getInvitationById(Integer invitationId);
	
	String getMessage();
}
