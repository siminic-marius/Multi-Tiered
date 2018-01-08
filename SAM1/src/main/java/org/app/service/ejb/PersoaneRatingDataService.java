package org.app.service.ejb;

import javax.ejb.Remote;

import org.app.patterns.EntityRepository;
import org.app.service.entities.Persoane;
import org.app.service.entities.Rating;

@Remote
public interface PersoaneRatingDataService extends EntityRepository<Persoane>{
	
	Persoane createNewPersoane(Integer Id);
	
	Rating getRatingById(Integer ratingId);
	
	String getMessage();
}
