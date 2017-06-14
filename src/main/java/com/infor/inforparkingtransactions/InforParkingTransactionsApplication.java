package com.infor.inforparkingtransactions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.infor.models.Email;

@EnableDiscoveryClient
@SpringBootApplication
public class InforParkingTransactionsApplication {
	
	@Value("${email.host}")
	private String host;
	
	@Value("${email.socketport}")
	private String socketport;
	
	@Value("${email.socketclass}")
	private String socketclass;
	
	@Value("${email.auth}")
	private String auth;
	
	@Value("${email.port}")
	private String port;
	
	@Value("${email.username}")
	private String username;
	
	@Value("${email.password}")
	private String password;

	public static void main(String[] args) {
		SpringApplication.run(InforParkingTransactionsApplication.class, args);
	}
	
	@Bean
	public Email email(){
		return new Email(host,socketport,socketclass,auth,port,username,password);
	}
}
