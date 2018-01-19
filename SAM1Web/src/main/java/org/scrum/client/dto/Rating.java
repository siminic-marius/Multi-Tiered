package org.scrum.client.dto;

import java.io.Serializable;
import java.time.LocalDateTime;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@XmlRootElement(name="rating")
@XmlAccessorType(XmlAccessType.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -163806720326544373L;

	
	private Integer ratingId;
	
	private String titleRating;
	
	private LocalDateTime dataRating;
	
	private String messageRating;
	
	private Integer scoreRating;
	
	private Event event;
	
	@XmlElement
	public Integer getRatingId() {
		return ratingId;
	}

	public void setRatingId(Integer ratingId) {
		this.ratingId = ratingId;
	}
	@XmlElement
	public String getTitleRating() {
		return titleRating;
	}

	public void setTitleRating(String titleRating) {
		this.titleRating = titleRating;
	}
	@XmlElement
	public LocalDateTime getDataRating() {
		return dataRating;
	}

	public void setDataRating(LocalDateTime dataRating) {
		this.dataRating = dataRating;
	}
	@XmlElement
	public String getMessageRating() {
		return messageRating;
	}

	public void setMessageRating(String messageRating) {
		this.messageRating = messageRating;
	}
	@XmlElement
	public Integer getScoreRating() {
		return scoreRating;
	}

	public void setScoreRating(Integer scoreRating) {
		this.scoreRating = scoreRating;
	}
//	@XmlElement
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Rating(Integer ratingId, String titleRating, LocalDateTime dataRating, String messageRating, Integer scoreRating, Event event
			) {
		super();
		this.ratingId = ratingId;
		this.titleRating = titleRating;
		this.dataRating = dataRating;
		this.messageRating = messageRating;
		this.scoreRating = scoreRating;
		this.event = event;
	}
	
	public static String BASE_URL = Event.BASE_URL;
	
	@XmlElement(name="link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL
				+ ((this.getEvent() != null) ? this.getEvent().getEventID() : "") + "/ratings/" + this.getRatingId();
		return new AtomLink(restUrl, "get-rating");
	}
	
	public void setLink(AtomLink link){}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ratingId == null) ? 0 : ratingId.hashCode());
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
		Rating other = (Rating) obj;
		if (ratingId == null) {
			if (other.ratingId != null)
				return false;
		} else if (!ratingId.equals(other.ratingId))
			return false;
		return true;
	}

}