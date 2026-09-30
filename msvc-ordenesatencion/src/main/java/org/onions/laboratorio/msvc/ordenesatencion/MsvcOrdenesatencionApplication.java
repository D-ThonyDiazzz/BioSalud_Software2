package org.onions.laboratorio.msvc.ordenesatencion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcOrdenesatencionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcOrdenesatencionApplication.class, args);
	}

}
