package org.app.service.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Presentations extends Event implements Serializable{
	
	
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
	public Presentations(Integer eventID, String eventName, String location, Date startDate, String description,
			Integer nrPlaces, List<Invitation> invitatie) {
		super(eventID, eventName, location, startDate, description, nrPlaces, invitatie);
		// TODO Auto-generated constructor stub
	}
	public Presentations(Integer eventID, String eventName, String Location, String description, Integer nrPlaces) {
		super(eventID, eventName, Location, description, nrPlaces);
		// TODO Auto-generated constructor stub
	}
	public Presentations(Integer eventID, String eventName, String location, Date startDate, String description,
			Integer nrPlaces, List<Invitation> invitatie, Integer presentationId, String speakerPresentation,
			Integer nrSeats) {
		super(eventID, eventName, location, startDate, description, nrPlaces, invitatie);
		this.presentationId = presentationId;
		this.speakerPresentation = speakerPresentation;
		this.nrSeats = nrSeats;
	}
	
	public Presentations(Integer presentationId, String speakerPresentation, Integer nrSeats) {
		super();
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
