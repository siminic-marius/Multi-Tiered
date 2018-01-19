package org.app.service.ejb;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.Invitatie;
import org.app.service.entities.Persoane;

@Path("persons")
@Stateless @LocalBean
public class Persoane1DataServiceEJB extends EntityRepositoryBase<Persoane> implements Persoane1DataService{
	private static Logger logger = Logger.getLogger(Persoane1DataServiceEJB.class.getName());
	
	private EntityRepository<Invitatie> invitatieRepository;
	
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		invitatieRepository = new EntityRepositoryBase<Invitatie>(this.em, Invitatie.class);
		logger.info("POSTCONSTRUCT-INIT releaseRepository: " + this.invitatieRepository);
	}
	

	@Override
	@GET 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Collection<Persoane> toCollection() {
		logger.info("**** DEBUG REST toCollection()");
		return super.toCollection();
	}	
	
	@GET @Path("/{id}") 	/* MSD-S4/data/projects/data/{id} 	REST-resource: project-entity*/
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Persoane getById(@PathParam("id") Integer id){ 
		Persoane persoane = super.getById(id);
		logger.info("**** DEBUG REST getById(" + id +") = " + persoane);
		return persoane;
	}	
	
	@POST 					/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	public Collection<Persoane> addIntoCollection(Persoane persoane) {
		// save aggregate
		super.add(persoane);
		logger.info("**** DEBUG REST save aggregate POST");
		// return updated collection
		return super.toCollection();
	}
	// 
	@PUT @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	@Override
	public Persoane add(Persoane persoane) {
		// restore aggregation-relation
		for (Invitatie r: persoane.getInvitatii())
			r.setPersons(persoane);
		logger.info("**** DEBUG REST restore aggregation-relation PUT");
		// save aggregate
		logger.info("**** DEBUG REST save aggregate PUT");
		persoane = super.add(persoane);
		// return updated collection	
		return persoane;
	}
	
	//@Override
	@DELETE 				/* MSD-S4/data/projects 		REST-resource: projects-collection*/
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public Collection<Persoane> removeFromCollection(Persoane persoane) {
		logger.info("DEBUG: called REMOVE - project: " + persoane);
		super.remove(persoane);
		return super.toCollection();
	}
	@DELETE @Path("/{id}") 	/* MSD-S4/data/projects/{id} 	REST-resource: project-entity*/	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
	public void remove(@PathParam("id")Integer id) {
		logger.info("DEBUG: called REMOVE - ById() for projects >>>>>>>>>>>>>> simplified ! + id");
		Persoane persoane = super.getById(id);
		super.remove(persoane);
	}
	
	// GET method on second repository for Release-type entities
		@GET @Path("/{persoanaId}/invitatii/{invitaionId}")
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public Invitatie getInvitatieById(@PathParam("invitaionId") Integer invitaionId){
			logger.info("DEBUG: called getReleaseById() for projects >>>>>>>>>>>>>> simplified !");
			Invitatie invitatie = invitatieRepository.getById(invitaionId);
			return invitatie;
		}
		
	
		@POST @Path("/new/{id}")
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })	
		@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // autonomous transaction
		// Aggregate factory method
		public Persoane createNewPersoane(@PathParam("id")Integer id){
			logger.info("**** DEBUG REST createNewProject POST");
			// create project aggregate
			Persoane persoana = new Persoane(id, "NEW PatientRESTTT" + "." + id);
			List<Invitatie> invitatii = new ArrayList<>();
			
			Integer admissionsCount = 3;
			for (int i=0; i<=admissionsCount-1; i++){
				invitatii.add(new Invitatie(i, persoana));
			}
			persoana.setInvitatii(invitatii);
			// save project aggregate
			this.add(persoana);
			// return project aggregate to service client
			return persoana;
		}

		@GET @Path("/test") // Check if resource is up ...
		@Produces({ MediaType.TEXT_PLAIN})
		public String getMessage(){
			return "Patient DataService is working...";
		}
		
		@GET @Path("/patientdata")
		@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		public Response getProjectData() throws Exception{
			Persoane dto = new Persoane(1111, "Pro 1111REST");
			JAXBContext jaxbContext = JAXBContext.newInstance(Persoane.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(dto, os);
			String aString = new String(os.toByteArray(),"UTF-8");
			
			Response response = Response
					.status(Status.OK)
					.type(MediaType.TEXT_PLAIN)
					.entity(aString)
					.build();
			
			return response;
		}
}
