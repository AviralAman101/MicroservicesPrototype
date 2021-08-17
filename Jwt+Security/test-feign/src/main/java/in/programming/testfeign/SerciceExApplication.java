package in.programming.testfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SerciceExApplication {

	public static void main(String[] args) {
		SpringApplication.run(SerciceExApplication.class, args);
	}

}
