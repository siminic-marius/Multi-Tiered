package org.app.service.ejb;
import org.app.service.entities.Workshops;

import java.util.logging.Logger;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless @LocalBean
public class WorkshopsServiceEJB implements WorkshopsService{
private static Logger logger = Logger.getLogger(WorkshopsServiceEJB.class.getName());
	
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	
	public WorkshopsServiceEJB() {
	}
	
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}

	@Override
	public Workshops AddWorkshops(Workshops workshopToAdd) {
		em.persist(workshopToAdd);
		em.flush();
		em.refresh(workshopToAdd);
		return workshopToAdd;
	}

	@Override
	public String removeWorkshops(Workshops workshopsToDelete) {
		workshopsToDelete = em.merge(workshopsToDelete);
		em.remove(workshopsToDelete);
		em.flush();
		return "True";
	}

	@Override
	public Workshops getWorkshopsByWorkshopsID(Integer workshopsId) {
		return em.find(Workshops.class, workshopsId);
	}

	@Override
	public Collection<Workshops> getWorkshops() {
		List<Workshops> workshops = em.createQuery("SELECT w FROM Workshops w", Workshops.class).getResultList();
		return workshops;	
	}

	@Override
	public Workshops getWorkshopsByName(String workshopsName) {
		return em.createQuery("SELECT w FROM Workshops w where w.titleWorkshop = :titleWorkshop", Workshops.class)
				.setParameter("titleWorkshop", workshopsName).getSingleResult();

	}

	@Override
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Workshops service is on...";
	}
	
}
