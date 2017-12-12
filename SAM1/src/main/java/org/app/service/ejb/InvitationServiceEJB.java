package org.app.service.ejb;

import java.util.List;
import java.util.Collection;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.app.service.entities.Invitation;

@Stateless @LocalBean
public class InvitationServiceEJB implements InvitationService{

private static Logger logger = Logger.getLogger(InvitationServiceEJB.class.getName());
	
	@PersistenceContext(unitName="MSD")
	private EntityManager em;
	
	public InvitationServiceEJB() {
	}
	
	@PostConstruct
	public void init(){
		logger.info("POSTCONSTRUCT-INIT : " + this.em);
	}	
	
	@Override
	public Invitation AddInvitation(Invitation invitationToAdd) {
		em.persist(invitationToAdd);
		em.flush();
		em.refresh(invitationToAdd);
		return invitationToAdd;
	}

	@Override
	public String removeInvitation(Invitation invitationToDelete) {
		invitationToDelete = em.merge(invitationToDelete);
		em.remove(invitationToDelete);
		em.flush();
		return "True";
	}

	@Override
	public Invitation getInvitationByidInvitation(Integer idInvitation) {
		// TODO Auto-generated method stub
		return em.find(Invitation.class, idInvitation);
	}

	@Override
	public Collection<Invitation> getInvitation() {
		List<Invitation> invitatii = em.createQuery("SELECT i FROM Invitation i ", Invitation.class).getResultList();
		return invitatii;
	}

	@Override
	public Invitation getInvitationByTitle(String invitationTitle) {
		return em.createQuery("SELECT i FROM Invitation i WHERE i.title = :title", Invitation.class)
				.setParameter("title", invitationTitle).getSingleResult();
	}

	@Override
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Invitation Service is on....";
	}

}
