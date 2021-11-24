package com.company.microtraining.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.microtraining.model.Order;
import com.company.microtraining.utils.Connector;

@Service
public class OrderService {
	private final Connector connector;

	public OrderService(Connector connector) {
		this.connector = connector;
	}
	
	public String test(Order order) throws InterruptedException {
		return connector.register(order);
	}
	
	public List<Order> getAllOrders() throws InterruptedException {
		return connector.getAllOrders();
	}
	
	public Order findOrderById(int id) throws InterruptedException {
		return connector.findOrderById(id);
	}
	
	public String deleteOrderbyId(int id) throws InterruptedException {
		return connector.deleteOrderById(id);
	}
	
	public String updateOrder(int id,Order order) throws InterruptedException {
		return connector.update(id, order);
	}
}
