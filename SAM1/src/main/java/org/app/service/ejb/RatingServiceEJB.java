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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ratings")
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
	
	@PUT @Path("/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })		
	@Override
	public Rating AddRating(Rating ratingToAdd) {
		em.persist(ratingToAdd);
		em.flush();
		em.refresh(ratingToAdd);
		return ratingToAdd;
	}
	@Override
	@GET @Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Rating getRatingByratingID(@PathParam("id") Integer ratingId) {
		logger.info("**** DEBUG REST getRatingByratingID: id = " + ratingId);
		return em.find(Rating.class, ratingId);
	}
	
	@DELETE 
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public String removeRating(Rating ratingToDelete) {
		ratingToDelete = em.merge(ratingToDelete);
		em.remove(ratingToDelete);
		em.flush();
		return "True";
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@Override
	public Collection<Rating> getRating() {
		List<Rating> rating = em.createQuery("SELECT r FROM Rating r", Rating.class).getResultList();
		return rating;
	}
	
	@GET @Path("/{titleRating}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })		
	@Override
	public Collection<Rating> getRatingBytitle(@PathParam("titleRating") String titleRating) {
		
		return em.createQuery("SELECT r FROM Rating r where r.titleRating = :titleRating", Rating.class).setParameter("titleRating", titleRating).getResultList();
	}

	@Override
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Rating service is on....";
	}

}
