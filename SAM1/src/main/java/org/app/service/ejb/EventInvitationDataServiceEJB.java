package org.app.service.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Event;
import org.app.service.entities.Invitation;
import org.app.service.entities.Persoane;
import org.app.service.entities.Rating;

@Stateless @LocalBean
public class EventInvitationDataServiceEJB extends EntityRepositoryBase<Event> implements EventInvitationDataService, Serializable{
	
	private static Logger logger = Logger.getLogger(EventInvitationDataServiceEJB.class.getName());
	
	@EJB
	private InvitationService invitationService;
	
	private EntityRepository<Invitation> invitationRepository;
	
	@PostConstruct
	public void init() {
		invitationRepository = new EntityRepositoryBase<Invitation>(this.em, Invitation.class);
		logger.info("POSTCONSTRUCT-INIT invitationRepository: " + this.invitationRepository);
	}
	@Override
	public Event createNewEvent(Integer Id) {
		//create event aggregate
		Event event = new Event (Id, "New event" + "." + Id, new Date());
		List<Invitation> invitationEvent = new ArrayList<>();
		Date invitationDate = new Date();
		Long interval = 30l /*zile*/ * 24 /*ore*/ * 60 /*min*/ * 60 /*sec*/ * 1000 /*milisec*/;
		Integer ratingCount = 5;
		for(int i = 0; i < ratingCount -1; i++) {
			invitationEvent.add(new Invitation(null, "Rat:" + event.getEventID() + "." + i, new Date (invitationDate.getTime() + i * interval), event));
		}
		event.setInvitatie(invitationEvent);
		//saveAggregate
		this.add(event);
				
		//return aggregate
		return event;
	}

	@Override
	public Invitation getInvitationById(Integer invitationId) {
		// TODO Auto-generated method stub
		return invitationRepository.getById(invitationId);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "EventInvitation DataService is working...";
	}

}
