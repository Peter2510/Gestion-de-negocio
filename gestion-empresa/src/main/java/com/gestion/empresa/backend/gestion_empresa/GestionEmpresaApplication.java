package com.gestion.empresa.backend.gestion_empresa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.gestion.empresa.backend.gestion_empresa.models")
public class GestionEmpresaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEmpresaApplication.class, args);
	}

}
