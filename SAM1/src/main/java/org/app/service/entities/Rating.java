package org.app.service.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@XmlRootElement(name="rating")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Rating implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -163806720326544373L;

	@Id
	private Integer ratingId;
	
	private String titleRating;
	
	@Column
	private LocalDateTime dataRating;
	
	private String messageRating;
	
	private Integer scoreRating;
	
	@ManyToMany(cascade = { CascadeType.ALL})
	@JoinTable(
			name = "Rating_Persoane",
			joinColumns = {@JoinColumn(name="ratingId")},
					inverseJoinColumns = {@JoinColumn(name = "persoanaId")}
			)
	private List<Persoane> persoane = new ArrayList<>();
	
	@ManyToOne(cascade = ALL, fetch = LAZY)
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

	public List<Persoane> getPersoane() {
		return persoane;
	}

	public void setPersoane(List<Persoane> persoane) {
		this.persoane = persoane;
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
	
	public Rating(Integer ratingId, String titleRating, LocalDateTime dataRating, String messageRating,
			Integer scoreRating, List<Persoane> persoane, Event event) {
		super();
		this.ratingId = ratingId;
		this.titleRating = titleRating;
		this.dataRating = dataRating;
		this.messageRating = messageRating;
		this.scoreRating = scoreRating;
		this.persoane = persoane;
		this.event = event;
	}

//	public static String BASE_URL = Event.BASE_URL;
//	
//	@XmlElement(name="link")
//	public AtomLink getLink() throws Exception {
//		String restUrl = BASE_URL
//				+ this.getEvent().getEventID()
//				+ "/ratings/"
//				+ this.getRatingId();
//		return new AtomLink(restUrl, "get-rating");
//	}
	
	

	@Override
	public String toString() {
		return "Rating [ratingId=" + ratingId + ", titleRating=" + titleRating + ", dataRating=" + dataRating
				+ ", messageRating=" + messageRating + ", scoreRating=" + scoreRating + "]";
	}
	
	public static String BASE_URL = "http://localhost:8080/SCRUM/DATA/ratings/";
	@XmlElement(name="link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getRatingId();
		return new AtomLink(restUrl, "get-rating");
	}
}