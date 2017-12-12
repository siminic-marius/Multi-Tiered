package org.app.service.ejb;

import org.app.service.entities.Rating;

import java.util.logging.Logger;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless @LocalBean
public class RatingServiceEJB implements RatingService{
	
	private static Logger logger = Logger.getLogger(RatingServiceEJB.class.getName());
	
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	
	public RatingServiceEJB() {
	}
	
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}	
	
	@Override
	public Rating AddRating(Rating ratingToAdd) {
		em.persist(ratingToAdd);
		em.flush();
		em.refresh(ratingToAdd);
		return ratingToAdd;
	}

	@Override
	public String removeRating(Rating ratingToDelete) {
		ratingToDelete = em.merge(ratingToDelete);
		em.remove(ratingToDelete);
		em.flush();
		return "True";
	}

	@Override
	public Rating getRatingByratingID(Integer ratingId) {
		return em.find(Rating.class, ratingId);
	}

	@Override
	public Collection<Rating> getRating() {
		List<Rating> rating = em.createQuery("SELECT r FROM Rating r", Rating.class).getResultList();
		return rating;
	}

	@Override
	public Rating getRatingBytitle(String titleRating) {
		return em.createQuery("SELECT r FROM Rating r where r.titleRating = :titleRating", Rating.class).setParameter("titleRating", titleRating).getSingleResult();

	}

	@Override
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Rating service is on....";
	}

}
