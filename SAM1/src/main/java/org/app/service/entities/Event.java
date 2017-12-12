package org.app.service.entities;
import java.io.Serializable;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@XmlRootElement(name="project")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Event implements Serializable {
	
	@Id 
	@GeneratedValue
	private Integer eventID;
	
	private String eventName;

	private String Location;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	private String description;
	
	private Integer nrPlaces;
	
	@OneToMany(mappedBy = "event", cascade = ALL, fetch = EAGER, orphanRemoval = false)
	private List<Invitation> invitatie = new ArrayList<>();
	
	@OneToMany(mappedBy = "event", cascade = ALL, fetch = LAZY, orphanRemoval = false)
	private List<Rating> rating = new ArrayList<>();

	@XmlElement
	public Integer getEventID() {
		return eventID;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}
	
	@XmlElement
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNrPlaces() {
		return nrPlaces;
	}

	public void setNrPlaces(Integer nrPlaces) {
		this.nrPlaces = nrPlaces;
	}
	
	@XmlElementWrapper(name="invitations") @XmlElement(name="invitation")
	public List<Invitation> getInvitatie() {
		return invitatie;
	}

	public void setInvitatie(List<Invitation> invitatie) {
		this.invitatie = invitatie;
	}
	@XmlElementWrapper(name="ratings") @XmlElement(name="rating")
	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public Event(Integer eventID, String eventName, String location, Date startDate, String description,
			Integer nrPlaces, List<Invitation> invitatie) {
		super();
		this.eventID = eventID;
		this.eventName = eventName;
		Location = location;
//		this.startDate = startDate;
		this.description = description;
		this.nrPlaces = nrPlaces;
		this.invitatie = invitatie;
	}

	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Event(Integer eventID, String eventName, String Location, String description, Integer nrPlaces) {
		super();
		this.eventID = eventID;
		this.eventName = eventName;
		this.Location = Location;
		this.description = description;
		this.nrPlaces = nrPlaces;
	
	}
	
	

	public Event(Integer eventID, String eventName, Date startDate) {
		super();
		this.eventID = eventID;
		this.eventName = eventName;
		this.startDate = startDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Location == null) ? 0 : Location.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + ((invitatie == null) ? 0 : invitatie.hashCode());
		result = prime * result + ((nrPlaces == null) ? 0 : nrPlaces.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (Location == null) {
			if (other.Location != null)
				return false;
		} else if (!Location.equals(other.Location))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (eventID == null) {
			if (other.eventID != null)
				return false;
		} else if (!eventID.equals(other.eventID))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (invitatie == null) {
			if (other.invitatie != null)
				return false;
		} else if (!invitatie.equals(other.invitatie))
			return false;
		if (nrPlaces == null) {
			if (other.nrPlaces != null)
				return false;
		} else if (!nrPlaces.equals(other.nrPlaces))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [eventID=" + eventID + ", eventName=" + eventName + ", Location=" + Location + ", startDate="
				 + ", description=" + description + ", nrPlaces=" + nrPlaces + ", invitatie=" + invitatie
				+ "]";
	}
	
	public static String BASE_URL = "http://localhost:8080/SCRUM/DATA/events/";
	@XmlElement(name = "link")
    public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getEventID();
        return new AtomLink(restUrl, "get-Event");
    }	
}
