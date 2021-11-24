package com.company.microtraining.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.microtraining.model.Order;
import com.company.microtraining.service.OrderService;

@RestController
public class OrderController {

	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}

	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody Order order) {
		return new ResponseEntity<>(service.createOrder(order),HttpStatus.OK);
	}

	@GetMapping("/getAllOrders")
	public ResponseEntity<?> read(){
		return new ResponseEntity<>(service.getAllOrders(), HttpStatus.OK);
	}

	@GetMapping("/getOrder/{id}")
	public ResponseEntity<?> readById(@PathVariable int id){
		return new ResponseEntity<>(service.findOrderById(id),HttpStatus.OK);
	}

	@PutMapping(value = "/updateOrder/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateOrderById(@PathVariable int id, @RequestBody Order order) {
		return new ResponseEntity<>(service.updateOrder(id,order), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteOrder/{id}")
	public ResponseEntity<?> deleteOrderById(@PathVariable int id) {
		return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
	}
	
	
	
}
