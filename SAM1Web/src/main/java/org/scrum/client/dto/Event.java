package org.scrum.client.dto;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.*;

@XmlRootElement(name="event")
@XmlAccessorType(XmlAccessType.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6485269313210188149L;

	
//	@GeneratedValue
	private Integer eventID;

	private String eventName;
	
	private String eventLocation;
	
	private Date startDate;
	
	private String description;
	
	private Integer nrPlaces;
	
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
	@XmlElement
	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	@XmlElement
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@XmlElement
	public Integer getNrPlaces() {
		return nrPlaces;
	}

	public void setNrPlaces(Integer nrPlaces) {
		this.nrPlaces = nrPlaces;
	}

	@XmlElementWrapper(name = "rating")
	@XmlElement(name="rating")
	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}


	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Event(Integer eventID, String eventName, String eventLocation, Date startDate, String description,
			Integer nrPlaces) {
		super();
		this.eventID = eventID;
		this.eventName = eventName;
		this.eventLocation = eventLocation;
		this.startDate = startDate;
		this.description = description;
		this.nrPlaces = nrPlaces;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
		result = prime * result + ((eventLocation == null) ? 0 : eventLocation.hashCode());
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + ((nrPlaces == null) ? 0 : nrPlaces.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
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
		if (eventLocation == null) {
			if (other.eventLocation != null)
				return false;
		} else if (!eventLocation.equals(other.eventLocation))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (nrPlaces == null) {
			if (other.nrPlaces != null)
				return false;
		} else if (!nrPlaces.equals(other.nrPlaces))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
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
		return "Event [eventID=" + eventID + ", eventName=" + eventName + ", eventLocation=" + eventLocation
				+ ", startDate=" + startDate + ", description=" + description + ", nrPlaces=" + nrPlaces
				+  "]";
	}

	public static String BASE_URL = "http://localhost:8080/SCRUM/DATA/events/";
	@XmlElement(name="link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getEventID();
		return new AtomLink(restUrl, "get-event");
	}
	
	public void setLink(AtomLink link) {}
	
	public Event toDTO () {
		Event eventToDTO = new Event (eventID, eventName, eventLocation, startDate, description, nrPlaces);
		return eventToDTO;
	}
	
	public static Collection<Event> toDTOCOllection (Collection<Event> event){
		List<Event> eventToDTO = new ArrayList<>();
		for(Event e: event) {
			eventToDTO.add(e.toDTO());
		}
		return eventToDTO;
	}
 }
