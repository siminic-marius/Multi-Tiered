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

	public Persoane getPersons() {
		return persons;
	}

	public void setPersons(Persoane persons) {
		this.persons = persons;
	}

	public Invitatie() {
		super();
	}
	
	public Invitatie(Integer invitationId, String titleInvitatie, String messageInvitatie, Date dataInvitatie, Persoane persons) {
		super();
		this.invitationId = invitationId;
		this.titleInvitatie = titleInvitatie;
		this.messageInvitatie = messageInvitatie;
		this.dataInvitatie = dataInvitatie;
		this.persons = persons;
	}
	// cu asta in RESTTTTTTTT
	public Invitatie(Integer invitationId, Persoane persons) {
		super();
		this.invitationId = invitationId;
		this.persons = persons;
	}
	
	public Invitatie toDTO() {
		return new Invitatie(invitationId, titleInvitatie, messageInvitatie, dataInvitatie, persons.toDto());
	}
	
	public static List<Invitatie> toDTOList(List<Invitatie> invitations){
		List<Invitatie> invitationsDTOList = new ArrayList<>();
		for(Invitatie i: invitationsDTOList) {
			invitationsDTOList.add(i.toDTO());
		}
		
		return invitationsDTOList;
	}
	
	public static String BASE_URL = Persoane.BASE_URL;
	
	@XmlElement(name="link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + ((this.getPersons() != null) ? this.getPersons().getPersoanaId() : "")
				+ "/invitatii/" + this.getinvitationId();
		return new AtomLink(restUrl, "get-invitatie");
		
	}
	
	public void setLink(AtomLink link){}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataInvitatie == null) ? 0 : dataInvitatie.hashCode());
		result = prime * result + ((invitationId == null) ? 0 : invitationId.hashCode());
		result = prime * result + ((messageInvitatie == null) ? 0 : messageInvitatie.hashCode());
		result = prime * result + ((persons == null) ? 0 : persons.hashCode());
		result = prime * result + ((titleInvitatie == null) ? 0 : titleInvitatie.hashCode());
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
		Invitatie other = (Invitatie) obj;
		if (dataInvitatie == null) {
			if (other.dataInvitatie != null)
				return false;
		} else if (!dataInvitatie.equals(other.dataInvitatie))
			return false;
		if (invitationId == null) {
			if (other.invitationId != null)
				return false;
		} else if (!invitationId.equals(other.invitationId))
			return false;
		if (messageInvitatie == null) {
			if (other.messageInvitatie != null)
				return false;
		} else if (!messageInvitatie.equals(other.messageInvitatie))
			return false;
		if (persons == null) {
			if (other.persons != null)
				return false;
		} else if (!persons.equals(other.persons))
			return false;
		if (titleInvitatie == null) {
			if (other.titleInvitatie != null)
				return false;
		} else if (!titleInvitatie.equals(other.titleInvitatie))
			return false;
		return true;
	}
	
	
}
