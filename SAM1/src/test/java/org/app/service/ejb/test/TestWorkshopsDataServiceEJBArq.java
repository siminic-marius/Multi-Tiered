package org.app.service.ejb.test;
import org.app.service.ejb.WorkshopsService;
import org.app.service.ejb.WorkshopsServiceEJB;
import org.app.service.entities.Workshops;

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
public class TestWorkshopsDataServiceEJBArq {
	private static Logger logger = Logger.getLogger(TestWorkshopsDataServiceEJBArq.class.getName());
	
	@EJB
	private static WorkshopsService service;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(Workshops.class.getPackage())
				.addClass(WorkshopsService.class)
				.addClass(WorkshopsServiceEJB.class)
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
	public void test2_getWorkshops() {
		logger.info("DEBUG: Junit TESTING: testGetWorkshops");
		
		Collection<Workshops> workshops = service.getWorkshops();
		assertTrue("Fail to read Workshops!", workshops.size() == 0);
	}
	
	@Test
	public void test3_addWorkshops() {
		logger.info("DEBUG Junit TESTING: testAddWorkshops...");
		
		Integer workshopsToAdd = 5;
		for(int i = 0; i < workshopsToAdd; i++) {
			service.AddWorkshops(new Workshops(150 + i, "asasda" + (90+i), "asadasdasdasd" + (80 + i)));
		}
		
		Collection<Workshops> workshops = service.getWorkshops();
		assertTrue("Fail to add a new Workshops!", workshops.size() == workshopsToAdd);
	}
	
	@Test
	public void test4_deleteWorkshops() {
		logger.info("DEBUG: Junit TESTING: testDeleteWorkshops...");
		
		Collection<Workshops> workshops = service.getWorkshops();
		for(Workshops w: workshops) {
			service.removeWorkshops(w);
		}
		Collection<Workshops> workshopsAfterDelete = service.getWorkshops();
		assertTrue("Fail to read Workshops!", workshopsAfterDelete.size() == 0);
	}
}
