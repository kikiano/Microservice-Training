package com.company.microtraining.utils;

import com.company.microtraining.model.Order;
import com.google.cloud.bigquery.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class Connector {
	
	private final BigQuery bigQuery;

	private final Logger LOGGER = LoggerFactory.getLogger(Connector.class);

	public Connector() {
		bigQuery = BigQueryOptions.getDefaultInstance().getService();
	}

	public String create(Order order)  {
		LOGGER.info("METHOD: create() : ACTION = CALLED : INPUT = " + order.toString());
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("INSERT INTO thd_dataset123.Orders " +
						"(orderId,orderSkus,orderDestination,orderQuantity,orderStatus,orderDate) "
						+ "VALUES ("+order.getOrderId()+
						", '"+order.getOrderSkus().toString().substring(1, order.getOrderSkus().toString().length()-1)+"'"+
						", '"+order.getOrderDestination()+"'"+
						","+order.getOrderQuantity()+
						", '"+order.getOrderStatus()+"'"+
						", '"+order.getCurrentDate(order.getDate())+"'"+")").build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		LOGGER.trace("QUERY: " + config.getQuery());
		try {
			job = job.waitFor();
			LOGGER.info("METHOD: create() : ACTION = SUCCESSFUL QUERY");
			return "The order with the orderID: " +order.getOrderId()+" has been created";
		}catch (InterruptedException e){
			LOGGER.error("METHOD: create() : ACTION = ERROR [" + Arrays.toString(e.getStackTrace()) + "]");
			return null;
		}
	}

	public List<Order> getAllOrders(){
		LOGGER.info("METHOD: getAllOrders() : ACTION = CALLED");
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("SELECT * FROM thd_dataset123.Orders").build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		LOGGER.trace("QUERY: " + config.getQuery());
		try{
			job = job.waitFor();
			TableResult result = job.getQueryResults();
			if(result.getTotalRows() > 0){
				List<Order> allOrders = new ArrayList<>();
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
				LOGGER.info("METHOD: getAllOrders() : ACTION = SUCCESSFUL QUERY");
				return allOrders;
			}else {
				LOGGER.warn("METHOD: getAllOrders() : ACTION = DENIED");
				throw new IllegalStateException();
			}
		}catch (InterruptedException e){
			LOGGER.error("METHOD: getAllOrders() : ACTION = ERROR [" + Arrays.toString(e.getStackTrace()) + "]");
			return null;
		}
	}

	public Order findOrderById(int id) {
		LOGGER.info("METHOD: findOrderById() : ACTION = CALLED : INPUT = " + id);
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("SELECT * FROM thd_dataset123.Orders WHERE orderId = " + id).build();
		JobId jobId = JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		LOGGER.trace("QUERY: " + config.getQuery());
		try {
			job = job.waitFor();
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
			LOGGER.info("METHOD: findOrderById() : ACTION = SUCCESSFUL QUERY");
			return order;
		}catch (InterruptedException e){
			LOGGER.error("METHOD: findOrderById() : ACTION = ERROR [" + Arrays.toString(e.getStackTrace()) + "]");
			return null;
		}
	}

	public String deleteById(int id){
		LOGGER.info("METHOD: deleteById() : ACTION = CALLED : INPUT = " + id);
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("DELETE  FROM thd_dataset123.Orders WHERE orderId = "+ id).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		LOGGER.trace("QUERY: " + config.getQuery());
		try{
			job = job.waitFor();
			LOGGER.info("METHOD:  deleteById() : ACTION = SUCCESSFUL QUERY");
			return "Order: "+id+" has been deleted!";
		}catch (InterruptedException e){
			LOGGER.error("METHOD: deleteById() : ACTION = ERROR [" + Arrays.toString(e.getStackTrace()) + "]");
			return null;
		}
	}

	public String update(Order order)  {
		LOGGER.info("METHOD: update() : ACTION = CALLED : INPUT = " + order.toString());
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("UPDATE thd_dataset123.Orders "
						+ "SET orderSkus = '"+order.getOrderSkus().toString().substring(1, order.getOrderSkus().toString().length()-1)+
						"',orderDestination='"+order.getOrderDestination()+
						"',orderQuantity="+order.getOrderQuantity()+
						", orderStatus ='"+order.getOrderStatus()+"'"+
						" , orderDate = '"+order.getCurrentDate(order.getDate())+"'"
						+ "WHERE orderId = " + order.getOrderId()).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		LOGGER.trace("QUERY: " + config.getQuery());
		try {
			job = job.waitFor();
			LOGGER.info("METHOD:  update()  : ACTION = SUCCESSFUL QUERY");
			return "Order: " +order.getOrderId()+" has been updated";
		}catch (InterruptedException e){
			LOGGER.error("METHOD: update() : ACTION = ERROR [" + Arrays.toString(e.getStackTrace()) + "]");
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

		}
		return false;
	}

}
