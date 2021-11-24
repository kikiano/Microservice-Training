package com.company.microtraining.model;

//import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	private int orderId;
	private List<String> orderSkus;
	private String orderDestination;
	//private Date date;
	private long orderQuantity;
	private String orderStatus;
	
	public Order(int orderId, List<String> orderSkus, String orderDestination, long orderQuantity, String orderStatus) {
		this.orderId = orderId;
		this.orderSkus = orderSkus;
		this.orderDestination = orderDestination;
		this.orderQuantity = orderQuantity;
		this.orderStatus = orderStatus;
	}
	
	public Order() {
		// TODO Auto-generated constructor stub
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public List<String> getOrderSkus() {
		return orderSkus;
	}
	public void setOrderSkus(List<String> orderSkus) {
		this.orderSkus = orderSkus;
	}
	public String getOrderDestination() {
		return orderDestination;
	}
	public void setOrderDestination(String orderDestination) {
		this.orderDestination = orderDestination;
	}
	public long getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
