package com.beehive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;

import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		BeeHiveApplication.class,
		Jsr310JpaConverters.class
})
public class BeeHiveApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("CET"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BeeHiveApplication.class, args);
	}
}