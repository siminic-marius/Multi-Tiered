package org.app.service.ejb;

import java.util.Collection;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Invitatie;

@Path("invitations")
@Stateless @LocalBean
public class InvitationServiceEJB extends EntityRepositoryBase<Invitatie> implements InvitationService{

private static Logger logger = Logger.getLogger(InvitationServiceEJB.class.getName());

	@PostConstruct
	public void init() {
		logger.info("POSTCONSTRUCT-INIT injected EntityManager: " + this.em);
	}
	
	@Override
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Invitatie> toCollection(){
		logger.info("DEBUG REST toCollection()");
		return super.toCollection();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Invitatie> addIntoCollection(Invitatie invitatie){
		super.add(invitatie);
		logger.info("**** DEBUG Save aggregate post");
		return super.toCollection();
	}
	
	@GET @Path("/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Invitatie getById (@PathParam("id")Integer id) {
		Invitatie invitatie = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " + invitatie);
		return invitatie;
	}
	
	
	
	@GET @Path("/test")
	@Produces({MediaType.TEXT_PLAIN})
	@Override 
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Invitatie Service is on....";
	}

}
