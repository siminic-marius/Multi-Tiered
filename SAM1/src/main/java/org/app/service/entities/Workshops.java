package org.app.service.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Workshops extends Event implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5166575430470962345L;
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
	
	public Workshops(Integer eventID, String eventName, String eventLocation, Date startDate, String description,
			Integer nrPlaces, Integer workshopId, String titleWorkshop, String materialsWorkshop) {
		super(eventID, eventName, eventLocation, startDate, description, nrPlaces);
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
