package org.app.service.entities;

import static javax.persistence.CascadeType.ALL;
import javax.persistence.*;
import java.util.*;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Persoane {
	@Id @GeneratedValue
	private Integer idPersoana;
	private String Nume;
	private String Localitate;
	private String Sex;
	private String email;
	
	@OneToMany(cascade = ALL, fetch = EAGER, mappedBy = "persons", orphanRemoval = false)
	private List<Invitation> invitatii = new ArrayList<>();
}
