package com.company.microtraining.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public String save(@RequestBody Order order) throws InterruptedException {
		return service.test(order);
	}
}
