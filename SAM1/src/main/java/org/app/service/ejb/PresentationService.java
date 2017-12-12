package org.app.service.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import org.app.service.entities.Presentations;

@Remote
public interface PresentationService {
	Presentations AddPresentations(Presentations presentationsToAdd);
	
	String removePresentations(Presentations presentationsToDelete);
	
	Presentations getPresentationsByPresentationsId (Integer presentationsId);
	
	Collection<Presentations> getPresentations();
	
	Presentations getPresentationsByName(String presentationsName);
	
	String sayRest();
}
