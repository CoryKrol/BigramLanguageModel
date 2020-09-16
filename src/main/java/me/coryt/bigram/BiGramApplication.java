package me.coryt.bigram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"me.coryt.bigram"})
public class BiGramApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(BiGramApplication.class);
	}
	
	
	@Override
	public void run(String... args) {
		log.info("EXECUTING: command line runner");
	}
}