package org.scrum.client.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.scrum.client.dto.Persoane;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.scrum.client.services.PersoaneRESTServiceConsumer;

@Named	@SessionScoped
public class PersoaneViewController implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PersoaneViewController.class.getName());
	
	private List<Persoane> persoane = new ArrayList<Persoane>();
	
	@Inject
	private PersoaneRESTServiceConsumer restService;
	
	public List<Persoane> getPersoane(){
		return persoane;
	}
	
	@PostConstruct
	private void init() {
		this.persoane = restService.getPersoane();
		if(this.getPersoane().size()>0)
			this.selectedPersoane = this.getPersoane().get(0);
	}
	
	private Persoane selectedPersoane;
	
	public Persoane getSelectedPersoane () {
		return selectedPersoane;
	}
	
	public void setSelectedPersoane(Persoane selectedPersoane) {
		this.selectedPersoane = selectedPersoane;
		System.out.println(">>> >>>> selectedPersoane: " + this.selectedPersoane);
	}
	
	// Filtering Support
	private List<Persoane> filteredPersoane = new ArrayList<Persoane>();

	public List<Persoane> getFilteredPersoane() {
			return filteredPersoane;
	}

	public void setFilteredPersoane(List<Persoane> filteredPersoane) {
		logger.info("filteredPersoane ::: ");
		if (filteredPersoane != null)
			filteredPersoane.stream().forEach(p -> System.out.println(p.getName()));
		else
			logger.info(">>> NULL ....");
			
		this.filteredPersoane = filteredPersoane;
	}
		
	public boolean filterByDate(Object value, Object filter, Locale locale) {

		if( filter == null ) {
	        return true;
	    }

	    if( value == null ) {
	    	return false;
	    }

	    Date dateValue = (Date) value;
	    Date dateFilter = (Date) filter;
	       
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    return dateFormat.format(dateFilter).equals(dateFormat.format(dateValue));
	}	
		
	// Data Grid support
		public void onRowSelect(SelectEvent event) {
			System.out.println(">>> >>>> onRowSelect:: selectedPersoane is: " + this.selectedPersoane);
		}
		
		public void saveSelectedPersoane(ActionEvent actionEvent) {
	        addMessage("Saved selected project => " + this.selectedPersoane.getName());
	        this.restService.addPersoane(this.selectedPersoane);
	    }
		
		public void addNewPatient(ActionEvent actionEvent) {
			this.selectedPersoane = this.restService.createPersoane();
			this.persoane = this.restService.getPersoane();
			addMessage("NEW persoane => " + this.selectedPersoane.getPersoanaId());
		}

//		public void deletePatient(ActionEvent actionEvent) {
//			addMessage("Deleted project => " + this.selectedPersoane.getPersoanaId());
//			if (this.selectedPatient != null)
//				this.restService.deletePatient(this.selectedPatient);
//			this.patients = this.restService.getPatients();
//			if (!this.patients.isEmpty())
//				this.selectedPatient = this.patients.get(0);
//		}
		
		public void addMessage(String summary) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
	    }
		
		public void onCellEdit(CellEditEvent event) {
			Object oldValue = event.getOldValue();
			Object newValue = event.getNewValue();
			addMessage("Cell Changed: " + "Old: " + oldValue + ", New:" + newValue);
		}
}
