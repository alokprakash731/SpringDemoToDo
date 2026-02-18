package com.example.SpringDemoToDo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;



@SpringBootApplication
public class SpringDemoToDoApplication {

	public static void main(String[] args) {

         // Configure dotenv to load the env variables from my .env file
		Dotenv dotenv = Dotenv.configure().load();

		// Iterate through the entries and set the system properties
		dotenv.entries().forEach((entry) -> System.setProperty(entry.getKey(),entry.getValue()));
		
		// Run the Spring application
		SpringApplication.run(SpringDemoToDoApplication.class, args);
		
	}

}
