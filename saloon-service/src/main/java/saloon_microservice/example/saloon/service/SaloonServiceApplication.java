package saloon_microservice.example.saloon.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SaloonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaloonServiceApplication.class, args);
	}

}
