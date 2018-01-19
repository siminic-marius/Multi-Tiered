package org.scrum.client.services;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
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

import org.scrum.client.dto.Persoane;


@Named @SessionScoped
public class PersoaneRESTServiceConsumer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PersoaneRESTServiceConsumer.class.getName());
	private static String serviceURL = "http://localhost:8080/SCRUM/data/persons";
	
	public List<Persoane> getPersoane() {
		logger.info(">>> REST Consumer: getPersoane.");
		List<Persoane> persoane = new ArrayList<Persoane>();
		persoane.addAll(this.getAllPersoane());
		return persoane;
	}
	
	@PostConstruct
	public void init() {
//		this.persoane.addAll(this.getAllPersoane());
	}
	
	public Collection<Persoane> getAllPersoane() {
		logger.info("DEBUG: PersoneRESTServiceCONSUMER: getAllPersoane...");
		Collection<Persoane> persoane = ClientBuilder.newClient().target(serviceURL).request().get()
								.readEntity(new GenericType<Collection<Persoane>>() {});
		persoane.stream().forEach(System.out::println);
		return persoane;
	}
	
	public List<Persoane> addPersoane(Persoane person) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: addProject: " + person);
		Client client = ClientBuilder.newClient();
		Collection<Persoane> persoane;
		
		persoane = client.target(serviceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(person, MediaType.APPLICATION_JSON))
				.readEntity(new GenericType<Collection<Persoane>>(){});
		
//		persoane.stream().forEach(System.out::println);
		return new ArrayList<> (persoane);
	}
	
	public Collection<Persoane> deletePersoane(Persoane person) {
		logger.info("DEBUG: ProjectsRESTServiceConsumer: deleteProject: " + person);
		
		String resourceURL = serviceURL + "/";
		
		Client client = ClientBuilder.newClient();
		client.target(resourceURL + person.getPersoanaId()).request().delete();
		
		Collection<Persoane> personsAfterDelete = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Persoane>>(){});	
		
		personsAfterDelete.stream().forEach(System.out::println);
		return personsAfterDelete;
	}
	
	public Persoane getByID(Integer persoanaId) {
		String resourceURL = serviceURL + "/" + persoanaId;
		logger.info("DEBUG: ProjectsRESTServiceConsumer: getByID: " + persoanaId);
		
		Persoane person = ClientBuilder.newClient().target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Persoane.class);
		
		assertNotNull("REST Data Service failed!", person);
		logger.info(">>>>>> DEBUG: REST Response ..." + person);
		
		return person;
	}	
	
	public Persoane updatePersoane(Persoane persoane) {
		String resourceURL = serviceURL + "/" + persoane.getPersoanaId();
		logger.info("DEBUG: ProjectsRESTServiceConsumer: project: " + persoane);
		Client client = ClientBuilder.newClient();

		// Get persoane server version
		Persoane serverPersoane= client.target(resourceURL)
				.request().accept(MediaType.APPLICATION_JSON)
				.get().readEntity(Persoane.class);
		
		logger.info(">>> Server-side Project: " + serverPersoane);
		
		persoane = client.target(resourceURL)
				//.request().accept(MediaType.APPLICATION_XML).header("Content-Type", "application/xml")
				.request().accept(MediaType.APPLICATION_JSON)
				.put(Entity.entity(persoane, MediaType.APPLICATION_JSON))
				.readEntity(Persoane.class);
		
		logger.info(">>> Updated Persoane: " + persoane);
		return persoane;
	}
	
	public Persoane createPersoane() {
		String resourceURL = serviceURL + "/new/"; //createNewProject
		logger.info("DEBUG: ProjectsRESTServiceConsumer: CreatePersoane ");

		Persoane persoane = ClientBuilder.newClient().target(resourceURL + 889)
					.request().accept(MediaType.APPLICATION_JSON)
					.post(null).readEntity(Persoane.class);
		logger.info(">>> Created/posted Persoane: " + persoane);
		
		return persoane;
	}
}
