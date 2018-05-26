package com.pdf.api.models;

import java.util.List;

public class Doc {
	
	private Customer 		customer;
	private List<OrderItem> orderItems;
	
	public Customer getCustomer () {
		return customer;
	}
	public void setCustomer (Customer customer) {
		this.customer = customer;
	}
	public List<OrderItem> getOrderItems () {
		return orderItems;
	}
	public void setLines (List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}