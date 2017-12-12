package org.app.service.ejb;
import org.app.service.entities.Demos;

import java.util.logging.Logger;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless @LocalBean
public class DemosServiceEJB implements DemosService{
	private static Logger logger = Logger.getLogger(DemosServiceEJB.class.getName());
	
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	
	public DemosServiceEJB() {
	}
	
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}

	@Override
	public Demos AddDemos(Demos demosToAdd) {
		em.persist(demosToAdd);
		em.flush();
		em.refresh(demosToAdd);
		return demosToAdd;
	}

	@Override
	public String removeDemos(Demos demosToDelete) {
		demosToDelete = em.merge(demosToDelete);
		em.remove(demosToDelete);
		em.flush();
		return "True";
	}

	@Override
	public Demos getDemosByDemosID(Integer demosId) {
		return em.find(Demos.class, demosId);
	}

	@Override
	public Collection<Demos> getDemos() {
		List<Demos> demos = em.createQuery("SELECT d FROM Demos d", Demos.class).getResultList();
		return demos;
	}

	@Override
	public Demos getDemosByTitle(String demosTitle) {
		return em.createQuery("SELECT d FROM Demos d where d.titleDemos = :titleDemos", Demos.class)
				.setParameter("titleDemos", demosTitle).getSingleResult();

	}

	@Override
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Demos serice is on...";
	}	
}
