package org.app.service.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Presentations extends Event implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5712138590642599420L;
	private Integer presentationId;
	private String speakerPresentation;
	private Integer nrSeats;
	
	public Integer getPresentationId() {
		return presentationId;
	}
	public void setPresentationId(Integer presentationId) {
		this.presentationId = presentationId;
	}
	public String getSpeakerPresentation() {
		return speakerPresentation;
	}
	public void setSpeakerPresentation(String speakerPresentation) {
		this.speakerPresentation = speakerPresentation;
	}
	public Integer getNrSeats() {
		return nrSeats;
	}
	public void setNrSeats(Integer nrSeats) {
		this.nrSeats = nrSeats;
	}
	public Presentations() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Presentations(Integer eventID, String eventName, String eventLocation, Date startDate, String description,
			Integer nrPlaces, Integer presentationId, String speakerPresentation, Integer nrSeats) {
		super(eventID, eventName, eventLocation, startDate, description, nrPlaces);
		this.presentationId = presentationId;
		this.speakerPresentation = speakerPresentation;
		this.nrSeats = nrSeats;
	}
	
	@Override
	public String toString() {
		return "Presentations [presentationId=" + presentationId + ", speakerPresentation=" + speakerPresentation
				+ ", nrSeats=" + nrSeats + "]";
	}
	
}
