package org.scrum.client.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.*;


@XmlRootElement(name="person")
@XmlAccessorType(XmlAccessType.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Persoane implements Serializable, Comparable<Persoane>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -670962705138259029L;

	private Integer persoanaId;
	
	private String name;
	
	private String localitate;
	
	private String sex;
	
	private String email;
	
	
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
	@XmlElementWrapper(name = "invitatii")
	@XmlElement(name="invitatie")
	public List<Invitatie> getInvitatii() {
		return invitatii;
	}

	public void setInvitatii(List<Invitatie> invitatii) {
		this.invitatii = invitatii;
	}
	
	public Persoane() {
		super();
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
	

	public static String BASE_URL = "http://localhost:8080/SAM1/data/persons/";
	
	@XmlElement(name= "link")
	public AtomLink getLink() throws Exception {
		String restUrl = BASE_URL + this.getPersoanaId();
		return new AtomLink(restUrl, "get-person");
	}
	
	public void setLink(AtomLink link){}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((persoanaId == null) ? 0 : persoanaId.hashCode());
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
		Persoane other = (Persoane) obj;
		if (persoanaId == null) {
			if (other.persoanaId != null)
				return false;
		} else if (!persoanaId.equals(other.persoanaId))
			return false;
		return true;
	}

	@Override
	public int compareTo(Persoane obj) {
		// TODO Auto-generated method stub
		return this.persoanaId.compareTo(((Persoane)obj).getPersoanaId());
	}
	
	
}
