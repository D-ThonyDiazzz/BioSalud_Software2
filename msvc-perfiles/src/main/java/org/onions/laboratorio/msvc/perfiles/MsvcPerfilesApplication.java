package org.onions.laboratorio.msvc.perfiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcPerfilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcPerfilesApplication.class, args);
	}

}
