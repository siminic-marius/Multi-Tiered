package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Invitatie;
import org.app.service.entities.Persoane;

@Remote
public interface Persoane1DataService extends EntityRepository<Persoane>{
	Persoane createNewPersoane (Integer persoaneId);
	
	Invitatie getInvitatieById (Integer invitaionId);
	
	String getMessage();
}
