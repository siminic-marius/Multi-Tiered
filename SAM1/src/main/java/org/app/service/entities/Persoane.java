package org.app.service.entities;

import static javax.persistence.CascadeType.ALL;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.*;
import static javax.persistence.FetchType.EAGER;


@Entity
public class Persoane implements Serializable{

	@Id 
	//@GeneratedValue @NotNull
	private Integer persoanaId;
	
	private String name;
	
	private String localitate;
	
	private String sex;
	
	private String email;
	
	@OneToMany(mappedBy = "persons", cascade = ALL, fetch = EAGER, orphanRemoval = false)
	private List<Invitation> invitatii = new ArrayList<>();

	public Integer getPersoanaId() {
		return persoanaId;
	}

	public void setPersoanaId(Integer persoanaId) {
		this.persoanaId = persoanaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalitate() {
		return localitate;
	}

	public void setLocalitate(String localitate) {
		this.localitate = localitate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Invitation> getInvitatii() {
		return invitatii;
	}

	public void setInvitatii(List<Invitation> invitatii) {
		this.invitatii = invitatii;
	}

	public Persoane() {
		super();
	}

	public Persoane(Integer persoanaId, String name, String localitate, String sex, String email,
			List<Invitation> invitatii) {
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
	
	public Persoane(Integer persoanaId, String name) {
		super();
		this.persoanaId = persoanaId;
		this.name = name;
	}


	@Override
	public String toString() {
		return "Persoane [persoanaId=" + persoanaId + ", name=" + name + ", localitate=" + localitate + ", sex=" + sex
				+ ", email=" + email + ", invitatii=" + invitatii + "]";
	}


	
}
