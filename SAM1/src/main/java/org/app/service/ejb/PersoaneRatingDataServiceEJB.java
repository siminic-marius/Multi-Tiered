package org.app.service.ejb;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Persoane;
import org.app.service.entities.Rating;

@Stateless @LocalBean
public class PersoaneRatingDataServiceEJB extends EntityRepositoryBase<Persoane>  implements PersoaneRatingDataService, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2988182192511945158L;

	private static Logger logger = Logger.getLogger(PersoaneRatingDataServiceEJB.class.getName());
	
	@EJB
	private RatingService ratingService;
	
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		logger.info("POSTCONSTRUCT-INIT ratingService: " + this.ratingService);
	}

	@Override
	public Persoane createNewPersoane(Integer Id) {
		Persoane persoane = new Persoane (Id, "Persoana: " + Id, "Iasi: " + Id, "F", "persoana" + Id + "@gmail.com");
		
//		List<Rating> ratings = new ArrayList<>();
//		LocalDateTime dataRating = LocalDateTime.of(2018, Month.JUNE, 23, 16, 40, 00);
//		
//		int ratingsToAdd = 5;
//		for(int i = 0; i < ratingsToAdd; i++) {
//			ratings.add(new Rating(2000 + i, "Rating: " + persoane.getPersoanaId() + "/" + i, dataRating.plusMonths(i), "It was an awesome event " + i + "!", 8+1, persoane ,null));
//		}
//		
//		persoane.setRatinguri(ratings);;
//		
//		//save
		this.add(persoane);
		
		return persoane;
	}

	@Override
	public Rating getRatingById(Integer ratingId) {
		return ratingService.getRatingByratingID(ratingId);
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "PersoaneRatingDataService is working";
	}

}
