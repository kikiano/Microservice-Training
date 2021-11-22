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
}
