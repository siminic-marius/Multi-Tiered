package org.app.service.rest.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.app.patterns.EntityRepository;
import org.app.service.ejb.PersoaneService;
import org.app.service.entities.Persoane;
import org.app.service.rest.ApplicationConfig;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPersoaneDataServiceRestArq {
	private static Logger logger = Logger.getLogger(TestPersoaneDataServiceRestArq.class.getName());
	
	private static String serviceURL = "http://localhost:8080/msd-s4-test/data/persons";
	
	@Deployment // Arquilian infrastructure
	public static Archive<?> createDeployment() {
	        return ShrinkWrap
	                .create(WebArchive.class, "msd-s4-test.war")
	                .addPackage(Persoane.class.getPackage())
	                .addPackage(PersoaneService.class.getPackage())
	                .addPackage(EntityRepository.class.getPackage())
	                .addPackage(ApplicationConfig.class.getPackage())
	                .addAsResource("META-INF/persistence.xml")
	                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void test1_GetMessage() {
		String resourceURL = serviceURL + "/test";
		logger.info("DEBUG: Junit TESTING: test1_GetMessage ...");
		String response = ClientBuilder.newClient().target(resourceURL)
				.request().get()
				.readEntity(String.class);
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}
	
	@Test
	public void test2_DeletePersoane() {
		String resourceURL = serviceURL + "/";
		logger.info("DEBUG: Junit TESTING: test2_DeleteProject ...");
		Client client = ClientBuilder.newClient();
		Collection<Persoane> persoane = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Persoane>>(){});		
		
		for (Persoane p: persoane) {
			client.target(resourceURL + p.getPersoanaId()).request().delete();
		}
		
		Collection<Persoane> persoaneAfterDelete = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Persoane>>(){});	
		assertTrue("Fail to read Projects!", persoaneAfterDelete.size() == 0);
	}
	
	@Test
	public void test3_CreatePersoane() {
		logger.info("DEBUG: Junit TESTING: test3_CreateProject ...");
		Client client = ClientBuilder.newClient();
		Collection<Persoane> persoane;
		Integer persoaneToAdd = 3;
		Persoane person;
		String sexChoice ="";
		for (int i=1; i <= persoaneToAdd; i++){
			if(i%2 == 0) {
				sexChoice = "M"; 
			} else {
				sexChoice = "F";
			}
			person = new Persoane(i + 111, "Persoana: " + (115+i), "Iasi: " + (80 + i), sexChoice, "persoana" + (10 + i) + "@gmail.com");
			persoane = client.target(serviceURL)
					.request().accept(MediaType.APPLICATION_XML)
					.post(Entity.entity(person, MediaType.APPLICATION_XML))////////////////////////////////////////not working on JSON
					.readEntity(new GenericType<Collection<Persoane>>() {});
			assertTrue("Fail to read Persoane!", persoane.size() > 0);
		}
		persoane = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Persoane>>(){});
		assertTrue("Fail to add Projects!", persoane.size() >= persoaneToAdd);
		persoane.stream().forEach(System.out::println);
	}
	
	@Test
	public void test4_GetEmployee() {
		logger.info("DEBUG: Junit TESTING: test4_GetEmployee ...");
		Collection<Persoane> persoane = ClientBuilder.newClient()
				.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Persoane>>(){});
		assertTrue("Fail to read Persoane!", persoane.size() > 0);
		persoane.stream().forEach(System.out::println);
	}
	
	
	@Test
	public void test5_GetByID() {
		String resourceURL = serviceURL + "/112";
		logger.info("DEBUG: Junit TESTING: test1_GetMessage ...");
		
		Persoane persoane = ClientBuilder.newClient().target(resourceURL)
				.request().accept(MediaType.APPLICATION_XML)
				.get().readEntity(Persoane.class);
		
		assertNotNull("REST Data Service failed!", persoane);
		logger.info(">>>>>> DEBUG: REST Response ..." + persoane);
	}
	
	@Test
	public void test6_createNewPersoana() {
		String resourceURL = serviceURL + "/new/";
		logger.info("DEBUG: Junit TESTING: test3_CreatePersoana...");
		Client client = ClientBuilder.newClient();
		
		Integer persoaneToAdd = 3;
		Persoane persoana;
		for (int i=1; i <= persoaneToAdd; i++){
			persoana = ClientBuilder.newClient().target(resourceURL + i)
					.request().accept(MediaType.APPLICATION_XML)
					.post(null).readEntity(Persoane.class);
			System.out.println(">>> Created/posted Persoane: " + persoana);
		}

		Collection<Persoane> persons = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Persoane>>(){});
		
		assertTrue("Fail to add Persoane!", persons.size() >= persoaneToAdd);
		
	}

}
