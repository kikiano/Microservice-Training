package com.company.microtraining.service;

import com.company.microtraining.model.Order;
import com.company.microtraining.utils.Connector;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
	private final Connector connector;

	public OrderService(Connector connector) {
		this.connector = connector;
	}
	
	public String createOrder(Order order){
		return connector.create(order);
	}
	
	public List<Order> getAllOrders() {
		return connector.getAllOrders();
	}
	
	public Order findOrderById(int id) {
		return connector.findOrderById(id);
	}
	
	public String deleteById(int id) {
		return connector.deleteById(id);
	}
	
	public String updateOrder(Order order) {
		return connector.update(order);
	}

	public boolean exitsById(int id){
		return connector.existById(id);
	}

}
