package org.app.service.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;



@Entity
public class Invitation implements Serializable{
	@Id @GeneratedValue
	private Integer idInvitation;
	private String title;
	private String message;
	
	@Temporal(TemporalType.DATE)
	private Date dataInvitatie;
	
	@ManyToOne
	private Event event;
	
	@ManyToOne
	private Persoane persons;

	public Integer getIdInvitation() {
		return idInvitation;
	}

	public void setIdInvitation(Integer idInvitation) {
		this.idInvitation = idInvitation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDataInvitatie() {
		return dataInvitatie;
	}

	public void setDataInvitatie(Date dataInvitatie) {
		this.dataInvitatie = dataInvitatie;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Persoane getPersons() {
		return persons;
	}

	public void setPersons(Persoane persons) {
		this.persons = persons;
	}

	public Invitation() {
		super();
	}

	public Invitation(Integer idInvitation, String title, String message) {
		super();
		this.idInvitation = idInvitation;
		this.title = title;
		this.message = message;
	}
	
	

	public Invitation(Integer idInvitation, String title, Persoane persons) {
		super();
		this.idInvitation = idInvitation;
		this.title = title;
		this.persons = persons;
	}
	
	

	public Invitation(Integer idInvitation, String title, Date dataInvitatie, Event event) {
		super();
		this.idInvitation = idInvitation;
		this.title = title;
		this.dataInvitatie = dataInvitatie;
		this.event = event;
	}

	@Override
	public String toString() {
		return "Invitation [idInvitation=" + idInvitation + ", title=" + title + ", message=" + message
				+ ", dataInvitatie=" + dataInvitatie + ", event=" + event + ", persons=" + persons + "]";
	}

	
}
