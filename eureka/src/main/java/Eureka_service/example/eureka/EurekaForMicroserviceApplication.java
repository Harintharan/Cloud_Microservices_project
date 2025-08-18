package Eureka_service.example.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaForMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaForMicroserviceApplication.class, args);
	}

}
