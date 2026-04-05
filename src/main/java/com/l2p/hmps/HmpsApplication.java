package com.l2p.hmps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HmpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmpsApplication.class, args);
	}
}
