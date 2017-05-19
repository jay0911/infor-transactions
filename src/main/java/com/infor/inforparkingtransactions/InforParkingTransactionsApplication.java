package com.infor.inforparkingtransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InforParkingTransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InforParkingTransactionsApplication.class, args);
	}
}
