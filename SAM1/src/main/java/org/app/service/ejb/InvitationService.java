package org.app.service.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import org.app.service.entities.Invitation;

@Remote
public interface InvitationService {
	Invitation AddInvitation(Invitation invitationToAdd);
	
	String removeInvitation(Invitation invitationToDelete);
	
	Invitation getInvitationByidInvitation (Integer idInvitation);
	
	Collection<Invitation> getInvitation();
	
	Invitation getInvitationByTitle(String invitationTitle);
	
	String sayRest();
}
