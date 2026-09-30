package org.onions.laboratorio.msvc.muestras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcMuestrasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcMuestrasApplication.class, args);
	}

}
