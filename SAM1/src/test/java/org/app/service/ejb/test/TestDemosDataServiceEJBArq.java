package org.app.service.ejb.test;
import org.app.service.ejb.DemosService;
import org.app.service.ejb.DemosServiceEJB;
import org.app.service.entities.Demos;

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
public class TestDemosDataServiceEJBArq {
private static Logger logger = Logger.getLogger(TestDemosDataServiceEJBArq.class.getName());
	
	@EJB
	private static DemosService service;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(Demos.class.getPackage())
				.addClass(DemosService.class)
				.addClass(DemosServiceEJB.class)
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
	public void test2_getDemos() {
		logger.info("DEBUG: Junit TESTING: testGetDemos");
		
		Collection<Demos> demos = service.getDemos();
		assertTrue("Fail to read Demos!", demos.size() == 0);
	}
	
	@Test
	public void test3_addDemos() {
		logger.info("DEBUG Junit TESTING: testAddDemos...");
		
		Integer demosToAdd = 5;
		for(int i = 0; i < demosToAdd; i++) {
			service.AddDemos(new Demos(251 + i, "asasda" + (90+i), "asadasdasdasd" + (80 + i), 10.0 + i));
		}
		
		Collection<Demos> demos = service.getDemos();
		assertTrue("Fail to add a new Demos!", demos.size() == demosToAdd);
	}
	
	@Test
	public void test4_deleteDemos() {
		logger.info("DEBUG: Junit TESTING: testDeleteDemos...");
		
		Collection<Demos> demos = service.getDemos();
		for(Demos d: demos) {
			service.removeDemos(d);
		}
		Collection<Demos> demosAfterDelete = service.getDemos();
		assertTrue("Fail to read Demos!", demosAfterDelete.size() == 0);
	}
}
