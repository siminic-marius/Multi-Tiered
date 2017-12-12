package org.app.service.ejb;
import org.app.service.entities.Persoane;

import java.util.logging.Logger;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless @LocalBean
public class PersoaneServiceEJB implements PersoaneService{

	private static Logger logger = Logger.getLogger(PersoaneServiceEJB.class.getName());
	
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	
	public PersoaneServiceEJB() {
	}
	
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}	
	
	@Override
	public Persoane AddPersoana(Persoane personToAdd) {
		em.persist(personToAdd);
		em.flush();
		em.refresh(personToAdd);
		return personToAdd;
	}

	@Override
	public Persoane getPersoanaByPersoanaID(Integer persoanaId) {
		// TODO Auto-generated method stub
		return em.find(Persoane.class, persoanaId);
	}

	public Collection<Persoane> getPersoane() {
		List<Persoane> persoane = em.createQuery("SELECT p FROM Persoane p", Persoane.class).getResultList();
		return persoane;
	}
	
	@Override
	public String removePersoane(Persoane persoanaToDelete) {
		persoanaToDelete = em.merge(persoanaToDelete);
		em.remove(persoanaToDelete);
		em.flush();
		return "True";
	}

	@Override
	public Persoane getPersoaneByName(String persoanaName) {
		return em.createQuery("SELECT p FROM Persoane p where p.name = :name", Persoane.class).setParameter("name", persoanaName).getSingleResult();
	}

	@Override
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Persoane Service is on....";
	}
	
}
