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
						"(orderId,orderSkus,orderDestination,orderQuantity,orderStatus) "
						+ "VALUES ("+order.getOrderId()+
						", '"+order.getOrderSkus().toString()+"'"+
						", '"+order.getOrderDestination()+"'"+
						","+order.getOrderQuantity()+
						", '"+order.getOrderStatus()+"')").build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try {
			job = job.waitFor();
			//TODO correct the return string
			return "DONE";
		}catch (InterruptedException e){
			//TODO add logger sentence
			e.printStackTrace();
		}
		//TODO correct the usage of the null
		return null;
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
				int orderId = (int) row.get("orderId").getLongValue();
				String orderSkus = row.get("orderSkus").getStringValue();
				String orderDestination = row.get("orderDestination").getStringValue();
				int orderQuantity = (int) row.get("orderQuantity").getLongValue();
				String orderStatus = row.get("orderStatus").getStringValue();
				List<String>skusList = new ArrayList<>(Arrays.asList(orderSkus.split(",")));
				allOrders.add(new Order(orderId,skusList,orderDestination,orderQuantity,orderStatus));
			}
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		return allOrders;
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
				}
				return order;
			}
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		return null;
	}

	public String deleteById(int id){
		//TODO check if the ordernumber exists
		//TODO modify the returning String
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("DELETE FROM thd_dataset123.Orders WHERE orderId = "+ id).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try{
			job = job.waitFor();
			return "Order deleted!";
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		return null;
	}

	public String update(int id,Order order)  {
		//TODO update the logic
		//TODO modify the returning String
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("UPDATE thd_dataset123.Orders "
						+ "SET orderStatus = '"+order.getOrderStatus()+
						"',orderDestination='"+order.getOrderDestination()+
						"',orderQuantity="+order.getOrderQuantity()+
						", orderStatus ='"+order.getOrderStatus()+"'"
						+ "WHERE orderId = " + id).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try {
			job = job.waitFor();
			TableResult result = job.getQueryResults();
			return "Order updated!";
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean existById(int id){
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("SELECT orderId FROM thd_dataset123.Orders " +
						"WHERE orderID = " + id).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		try {
			job = job.waitFor();
			TableResult result = job.getQueryResults();
			return result != null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

}
