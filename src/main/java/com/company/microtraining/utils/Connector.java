package com.company.microtraining.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.company.microtraining.model.Order;
import com.google.cloud.bigquery.*;

@Component
public class Connector {
	private BigQuery bigQuery;

	public Connector() {
		bigQuery = BigQueryOptions.getDefaultInstance().getService();
	}
	
	//QUERY : INSERT INTO thd_dataset123.Orders (orderID,orderSkus,orderDestination,orderQuantity,orderStatus) values (1,"123,245","Guadalajara",12,"ok")
	//Lista a string: String.join(DELIMITER,LISTA)
	//String a lista: String.split(Arrays.asList(string.split(DELIMITADOR))
	public String register(Order order) throws InterruptedException {
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("INSERT INTO thd_dataset123.Orders (orderId,orderSkus,orderDestination,orderQuantity,orderStatus) "
						+ "VALUES ("+order.getOrderId()+
						", '"+order.getOrderSkus().toString()+"'"+
						", '"+order.getOrderDestination()+"'"+
						","+order.getOrderQuantity()+
						", '"+order.getOrderStatus()+"')").build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		job = job.waitFor();
		return "Done!";
	}
	
	public List<Order> getAllOrders() throws InterruptedException{
		
		List<Order> allOrders = new ArrayList<>();
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("SELECT * FROM thd_dataset123.Orders").build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		job = job.waitFor();
		
		TableResult result = job.getQueryResults();
		
		
		for(FieldValueList row : result.iterateAll()) {
			int orderId = (int) row.get("orderId").getLongValue();
			String orderSkus = row.get("orderSkus").getStringValue();
			String orderDestination = row.get("orderDestination").getStringValue();
			int orderQuantity = (int) row.get("orderQuantity").getLongValue();
			String orderStatus = row.get("orderStatus").getStringValue();
			
			List<String>skusList = new ArrayList<String>(Arrays.asList(orderSkus.split(",")));
			
			allOrders.add(new Order(orderId,skusList,orderDestination,orderQuantity,orderStatus));
		}
		
		return allOrders;
	}
	
	public Order findOrderById(int id) throws InterruptedException {
		Order order = new Order();
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("SELECT * FROM thd_dataset123.Orders WHERE orderId = " + id).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		job = job.waitFor();
		
		TableResult result = job.getQueryResults();
		
		
		for(FieldValueList row : result.iterateAll()) {
			order.setOrderId((int) row.get("orderId").getLongValue());
			List<String>skusList = new ArrayList<String>(Arrays.asList(row.get("orderSkus").getStringValue().split(",")));
			order.setOrderSkus(skusList);
			order.setOrderDestination(row.get("orderDestination").getStringValue());
			order.setOrderQuantity((int) row.get("orderQuantity").getLongValue());
			order.setOrderStatus(row.get("orderStatus").getStringValue());
		}
		
		return order;
	}
	
	public String deleteOrderById(int id) throws InterruptedException {
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("DELETE FROM thd_dataset123.Orders WHERE orderId = "+ id).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		job = job.waitFor();
		return "Order deleted!";
	}
	
	public String update(int id,Order order) throws InterruptedException {
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("UPDATE thd_dataset123.Orders "
						+ "SET orderStatus = '"+order.getOrderStatus().toString()+
						"','"+order.getOrderDestination()+
						"',"+order.getOrderQuantity()+",'"+order.getOrderStatus()+"')"
						+ "WHERE orderId = " + id).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		job = job.waitFor();
		return "Order updated!";
	}
	
}
