package com.pdf.api.models;

public class OrderItem {

	private String name;
	private String unitCost;
	private String totalCost;
	private String cartons;
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getUnitCost () {
		return unitCost;
	}
	
	public void setUnitCost (String unitCost) {
		this.unitCost = unitCost;
	}
	
	public String getTotalCost () {
		return totalCost;
	}

	public void setTotalCost (String totalCost) {
		this.totalCost = totalCost;
	}

	public String getCartons () {
		return cartons;
	}
	
	public void setCartons (String cartons) {
		this.cartons = cartons;
	}	
}