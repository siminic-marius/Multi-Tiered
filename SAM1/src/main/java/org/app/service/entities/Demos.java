package org.app.service.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Demos extends Event implements Serializable{
	
	private Integer demosId;
	private String titleDemos;
	private String productDemos;
	private Double productVersionDemos;
	
	public Integer getDemosId() {
		return demosId;
	}
	public void setDemosId(Integer demosId) {
		this.demosId = demosId;
	}
	public String getTitleDemos() {
		return titleDemos;
	}
	public void setTitleDemos(String titleDemos) {
		this.titleDemos = titleDemos;
	}
	public String getProductDemos() {
		return productDemos;
	}
	public void setProductDemos(String productDemos) {
		this.productDemos = productDemos;
	}
	public Double getProductVersionDemos() {
		return productVersionDemos;
	}
	public void setProductVersionDemos(Double productVersionDemos) {
		this.productVersionDemos = productVersionDemos;
	}
	@Override
	public String toString() {
		return "Demos [demosId=" + demosId + ", titleDemos=" + titleDemos + ", productDemos=" + productDemos
				+ ", productVersionDemos=" + productVersionDemos + "]";
	}
	public Demos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Demos(Integer eventID, String eventName, String Location, String description, Integer nrPlaces) {
		super(eventID, eventName, Location, description, nrPlaces);
		// TODO Auto-generated constructor stub
	}
	public Demos(Integer demosId, String titleDemos, String productDemos, Double productVersionDemos) {
		super();
		this.demosId = demosId;
		this.titleDemos = titleDemos;
		this.productDemos = productDemos;
		this.productVersionDemos = productVersionDemos;
	}
	
}
