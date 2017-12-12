package org.app.service.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="rating")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Rating implements Serializable{
	@Id @GeneratedValue
	private Integer ratingId;
	
	private String titleRating;
	
	@Temporal(TemporalType.DATE)
	private Date dataRating;
	
	private String messageRating;
	
	private Integer scoreRating;
	
	@ManyToOne
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

	public Date getDataRating() {
		return dataRating;
	}

	public void setDataRating(Date dataRating) {
		this.dataRating = dataRating;
	}
	
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
	@XmlElement
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "Rating [ratingId=" + ratingId + ", titleRating=" + titleRating + ", dataRating=" + dataRating
				+ ", messageRating=" + messageRating + ", scoreRating=" + scoreRating + ", event=" + event + "]";
	}

	public Rating(Integer ratingId, String titleRating, Date dataRating, String messageRating, Integer scoreRating,
			Event event) {
		super();
		this.ratingId = ratingId;
		this.titleRating = titleRating;
		this.dataRating = dataRating;
		this.messageRating = messageRating;
		this.scoreRating = scoreRating;
		this.event = event;
	}

	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rating(Integer ratingId, String titleRating, String messageRating, Integer scoreRating) {
		super();
		this.ratingId = ratingId;
		this.titleRating = titleRating;
		this.messageRating = messageRating;
		this.scoreRating = scoreRating;
	}

	public Rating(Integer ratingId, String titleRating, Date dataRating, Event event) {
		super();
		this.ratingId = ratingId;
		this.titleRating = titleRating;
		this.dataRating = dataRating;
		this.event = event;
	}
	
	public static String BASE_URL = Event.BASE_URL;
	
	@XmlElement(name="link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL
				+ this.getEvent().getEventName() + "/ratings/" + this.getRatingId();
		return new AtomLink(restUrl, "get-rating");
	}
}