package org.app.service.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import org.app.service.entities.Workshops;

@Remote
public interface WorkshopsService {
	
	Workshops AddWorkshops(Workshops workshopToAdd);
	
	String removeWorkshops(Workshops workshopsToDelete);
	
	Workshops getWorkshopsByWorkshopsID (Integer workshopsId);
	
	Collection<Workshops> getWorkshops();
	
	Workshops getWorkshopsByName(String workshopsName);
	
	String sayRest();
}
