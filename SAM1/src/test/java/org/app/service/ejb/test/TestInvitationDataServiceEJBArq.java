package org.app.service.ejb.test;
import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.ejb.InvitationService;
import org.app.service.ejb.InvitationServiceEJB;
import org.app.service.entities.Invitatie;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

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
public class TestInvitationDataServiceEJBArq {
		private static Logger logger = Logger.getLogger(TestInvitationDataServiceEJBArq.class.getName());
		
		@EJB
		private static InvitationService service;
		
		@Deployment
		public static Archive<?> createDeployment() {
			return ShrinkWrap
					.create(WebArchive.class, "msd-test.war")
					.addPackage(Invitatie.class.getPackage())
					.addClass(InvitationService.class)
					.addClass(InvitationServiceEJB.class)
					.addClass(EntityRepository.class)
					.addClass(EntityRepositoryBase.class)
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
		public void test4_getInvitation() {
			logger.info("DEBUG: Junit TESTING: testGetPersoane");
			
			Collection<Invitatie> invitations = service.toCollection();
			assertTrue("Fail to read invitations!", invitations.size() > 0);
		}
		
		@Test
		public void test3_addInvitation() {
			logger.info("DEBUG Junit TESTING: testAddPersoana ...");
			
			Date dataInvitatie = new Date();
			
			Long numar = 30l * 24 * 60 * 60 * 1000;
			
			Date dataEvent = new Date();
			
			String sexChoice="";
			Integer invitationToAdd = 5;
			for(int i = 0; i < invitationToAdd; i++) {
				if(i%2 == 0) {
					sexChoice = "M"; 
				} else {
					sexChoice = "F";
				}
				
				service.add(new Invitatie(i, "Invitatie Event Number: " + (80+i), "We invite you to our event: " + (80 + i), new Date (dataInvitatie.getTime() + i * numar), 
						 null));
			}
			
			Collection<Invitatie> invitations = service.toCollection();
			assertTrue("Fail to add a new invitation!", invitations.size() == invitationToAdd);
		}
		
		@Test
		public void test2_deleteInvitation() {
			logger.info("DEBUG: Junit TESTING: testDeletePersoana ...");
			
			Collection<Invitatie> invitations = service.toCollection();
			for(Invitatie i: invitations) {
				service.remove(i);
			}
			Collection<Invitatie> invitationsAfterDelete = service.toCollection();
			assertTrue("Fail to read invitations!", invitationsAfterDelete .size() == 0);
		}
}