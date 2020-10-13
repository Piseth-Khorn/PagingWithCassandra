package com.example.ManagementSystem;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementSystemApplication.class, args);
	}

}
