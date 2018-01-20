package org.scrum.client.controllers;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.scrum.client.dto.Invitatie;
import org.scrum.client.services.InvitatieRestServiceConsumer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@Named	@SessionScoped
public class InvitatieViewController implements Serializable {

	private static final long serialVersionUID = -6736318872189537488L;
	
private static Logger logger = Logger.getLogger(PersoaneViewController.class.getName());
	
	private List<Invitatie> invitatie = new ArrayList<Invitatie>();
	
	@Inject
	private InvitatieRestServiceConsumer restService;
	
	public List<Invitatie> getInvitatie(){
		return invitatie;
	}
	
	@PostConstruct
	private void init() {
		this.invitatie = restService.getInvitatie();
		if(this.getInvitatie().size()>0)
			this.selectedInvitatie = this.getInvitatie().get(0);
	}
	
	private Invitatie selectedInvitatie;
	
	public Invitatie getSelectedInvitatie() {
		return selectedInvitatie;
	}
	
	public void setSelectedInvitatie(Invitatie selectedInvitatie) {
		this.selectedInvitatie = selectedInvitatie;
		System.out.println(">>> >>>> selectedInvitatie: " + this.selectedInvitatie);
	}
	
	// Filtering Support
	private List<Invitatie> filteredInvitatie = new ArrayList<Invitatie>();

	public List<Invitatie> getFilteredInvitatie() {
			return filteredInvitatie;
	}

	public void setFilteredInvitatii(List<Invitatie> filteredInvitatie) {
		logger.info("filteredInvitatie ::: ");
		if (filteredInvitatie != null)
			filteredInvitatie.stream().forEach(p -> System.out.println(p.getTitleInvitatie()));
		else
			logger.info(">>> NULL ....");
			
		this.filteredInvitatie = filteredInvitatie;
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
			System.out.println(">>> >>>> onRowSelect:: selectedPersoane is: " + this.filteredInvitatie);
		}
		
		public void saveSelectedPersoane(ActionEvent actionEvent) {
	        addMessage("Saved selected project => " + this.selectedInvitatie.getTitleInvitatie());
	        this.restService.addInvitatie(this.selectedInvitatie);
	    }
		
		public void addNewPersoana(ActionEvent actionEvent) {
			this.selectedInvitatie = this.restService.createInvitatie();
			this.invitatie = this.restService.getInvitatie();
			addMessage("NEW invitatie => " + this.selectedInvitatie.getinvitationId());
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
