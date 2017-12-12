package org.app.service.ejb.test;
import org.app.service.ejb.PresentationService;
import org.app.service.ejb.PresentationServiceEJB;
import org.app.service.entities.Persoane;
import org.app.service.entities.Presentations;

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
public class TestPresentationsDataServiceEJBArq {
private static Logger logger = Logger.getLogger(TestPresentationsDataServiceEJBArq.class.getName());
	
	@EJB
	private static PresentationService service;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(Persoane.class.getPackage())
				.addClass(PresentationService.class)
				.addClass(PresentationServiceEJB.class)
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
	public void test2_getPresentations() {
		logger.info("DEBUG: Junit TESTING: testGetPresentations");
		
		Collection<Presentations> presentations = service.getPresentations();
		assertTrue("Fail to read presentations!", presentations.size() == 0);
	}
	
	@Test
	public void test3_addPresentation() {
		logger.info("DEBUG Junit TESTING: testAddpresentations...");
		
		Integer presentationsToAdd = 5;
		for(int i = 0; i < presentationsToAdd; i++) {
			service.AddPresentations(new Presentations(100 + i , "asasda" + (90+i), 10 + i));
		}
		
		Collection<Presentations> presentations = service.getPresentations();
		assertTrue("Fail to add a new presentations!", presentations.size() == presentationsToAdd);
	}
	
	@Test
	public void test4_deletePresentation() {
		logger.info("DEBUG: Junit TESTING: testDeletepresentations...");
		
		Collection<Presentations> presentations = service.getPresentations();
		for(Presentations p: presentations) {
			service.removePresentations(p);
		}
		Collection<Presentations> presentationsAfterDelete = service.getPresentations();
		assertTrue("Fail to read presentations!", presentationsAfterDelete.size() == 0);
	}
}
