package com.company.microtraining.utils;

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
	
	public String register(Order order) throws InterruptedException {
		QueryJobConfiguration config = QueryJobConfiguration.
				newBuilder("INSERT INTO thd_dataset123.Orders (orderID,orderSkus,orderDestination,orderQuantity,orderStatus) values (2,'134,124','Mexico',15,'ok')").setUseLegacySql(false).build();
		JobId jobId= JobId.of(UUID.randomUUID().toString());
		
		Job job = bigQuery.create(JobInfo.newBuilder(config).setJobId(jobId).build());
		job = job.waitFor();
		return "Done!";
	}
	
	
}
