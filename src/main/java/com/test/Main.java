package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.io.File;

@SpringBootApplication
public class Main {
	public static void main(String[] args) throws JsonProcessingException {
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.directory(new File("/app/bin/"));
			pb.command("/app/bin/main", "start");
			pb.start();
			System.out.println("Server Started on 9090 port");

		} catch (java.lang.Exception exception) {
			System.out.println("Could not start server");
			System.out.println(exception.getMessage());
		}

		SpringApplication.run(Main.class, args);

		try {
			RestTemplate restTemplate = new RestTemplate();
			String data = restTemplate.getForObject("http://localhost:9090/world", String.class);

			System.out.println(data);

			System.exit(0);
			
		} catch (RestClientException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
