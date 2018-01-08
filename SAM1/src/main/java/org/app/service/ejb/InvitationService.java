package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Invitatie;

@Remote
public interface InvitationService extends EntityRepository<Invitatie>{
		
	String sayRest();
}
