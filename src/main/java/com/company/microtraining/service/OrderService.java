package com.company.microtraining.service;

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
	
	
}
