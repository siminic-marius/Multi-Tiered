package org.app.service.ejb.test;
import org.app.service.ejb.PersoaneService;
import org.app.service.ejb.PersoaneServiceEJB;
import org.app.service.entities.Persoane;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
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
public class TestPersoaneDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestPersoaneDataServiceEJBArq.class.getName());
	
	@EJB
	private static PersoaneService service;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(Persoane.class.getPackage())
				.addClass(PersoaneService.class)
				.addClass(PersoaneServiceEJB.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
				
	}
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: getMessage ...");
		String response = service.sayRest();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}
	
	@Test
	public void test2_getPersoane() {
		logger.info("DEBUG: Junit TESTING: testGetPersoane");
		
		Collection<Persoane> persoane = service.getPersoane();
		assertTrue("Fail to read persoane!", persoane.size() == 0);
	}
	
	@Test
	public void test3_addPersoane() {
		logger.info("DEBUG Junit TESTING: testAddPersoana ...");
		
		Integer personToAdd = 5;
		for(int i = 0; i < personToAdd; i++) {
			service.AddPersoana(new Persoane(null, "asasda" + (90+i), "asadasdasdasd" + (80 + i), "azzzzzz" + (50 + i), "zzzzzxx" + (10 + i)));
		}
		
		Collection<Persoane> persons = service.getPersoane();
		assertTrue("Fail to add a new person!", persons.size() == personToAdd);
	}
	
	@Test
	public void test4_deletePersoana() {
		logger.info("DEBUG: Junit TESTING: testDeletePersoana ...");
		
		Collection<Persoane> persoane = service.getPersoane();
		for(Persoane p: persoane) {
			service.removePersoane(p);
		}
		Collection<Persoane> persoaneAfterDelete = service.getPersoane();
		assertTrue("Fail to read persoane!", persoaneAfterDelete.size() == 0);
	}
	
}
