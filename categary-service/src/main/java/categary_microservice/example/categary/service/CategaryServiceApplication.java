package categary_microservice.example.categary.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients
public class CategaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategaryServiceApplication.class, args);
	}

}
