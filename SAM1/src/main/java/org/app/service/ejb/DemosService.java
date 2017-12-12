package org.app.service.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import org.app.service.entities.Demos;

@Remote
public interface DemosService {
	Demos AddDemos(Demos demosToAdd);
	
	String removeDemos(Demos demosToDelete);
	
	Demos getDemosByDemosID (Integer demosId);
	
	Collection<Demos> getDemos();
	
	Demos getDemosByTitle(String demosTitle);
	
	String sayRest();
}
