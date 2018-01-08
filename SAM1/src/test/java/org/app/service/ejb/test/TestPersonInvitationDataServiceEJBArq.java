package org.app.service.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.app.service.entities.Invitatie;
import org.app.service.entities.Persoane;


import javax.ejb.EJB;
import org.app.patterns.EntityRepository;
import org.app.service.ejb.InvitationService;
import org.app.service.ejb.InvitationServiceEJB;
import org.app.service.ejb.PersonInvitationDataService;
import org.app.service.ejb.PersonInvitationDataServiceEJB;

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
public class TestPersonInvitationDataServiceEJBArq {
	
	private static Logger logger = Logger.getLogger(TestPersonInvitationDataServiceEJBArq.class.getName());
	
	@Deployment
	public static Archive<?> createDeployment(){
		return ShrinkWrap
				.create(WebArchive.class, "msd-test.war")
				.addPackage(EntityRepository.class.getPackage()).addPackage(Persoane.class.getPackage())
				.addClass(InvitationService.class).addClass(InvitationServiceEJB.class)
				.addClass(PersonInvitationDataService.class).addClass(PersonInvitationDataServiceEJB.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@EJB
	private static PersonInvitationDataService service;
	
	@Test
	public void test1_GetMessage() {
		logger.info("DEBUG: Junit TESTING: testGetMessage ...");
		String response = service.getMessage();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}
	
	@Test
	public void test4_getPerson() {
		logger.info("DEBUG: Junit TESTING: testGetPerson:121  ...");
		Persoane persoane = service.getById(121);
		assertNotNull("Fail to get Person 121!", persoane);
	}
	//create aggregate
	@Test
	public void test3_createPerson() {
		Persoane persoane = service.addNewPerson(121);
		
		assertNotNull("Fail to create new person in repository!'", persoane);
		//update project
		persoane.setName(persoane.getName() + " Married");
		List<Invitatie> invitatii = persoane.getInvitatii();
		
		for(Invitatie i: invitatii) {
			i.setMessageInvitatie(i.getMessageInvitatie() + " Happy Holidays!");
		}
		persoane = service.add(persoane);
		assertNotNull("Fail to save new person in repository!'", persoane);
		
		logger.info("DEBUG createNewPerson: person changed: " + persoane);
		
		//check read
		
		persoane = service.getById(121);
		assertNotNull("Fail to find changed persoana in repository!'", persoane);
		logger.info("DEBUG createNewPerson: queried Person: " + persoane);
	}
	
	@Test
	public void test2_deletePersoane() {
		logger.info("DEBUG Junit TESTING: testDeletePerson with id  121 ");
		Persoane persoane = service.getById(121);
		
		if(persoane != null) {
			service.remove(persoane);
		}
		
		persoane = service.getById(121);
		assertNull("Fail to delete person with id 121!", persoane);
	}
}
