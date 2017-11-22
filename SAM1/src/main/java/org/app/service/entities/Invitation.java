package org.app.service.entities;

import javax.persistence.*;
import java.util.*;



@Entity
public class Invitation {
	@Id @GeneratedValue
	private Integer idInvitation;
	private String Message;
	
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

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
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

	public Invitation(Integer idInvitation, String message, Date dataInvitatie, Event event) {
		super();
		this.idInvitation = idInvitation;
		Message = message;
		this.dataInvitatie = dataInvitatie;
		this.event = event;
	}

	
}
