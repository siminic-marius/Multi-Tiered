package org.app.service.ejb;
import org.app.service.entities.Presentations;

import java.util.logging.Logger;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless @LocalBean
public class PresentationServiceEJB implements PresentationService{
private static Logger logger = Logger.getLogger(PresentationServiceEJB.class.getName());
	
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	
	public PresentationServiceEJB() {
	}
	
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}

	@Override
	public Presentations AddPresentations(Presentations presentationsToAdd) {
		em.persist(presentationsToAdd);
		em.flush();
		em.refresh(presentationsToAdd);
		return presentationsToAdd;
	}

	@Override
	public String removePresentations(Presentations presentationsToDelete) {
		presentationsToDelete = em.merge(presentationsToDelete);
		em.remove(presentationsToDelete);
		em.flush();
		return "True";
	}

	@Override
	public Presentations getPresentationsByPresentationsId(Integer presentationsId) {
		return em.find(Presentations.class, presentationsId);
	}

	@Override
	public Collection<Presentations> getPresentations() {
		List<Presentations> presentations = em.createQuery("SELECT p FROM Presentations p", Presentations.class).getResultList();
		return presentations;
	}

	@Override
	public Presentations getPresentationsByName(String presentationsName) {
		return em.createQuery("SELECT p FROM Presentations p where p.eventName = :eventName", Presentations.class)
				.setParameter("eventName", presentationsName).getSingleResult();

	}

	@Override
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Presentation service is on...";
	}	
}
