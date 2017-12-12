package org.app.service.ejb.test;
import org.app.service.ejb.InvitationService;
import org.app.service.ejb.InvitationServiceEJB;
import org.app.service.entities.Invitation;
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
public class TestInvitationDataServiceEJBArq {
		private static Logger logger = Logger.getLogger(TestInvitationDataServiceEJBArq.class.getName());
		
		@EJB
		private static InvitationService service;
		
		@Deployment
		public static Archive<?> createDeployment() {
			return ShrinkWrap
					.create(WebArchive.class, "msd-test.war")
					.addPackage(Invitation.class.getPackage())
					.addClass(InvitationService.class)
					.addClass(InvitationServiceEJB.class)
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
		public void test2_getInvitation() {
			logger.info("DEBUG: Junit TESTING: testGetPersoane");
			
			Collection<Invitation> invitations = service.getInvitation();
			assertTrue("Fail to read invitations!", invitations.size() == 0);
		}
		
		@Test
		public void test3_addInvitation() {
			logger.info("DEBUG Junit TESTING: testAddPersoana ...");
			
			Integer invitationToAdd = 5;
			for(int i = 0; i < invitationToAdd; i++) {
				service.AddInvitation(new Invitation(null, "asasda" + (90+i), "asadasdasdasd" + (80 + i)));
			}
			
			Collection<Invitation> invitations = service.getInvitation();
			assertTrue("Fail to add a new invitation!", invitations.size() == invitationToAdd);
		}
		
		@Test
		public void test4_deleteInvitation() {
			logger.info("DEBUG: Junit TESTING: testDeletePersoana ...");
			
			Collection<Invitation> invitations = service.getInvitation();
			for(Invitation i: invitations) {
				service.removeInvitation(i);
			}
			Collection<Invitation> invitationsAfterDelete = service.getInvitation();
			assertTrue("Fail to read invitations!", invitationsAfterDelete .size() == 0);
		}
}