package org.scrum.client.services;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.scrum.client.dto.Invitatie;

import java.io.Serializable;

@Named @SessionScoped
public class InvitatieRestServiceConsumer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PersoaneRESTServiceConsumer.class.getName());
	private static String serviceURL = "http://localhost:8080/SCRUM/data/invitations";
	
	public List<Invitatie> getInvitatie() {
		logger.info(">>> REST Consumer: getPersoane.");
		List<Invitatie> invitatie = new ArrayList<Invitatie>();
		invitatie.addAll(this.getAllInvitatie());
		return invitatie;
	}
	
	@PostConstruct
	public void init() {
	}
	
	public Collection<Invitatie> getAllInvitatie() {
		logger.info("DEBUG: PersoneRESTServiceCONSUMER: getAllPersoane...");
		Collection<Invitatie> invitatie= ClientBuilder.newClient().target(serviceURL).request().get()
								.readEntity(new GenericType<Collection<Invitatie>>() {});
		invitatie.stream().forEach(System.out::println);
		return invitatie;
	}
	
	public List<Invitatie> addInvitatie(Invitatie invitation) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: addInvitatie: " + invitation);
		Client client = ClientBuilder.newClient();
		Collection<Invitatie> invitatie;
		
		invitatie = client.target(serviceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(invitation, MediaType.APPLICATION_JSON))
				.readEntity(new GenericType<Collection<Invitatie>>(){});
		
//		persoane.stream().forEach(System.out::println);
		return new ArrayList<> (invitatie);
	}
	
	public Collection<Invitatie> deletePersoane(Invitatie invitation) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: deleteProject: " + invitation);
		
		String resourceURL = serviceURL + "/";
		
		Client client = ClientBuilder.newClient();
		client.target(resourceURL + invitation.getinvitationId()).request().delete();
		
		Collection<Invitatie> invitatieAfterDelete = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Invitatie>>(){});	
		
		invitatieAfterDelete.stream().forEach(System.out::println);
		return invitatieAfterDelete;
	}
	
	public Invitatie getByID(Integer invitatieId) {
		String resourceURL = serviceURL + "/" + invitatieId;
		logger.info("DEBUG: ProjectsRESTServiceConsumer: getByID: " + invitatieId);
		
		Invitatie invitation = ClientBuilder.newClient().target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Invitatie.class);
		
		assertNotNull("REST Data Service failed!", invitation);
		logger.info(">>>>>> DEBUG: REST Response ..." + invitation);
		
		return invitation;
	}	
	
	public Invitatie updateInvitatie(Invitatie invitatie) {
		String resourceURL = serviceURL + "/" + invitatie.getinvitationId();
		logger.info("DEBUG: ProjectsRESTServiceConsumer: project: " + invitatie);
		Client client = ClientBuilder.newClient();

		// Get persoane server version
		Invitatie serverInvitatie= client.target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Invitatie.class);
		
		logger.info(">>> Server-side Project: " + serverInvitatie);
		
		invitatie = client.target(resourceURL)
				//.request().accept(MediaType.APPLICATION_XML).header("Content-Type", "application/xml")
				.request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(invitatie, MediaType.APPLICATION_JSON))
				.readEntity(Invitatie.class);
		
		logger.info(">>> Updated Invitatie: " + invitatie);
		return invitatie;
	}
	
	public Invitatie createInvitatie() {
		String resourceURL = serviceURL + "/new/"; //createNewProject
		logger.info("DEBUG: ProjectsRESTServiceConsumer: CreateInvitatie ");

		Invitatie invitatie = ClientBuilder.newClient().target(resourceURL + 1123)
					.request().accept(MediaType.APPLICATION_JSON)
					.post(null).readEntity(Invitatie.class);
		logger.info(">>> Created/posted Invitatie: " + invitatie);
		
		return invitatie;
	}
}
