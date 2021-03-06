package com.company.microtraining.controller;

import com.company.microtraining.model.Order;
import com.company.microtraining.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		if(service.exitsById(id)){
			return new ResponseEntity<>(service.findOrderById(id),HttpStatus.OK);
		}
		return new ResponseEntity<>("NOT A VALID ID", HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = "/updateOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateOrderById(@RequestBody Order order) {
		if(service.exitsById(order.getOrderId())) {
			return new ResponseEntity<>(service.updateOrder(order), HttpStatus.OK);
		}
		return new ResponseEntity<>("NOT A VALID ID", HttpStatus.BAD_REQUEST);
		
	}
	
	@DeleteMapping("/deleteOrder/{id}")
	public ResponseEntity<?> deleteOrderById(@PathVariable int id) {
		if (service.exitsById(id)) {
			return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
		}
		return new ResponseEntity<>("NOT A VALID ID", HttpStatus.BAD_REQUEST);
	}
}
