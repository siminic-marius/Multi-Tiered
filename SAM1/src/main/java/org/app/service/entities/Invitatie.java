package org.app.service.entities;

import javax.persistence.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.*;
import static javax.persistence.CascadeType.ALL;

@XmlRootElement(name="invitatie")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Invitatie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2597204678077482480L;
	@Id 
	private Integer invitationId;
	private String titleInvitatie;
	private String messageInvitatie;
	
	@Temporal(TemporalType.DATE)
	private Date dataInvitatie;
	
	@ManyToOne(cascade = ALL)
	private Event event;
	
	@ManyToOne(cascade = ALL)
	private Persoane persons;

	@XmlElement
	public Integer getinvitationId() {
		return invitationId;
	}

	public void setinvitationId(Integer invitationId) {
		this.invitationId = invitationId;
	}
	@XmlElement
	public String getTitleInvitatie() {
		return titleInvitatie;
	}
	
	public void setTitleInvitatie(String titleInvitatie) {
		this.titleInvitatie = titleInvitatie;
	}
	@XmlElement
	public String getMessageInvitatie() {
		return messageInvitatie;
	}

	public void setMessageInvitatie(String messageInvitatie) {
		this.messageInvitatie = messageInvitatie;
	}
	@XmlElement
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

	public Invitatie() {
		super();
	}
	
	
	public Invitatie(Integer invitationId, String titleInvitatie, String messageInvitatie, Date dataInvitatie, Event event, Persoane persons) {
		super();
		this.invitationId = invitationId;
		this.titleInvitatie = titleInvitatie;
		this.messageInvitatie = messageInvitatie;
		this.dataInvitatie = dataInvitatie;
		this.event = event;
		this.persons = persons;
	}

	
	
	public Invitatie(Integer invitationId, Persoane persons) {
		super();
		this.invitationId = invitationId;
		this.persons = persons;
	}

	@Override
	public String toString() {
		return "Invitation [invitationId=" + invitationId + ", titleInvitatie=" + titleInvitatie + ", persons=" + persons + "]";
	}
	
	public static String BASE_URL = Persoane.BASE_URL;
	
	@XmlElement(name="link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL +
				this.getPersons().getPersoanaId() + "/invitatii/" + this.getinvitationId();
		return new AtomLink(restUrl, "get-invitatie");
		
	}
	
	public Invitatie toDTO() {
		return new Invitatie(invitationId, titleInvitatie, messageInvitatie, dataInvitatie, event.toDTO(), persons.toDto());
	}
	
	public static List<Invitatie> toDTOList(List<Invitatie> invitations){
		List<Invitatie> invitationsDTOList = new ArrayList<>();
		for(Invitatie i: invitationsDTOList) {
			invitationsDTOList.add(i.toDTO());
		}
		
		return invitationsDTOList;
	}
}
