package org.app.service.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Workshops extends Event implements Serializable {
	
	private Integer workshopId;
	private String titleWorkshop;
	private String materialsWorkshop;
	public Integer getWorkshopId() {
		return workshopId;
	}
	public void setWorkshopId(Integer workshopId) {
		this.workshopId = workshopId;
	}
	public String getTitleWorkshop() {
		return titleWorkshop;
	}
	public void setTitleWorkshop(String titleWorkshop) {
		this.titleWorkshop = titleWorkshop;
	}
	public String getMaterialsWorkshop() {
		return materialsWorkshop;
	}
	public void setMaterialsWorkshop(String materialsWorkshop) {
		this.materialsWorkshop = materialsWorkshop;
	}
	public Workshops() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Workshops(Integer eventID, String eventName, String location, Date startDate, String description,
			Integer nrPlaces, List<Invitation> invitatie) {
		super(eventID, eventName, location, startDate, description, nrPlaces, invitatie);
		// TODO Auto-generated constructor stub
	}
	public Workshops(Integer eventID, String eventName, String Location, String description, Integer nrPlaces) {
		super(eventID, eventName, Location, description, nrPlaces);
		// TODO Auto-generated constructor stub
	}
	public Workshops(Integer eventID, String eventName, String location, Date startDate, String description,
			Integer nrPlaces, List<Invitation> invitatie, Integer workshopId, String titleWorkshop,
			String materialsWorkshop) {
		super(eventID, eventName, location, startDate, description, nrPlaces, invitatie);
		this.workshopId = workshopId;
		this.titleWorkshop = titleWorkshop;
		this.materialsWorkshop = materialsWorkshop;
	}
	public Workshops(Integer workshopId, String titleWorkshop, String materialsWorkshop) {
		super();
		this.workshopId = workshopId;
		this.titleWorkshop = titleWorkshop;
		this.materialsWorkshop = materialsWorkshop;
	}
	public Workshops(Integer eventID, String eventName, String Location, String description, Integer nrPlaces,
			Integer workshopId, String titleWorkshop, String materialsWorkshop) {
		super(eventID, eventName, Location, description, nrPlaces);
		this.workshopId = workshopId;
		this.titleWorkshop = titleWorkshop;
		this.materialsWorkshop = materialsWorkshop;
	}
	@Override
	public String toString() {
		return "Workshops [workshopId=" + workshopId + ", titleWorkshop=" + titleWorkshop + ", materialsWorkshop="
				+ materialsWorkshop + "]";
	}
	
	
	
}
