package org.app.service.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Event;
import org.app.service.entities.Invitatie;

@Stateless @LocalBean
public class EventInvitationDataServiceEJB extends EntityRepositoryBase<Event> implements EventInvitationDataService, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3275701352285396487L;

	private static Logger logger = Logger.getLogger(EventInvitationDataServiceEJB.class.getName());
	
	private EntityRepository<Invitatie> invitationRepository;
	
	@PostConstruct
	public void init() {
		invitationRepository = new EntityRepositoryBase<Invitatie>(this.em, Invitatie.class);
		logger.info("POSTCONSTRUCT-INIT invitationRepository: " + this.invitationRepository);
	}
	@Override
	public Event createNewEvent(Integer Id) {
		//create event aggregate
		Date startDate = new Date();
		Long interval =  12l * 30  * 24  * 60  * 60  * 1000;
		Event event = new Event (Id, "Event: " + (Id + 100), "Event: Iasi: " + (Id + 100), 
				new Date(startDate.getTime() + 4 * interval), "Event -- " + (Id + 100), 150 + Id);
		List<Invitatie> invitationEvent = new ArrayList<>();
		Date invitationDate = new Date();
		Integer ratingCount = 5;
		for(int i = 0; i < ratingCount -1; i++) {
			invitationEvent.add(new Invitatie(650 + i, "Invt:" + event.getEventID() + "/" + i, "Come to our Christmas party " + i, new Date (invitationDate.getTime() + i * interval), event, null));
		}
		event.setInvitatie(invitationEvent);
		//saveAggregate
		this.add(event);
				
		//return aggregate
		return event;
	}

	@Override
	public Invitatie getInvitationById(Integer invitationId) {
		// TODO Auto-generated method stub
		
		return invitationRepository.getById(invitationId);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "EventInvitation DataService is working...";
	}

}
