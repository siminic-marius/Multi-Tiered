package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Persoane;

@Remote
public interface PersoaneService extends EntityRepository<Persoane>{
	
	String sayRest();
}
