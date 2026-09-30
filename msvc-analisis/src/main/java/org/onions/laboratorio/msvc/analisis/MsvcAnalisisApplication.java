package org.onions.laboratorio.msvc.analisis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcAnalisisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAnalisisApplication.class, args);
	}

}
