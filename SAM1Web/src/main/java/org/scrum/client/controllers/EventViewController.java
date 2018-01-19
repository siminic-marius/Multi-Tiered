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

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.scrum.client.dto.Event;
import org.scrum.client.services.EventRestServiceConsumer;


@Named	@SessionScoped
public class EventViewController implements Serializable{

	private static final long serialVersionUID = -7445909339372572933L;

	private static Logger logger = Logger.getLogger(EventViewController.class.getName());
	
	private List<Event> events = new ArrayList<Event>();
	
	@Inject
	private EventRestServiceConsumer restService;
	
	public List<Event> getEvent(){
		return events;
	}
	
	@PostConstruct
	private void init() {
		this.events = restService.getEvent();
		if(this.getEvent().size()>0)
			this.selectedEvent = this.getEvent().get(0);
	}
	
	private Event selectedEvent; 
	
	public Event getSelectedEvent () {
		return selectedEvent;
	}
	
	public void setSelectedPersoane(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
		System.out.println(">>> >>>> selectedEvent: " + this.selectedEvent);
	}
	
	// Filtering Support
	private List<Event> filteredEvents = new ArrayList<Event>();

	public List<Event> getFilteredEvent() {
			return filteredEvents;
	}

	public void setfilteredEvents(List<Event> filteredEvents) {
		logger.info("filteredPersoane ::: ");
		if (filteredEvents != null)
			filteredEvents.stream().forEach(p -> System.out.println(p.getEventName()));
		else
			logger.info(">>> NULL ....");
			
		this.filteredEvents = filteredEvents;
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
			System.out.println(">>> >>>> onRowSelect:: selectedPersoane is: " + this.selectedEvent);
		}
		
		public void saveSelectedPersoane(ActionEvent actionEvent) {
	        addMessage("Saved selected project => " + this.selectedEvent.getEventName());
	        this.restService.addPersoane(this.selectedEvent);
	    }
		
		public void addNewPatient(ActionEvent actionEvent) {
			this.selectedEvent = this.restService.createEvent();
			this.events = this.restService.getEvent();
			addMessage("NEW persoane => " + this.selectedEvent.getEventID());
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
