package org.app.service.ejb;

import javax.ejb.Remote;
import java.util.*;
import org.app.service.entities.Persoane;;

@Remote
public interface PersoaneService {
	
	Persoane AddPersoana(Persoane personToAdd);
	
	String removePersoane(Persoane persoanaToDelete);
	
	Persoane getPersoanaByPersoanaID (Integer persoanaId);
	
	Collection<Persoane> getPersoane();
	
	Persoane getPersoaneByName(String persoanaName);
	
	String sayRest();
}
