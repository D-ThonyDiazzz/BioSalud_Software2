package org.onions.laboratorio.msvc.responsables;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcResponsablesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcResponsablesApplication.class, args);
	}

}
