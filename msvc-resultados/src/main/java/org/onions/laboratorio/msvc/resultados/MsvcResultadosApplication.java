package org.onions.laboratorio.msvc.resultados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcResultadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcResultadosApplication.class, args);
	}

}
