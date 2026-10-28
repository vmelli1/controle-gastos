package com.victor.controle_gastos_port;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControleGastosPortApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleGastosPortApplication.class, args);
	}

}
