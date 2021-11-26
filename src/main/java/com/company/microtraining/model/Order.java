package com.company.microtraining.model;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class Order {
	private int orderId;
	private List<String> orderSkus;
	private String orderDestination;
	private Date date;
	private long orderQuantity;
	private String orderStatus;

	public Order(int orderId, List<String> orderSkus, String orderDestination, Date date, long orderQuantity,
			String orderStatus) {
		this.orderId = orderId;
		this.orderSkus = orderSkus;
		this.orderDestination = orderDestination;
		this.date = date;
		this.orderQuantity = orderQuantity;
		this.orderStatus = orderStatus;
	}
	public Order() {
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
	public Date getDate() {
		return date;
	}
	public void setDate(String date) {
		try {
			this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public String getCurrentDate(Date date){
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
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
	@Override
	public String toString() {
		return "Order{" +
				"orderId=" + orderId +
				", orderSkus=" + orderSkus +
				", orderDestination='" + orderDestination + '\'' +
				", date=" + date +
				", orderQuantity=" + orderQuantity +
				", orderStatus='" + orderStatus + '\'' +
				'}';
	}
}
