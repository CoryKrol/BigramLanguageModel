package me.coryt.bigram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"me.coryt.bigram"})
public class BiGramApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BiGramApplication.class);
	}
}
