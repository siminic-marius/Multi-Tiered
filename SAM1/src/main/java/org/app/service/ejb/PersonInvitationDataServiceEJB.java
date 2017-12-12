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
import org.app.service.entities.Persoane;
import org.app.service.entities.Invitation;

@Stateless @LocalBean
public class PersonInvitationDataServiceEJB extends EntityRepositoryBase<Persoane> implements PersonInvitationDataService, Serializable{

	private static Logger logger = Logger.getLogger(PersonInvitationDataServiceEJB.class.getName());
	
	@EJB
	private InvitationService invitationService;
	
	private EntityRepository<Invitation> invitationRepository;
	
	@PostConstruct
	public void init() {
		invitationRepository = new EntityRepositoryBase<Invitation>(this.em, Invitation.class);
		logger.info("POSTCONSTRUCT-INIT invitationRepository: " + this.invitationRepository);
	}
	
	@Override
	public Persoane addNewPerson(Integer id) {
		Persoane persoane = new Persoane(id, "New Person" + "." + id);
		List<Invitation> invitationsPersons = new ArrayList<>();
		
		Integer invitationsCount = 5;
		for(int i =0; i < invitationsCount; i++) {
			invitationsPersons.add(new Invitation(null, "I: " + persoane.getPersoanaId() + "." + i, persoane));
		}
		persoane.setInvitatii(invitationsPersons);
		this.add(persoane);
		return persoane;
	}

	@Override
	public Invitation getInvitationgById(Integer invitationId) {
		// TODO Auto-generated method stub
		return invitationRepository.getById(invitationId);
	}

	@Override
	public String getMessage() {
		
		return "PersonInvitation DataService is working....";
	}

	

}
