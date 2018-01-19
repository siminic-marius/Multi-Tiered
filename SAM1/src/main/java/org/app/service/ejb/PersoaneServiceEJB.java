package org.app.service.ejb;
import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Invitatie;
import org.app.service.entities.Persoane;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("persons123")
@Stateless @LocalBean
public class PersoaneServiceEJB extends EntityRepositoryBase<Persoane> implements PersoaneService{

	private static Logger logger = Logger.getLogger(PersoaneServiceEJB.class.getName());
	
	// Local component-entity-repository
	private EntityRepository<Invitatie> invitatieRepository;
	
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		invitatieRepository = new EntityRepositoryBase<Invitatie>(this.em, Invitatie.class);
		logger.info("POSTCONSTRUCT-INIT invitatieRepository: " + this.invitatieRepository);
	}
	
	@Override
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Persoane> toCollection() {
		logger.info("DEBUG REST toCollection()");
		return super.toCollection();
	}
	
	@GET @Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Persoane getById(@PathParam("id") Integer id){ 
		Persoane persoane = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " + persoane);
		return persoane;
	}	
	
	@POST 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Collection<Persoane> addIntoCollection(Persoane persoana) {
		// save aggregate
		super.add(persoana);
		logger.info("**** DEBUG REST save aggregate POST");
		// return updated collection
		return super.toCollection();
	}
	
	@PUT @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Override
	public Persoane add(Persoane persoana) {
		// restore aggregation-relation
		for (Invitatie i: persoana.getInvitatii())
			i.setPersons(persoana);
		logger.info("**** DEBUG REST restore aggregation-relation PUT");
		// save aggregate
		logger.info("**** DEBUG REST save aggregate PUT");
		persoana = super.add(persoana);
		// return updated collection	
		return persoana;
	}	
	
//	@Override
	@DELETE 				/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public Collection<Persoane> removeFromCollection(Persoane persoana) {
		logger.info("DEBUG: called REMOVE - person: " + persoana);
		super.remove(persoana);
		return super.toCollection();
	}
	
	@DELETE @Path("/{id}") 		
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remove(@PathParam("id")Integer id) {
		logger.info("DEBUG: called REMOVE - ById() for projects >>>>>>>>>>>>>> simplified ! + id");
		Persoane persoana = super.getById(id);
		super.remove(persoana);
	}
	
	// GET method on second repository for Release-type entities
	@GET @Path("/{persoaneid}/invitatii/{invitatieid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Invitatie getInvitatieById(@PathParam("invitatieid") Integer invitatieid){
		logger.info("DEBUG: called getInvitatieById() for persoane >>>>>>>>>>>>>> simplified !");
		Invitatie invitatie = invitatieRepository.getById(invitatieid);
		return invitatie;
	}
	
	@POST @Path("/new/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Persoane addNewPerson(@PathParam("id")Integer id) {
		
		Persoane persoane = new Persoane(id, "New Person " + id, "Iasi: " + id, "M", "person" + id + "@gmail.com");
		List<Invitatie> invitationsPersons = new ArrayList<>();
		
		Date dataInvitatie = new Date();
		Long interval = 30l * 24 * 60 * 60 * 1000;
		
		Integer invitationsCount = 5;
		for(int i =0; i < invitationsCount; i++) {
			invitationsPersons.add(new Invitatie(i + 525 , "I: " + persoane.getPersoanaId() + i, "Invitatie: " + i,  new Date(dataInvitatie.getTime() + i* interval),persoane));
		}
		persoane.setInvitatii(invitationsPersons);
		
		this.add(persoane);
		
		return persoane;
	}
	
	@GET @Path("/test") // Check if resource is up ...
	@Produces({ MediaType.TEXT_PLAIN})
	public String sayRest() {
		// TODO Auto-generated method stub
		return "Persoane DataService is working...";
	}	
}
