package org.app.service.entities;

import static javax.persistence.CascadeType.ALL;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.*;
import static javax.persistence.FetchType.EAGER;

@XmlRootElement(name="person")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Persoane implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -670962705138259029L;

	@Id
	private Integer persoanaId;
	
	private String name;
	
	private String localitate;
	
	private String sex;
	
	private String email;
	
	@OneToMany(mappedBy = "persons", cascade = ALL, fetch = EAGER, orphanRemoval = false)
	private List<Invitatie> invitatii = new ArrayList<>();
	
	@XmlElement
	public Integer getPersoanaId() {
		return persoanaId;
	}

	public void setPersoanaId(Integer persoanaId) {
		this.persoanaId = persoanaId;
	}
	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@XmlElement
	public String getLocalitate() {
		return localitate;
	}

	public void setLocalitate(String localitate) {
		this.localitate = localitate;
	}
	@XmlElement
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@XmlElement
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@XmlElementWrapper(name = "invitatii") @XmlElement(name="invitatie")
	public List<Invitatie> getInvitatii() {
		return invitatii;
	}

	public void setInvitatii(List<Invitatie> invitatii) {
		this.invitatii = invitatii;
	}

	public Persoane() {
		super();
	}
	
	public Persoane(Integer persoanaId, String name) {
		super();
		this.persoanaId = persoanaId;
		this.name = name;
	}
	
	public Persoane(Integer persoanaId, String name, String localitate, String sex, String email,
			List<Invitatie> invitatii) {
		super();
		this.persoanaId = persoanaId;
		this.name = name;
		this.localitate = localitate;
		this.sex = sex;
		this.email = email;
		this.invitatii = invitatii;
	}

	public Persoane(Integer persoanaId, String name, String localitate, String sex, String email) {
		super();
		this.persoanaId = persoanaId;
		this.name = name;
		this.localitate = localitate;
		this.sex = sex;
		this.email = email;
	}
	

	public static String BASE_URL = "http://localhost:8080/SCRUM/data/persons/";
	
	@XmlElement(name= "link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getPersoanaId();
		return new AtomLink(restUrl, "get-person");
	}
	
	public void setLink(AtomLink link){}
	
	public Persoane toDto() {
		return new Persoane (persoanaId, name, localitate, sex, email);
	}

}
