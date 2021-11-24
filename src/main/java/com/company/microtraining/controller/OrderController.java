package com.company.microtraining.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.microtraining.model.Order;
import com.company.microtraining.service.OrderService;

@RestController
public class OrderController {
	private OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}
	
	@GetMapping("/test")
	public String test() {
		return "it work! :)";
	}
	
	@GetMapping("/getAllOrders")
	public List<Order> getAllOrders() throws InterruptedException {
		return service.getAllOrders();
	}
	
	@GetMapping("/getOrder/{id}")
	public Order getOrder(@PathVariable int id) throws InterruptedException {
		return service.findOrderById(id);
	}
	
	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public String save(@RequestBody Order order) throws InterruptedException {
		return service.test(order);
	}
	
	@PostMapping(value = "/updateOrder/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateOrderById(@PathVariable int id, @RequestBody Order order) throws InterruptedException {
		return service.updateOrder(id, order);
	}
	
	@DeleteMapping("/deleteOrder/{id}")
	public String deleteOrderById(@PathVariable int id) throws InterruptedException {
		return service.deleteOrderbyId(id);
	}
	
	
	
}
