package com.company.microtraining.utils;

import java.util.*;

import org.springframework.stereotype.Component;

import com.company.microtraining.model.Order;
import com.google.cloud.bigquery.*;


@Component
public class Connector {
	
	private final BigQuery bigQuery;

	public Connector() {
		bigQuery = BigQueryOptions.getDefaultInstance().getService();
	}

	public String create(Order order)  {
		
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("INSERT INTO thd_dataset123.Orders " +
						"(orderId,orderSkus,orderDestination,orderQuantity,orderStatus,orderDate) "
						+ "VALUES ("+order.getOrderId()+
						", '"+order.getOrderSkus().toString().substring(1, order.getOrderSkus().toString().length()-1)+"'"+
						", '"+order.getOrderDestination()+"'"+
						","+order.getOrderQuantity()+
						", '"+order.getOrderStatus()+"'"+
						", '"+order.currentDate(order.getDate())+"'"+")").build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try {
			job = job.waitFor();
			return "The order with the orderID: " +order.getOrderId()+" has been created";
		}catch (InterruptedException e){
			//TODO add logger sentence
			e.printStackTrace();
			return null;
		}
	}

	public List<Order> getAllOrders(){
		List<Order> allOrders = new ArrayList<>();
		
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("SELECT * FROM thd_dataset123.Orders").build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try{
			job = job.waitFor();
			TableResult result = job.getQueryResults();
			for(FieldValueList row : result.iterateAll()) {
				Order myOrder = new Order();
				myOrder.setOrderId((int) row.get("orderId").getLongValue());
				List<String>skusList = new ArrayList<>(Arrays.asList(row.get("orderSkus").getStringValue().split(",")));
				myOrder.setOrderSkus(skusList);
				myOrder.setOrderDestination(row.get("orderDestination").getStringValue());
				myOrder.setOrderQuantity((int) row.get("orderQuantity").getLongValue());
				myOrder.setOrderStatus(row.get("orderStatus").getStringValue());
				myOrder.setDate(row.get("orderDate").getStringValue());
				allOrders.add(myOrder);
			}
			return allOrders;
		}catch (InterruptedException e){
			e.printStackTrace();
			return null;
		}
		
	}

	public Order findOrderById(int id) {
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("SELECT * FROM thd_dataset123.Orders WHERE orderId = " + id).build();
		JobId jobId = JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try {
			job = job.waitFor();
			if(job == null) {
				return null;
			}else{
				TableResult result = job.getQueryResults();
				Order order = new Order();
				for(FieldValueList row : result.iterateAll()) {
					order.setOrderId((int) row.get("orderId").getLongValue());
					List<String>skusList = new ArrayList<>(Arrays.asList(row.get("orderSkus").getStringValue().split(",")));
					order.setOrderSkus(skusList);
					order.setOrderDestination(row.get("orderDestination").getStringValue());
					order.setOrderQuantity((int) row.get("orderQuantity").getLongValue());
					order.setOrderStatus(row.get("orderStatus").getStringValue());
					order.setDate(row.get("orderDate").getStringValue());
				}
				return order;				
			}
		}catch (InterruptedException e){
			e.printStackTrace();
			return null;
		}
		
	}

	public String deleteById(int id){
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("DELETE  FROM thd_dataset123.Orders WHERE orderId = "+ id).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try{
			job = job.waitFor();
			
			return "Order: "+id+" has been deleted!";
		}catch (InterruptedException e){
			e.printStackTrace();
			return null;
		}
		
	}

	public String update(Order order)  {
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("UPDATE thd_dataset123.Orders "
						+ "SET orderSkus = '"+order.getOrderSkus().toString().substring(1, order.getOrderSkus().toString().length()-1)+
						"', orderDestination='"+order.getOrderDestination()+
						"', orderQuantity="+order.getOrderQuantity()+
						" , orderStatus ='"+order.getOrderStatus()+"'"+
						" , orderDate = '"+order.currentDate(order.getDate())+"'"
						+ "WHERE orderId = " + order.getOrderId()).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try {
			job = job.waitFor();
			TableResult result = job.getQueryResults();
			return "Order: " +order.getOrderId()+" has been updated";
		}catch (InterruptedException e){
			e.printStackTrace();
			return null;
		}
	}

	public boolean existById(int id){
		int val = 0;
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("SELECT orderId FROM thd_dataset123.Orders " +
						"WHERE orderId = " + id).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try{
			job.waitFor();
			TableResult result = job.getQueryResults();
			for(FieldValueList row: result.iterateAll()) {
				val = (int) row.get("orderId").getLongValue();
			}
			if(val != 0) {
				return true;
			}else {
				throw new NoSuchElementException();
			}
		}catch(Exception e) {
			//TODO cachar la exception por logger en lugar de printstacktrace
			//e.printStackTrace();
		}
		return false;
	}

}
