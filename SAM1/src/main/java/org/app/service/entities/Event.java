package org.app.service.entities;

import static javax.persistence.CascadeType.ALL;
import javax.persistence.*;
import java.util.*;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Event {
	@Id @GeneratedValue
	private Integer eventID;
	
	private String eventName;
	
	///private enum eventType {Presentations, Workshops, Demos};
	////private String Location;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	///private String description;
	///private Integer nrPlaces;
	
	@OneToMany(mappedBy = "event", cascade = ALL, fetch = EAGER, orphanRemoval = false)
	private List<Invitation> invitatie = new ArrayList<>();

	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<Invitation> getInvitatie() {
		return invitatie;
	}

	public void setInvitatie(List<Invitation> invitatie) {
		this.invitatie = invitatie;
	}

	public Event() {
		super();
	}

	public Event(Integer eventID, String eventName, Date startDate) {
		super();
		this.eventID = eventID;
		this.eventName = eventName;
		this.startDate = startDate;
	}
	

}
