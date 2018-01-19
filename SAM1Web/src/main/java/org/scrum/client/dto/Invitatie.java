package org.scrum.client.dto;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.*;

@XmlRootElement(name="invitatie")
@XmlAccessorType(XmlAccessType.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invitatie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2597204678077482480L;
	
	private Integer invitationId;
	
	private String titleInvitatie;
	
	private String messageInvitatie;
	
	private Date dataInvitatie;
	
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
	
	
	public Invitatie(Integer invitationId, String titleInvitatie, String messageInvitatie, Date dataInvitatie,  Persoane persons) {
		super();
		this.invitationId = invitationId;
		this.titleInvitatie = titleInvitatie;
		this.messageInvitatie = messageInvitatie;
		this.dataInvitatie = dataInvitatie;
//		this.event = event;Event event,
		this.persons = persons;
	}
	
	public Invitatie(Integer invitationId, Persoane persons) {
		super();
		this.invitationId = invitationId;
		this.persons = persons;
	}
	
	public static String BASE_URL = Persoane.BASE_URL;
	
	@XmlElement(name="link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL +
				((this.getPersons() != null) ? this.getPersons().getPersoanaId() : "") + "/invitatii/" + this.getinvitationId();
		return new AtomLink(restUrl, "get-invitatie");
	}
	
	public void setLink(AtomLink link){}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((invitationId == null) ? 0 : invitationId.hashCode());
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
		if (invitationId == null) {
			if (other.invitationId != null)
				return false;
		} else if (!invitationId.equals(other.invitationId))
			return false;
		return true;
	}

}
