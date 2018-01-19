package org.app.service.rest.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import org.app.patterns.EntityRepository;
import org.app.service.ejb.InvitationService;
import org.app.service.entities.Invitatie;
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
public class TestInvitatiiDataServiceRestArq {
	private static Logger logger = Logger.getLogger(TestInvitatiiDataServiceRestArq.class.getName());
	
	private static String serviceURL = "http://localhost:8080/msd-s4-test/data/invitations";
	
	@Deployment
	public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "msd-s4-test.war")
                .addPackage(Invitatie.class.getPackage())
                .addPackage(InvitationService.class.getPackage())
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
	public void test2_DeleteInvitatie() {
		String resourceURL = serviceURL + "/";
		logger.info("DEBUG: Junit TESTING: test2_DeleteInvitatii ...");
		Client client = ClientBuilder.newClient();
		Collection<Invitatie> invitatie = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Invitatie>>(){});		
		
		for (Invitatie i: invitatie) {
			client.target(resourceURL + i.getinvitationId()).request().delete();
		}
		
		Collection<Invitatie> invitatieAfterDelete = client.target(serviceURL)
				.request().get()
				.readEntity(new GenericType<Collection<Invitatie>>(){});	
		assertTrue("Fail to read Invitatii!", invitatieAfterDelete.size() == 0);
	}
}
