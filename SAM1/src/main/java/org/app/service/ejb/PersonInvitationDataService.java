package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Persoane;
import org.app.service.entities.Invitatie;

@Remote
public interface PersonInvitationDataService extends EntityRepository<Persoane>{
	
		Persoane addNewPerson(Integer id);
		
		Invitatie getInvitationgById (Integer invitationId);
		
		String getMessage();
}
