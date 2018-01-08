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
import org.app.service.entities.Persoane;
import org.app.service.entities.Invitatie;

@Stateless @LocalBean
public class PersonInvitationDataServiceEJB extends EntityRepositoryBase<Persoane> implements PersonInvitationDataService, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4326843952340744339L;

	private static Logger logger = Logger.getLogger(PersonInvitationDataServiceEJB.class.getName());
	
	private EntityRepository<Invitatie> invitationRepository;
	
	@PostConstruct
	public void init() {
		invitationRepository = new EntityRepositoryBase<Invitatie>(this.em, Invitatie.class);
		logger.info("POSTCONSTRUCT-INIT invitationRepository: " + this.invitationRepository);
	}
	
	//Aggregade factory method
	public Persoane addNewPerson(Integer id) {
		
		Persoane persoane = new Persoane(id, "Persoana: " + id, "Iasi: " + id, "M", "person" + id + "@gmail.com");
		List<Invitatie> invitationsPersons = new ArrayList<>();
		
		Date dataInvitatie = new Date();
		Long interval = 30l * 24 * 60 * 60 * 1000;
		
		Integer invitationsCount = 5;
		for(int i =0; i < invitationsCount; i++) {
			invitationsPersons.add(new Invitatie(i + 525 , "I: " + persoane.getPersoanaId() + "/" + i, "Invitatie: " + i,  new Date(dataInvitatie.getTime() + i* interval), null, persoane));
		}
		
		persoane.setInvitatii(invitationsPersons);
		
		//save
		this.add(persoane);
		//return aggregate to service client
		return persoane;
	}


	public Invitatie getInvitationgById(Integer invitationId) {
		// TODO Auto-generated method stub
		return invitationRepository.getById(invitationId);
	}

	public String getMessage() {	
		return "PersonInvitation DataService is working....";
	}

	

}
