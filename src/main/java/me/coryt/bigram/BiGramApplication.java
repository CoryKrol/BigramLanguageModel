package me.coryt.bigram;

import lombok.extern.slf4j.Slf4j;
import me.coryt.bigram.model.ModelTrainer;
import me.coryt.bigram.model.data.BiGram;
import me.coryt.bigram.util.ResourceReader;
import me.coryt.bigram.util.TextProcessingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class BiGramApplication implements CommandLineRunner {
	@Autowired
	ModelTrainer modelTrainer;
	
	public static void main(String[] args) {
		SpringApplication.run(BiGramApplication.class);
	}
	
	
	@Override
	public void run(String... args) {
		if (args.length != 2) {
			log.error("Invalid number of arguments");
		}
		
		log.info("EXECUTING: command line runner");
//		modelTrainer.trainModel(args[0]);
		modelTrainer.trainModel("/Users/coryt/IdeaProjects/Bigram Language Model/src/main/resources/train.txt");
		log.info("MODEL TRAINED: ");
		List<List<BiGram>> testData = modelTrainer.loadTestData(TextProcessingUtil.tokenizeCorpus(ResourceReader.readFileToString("test.txt")));
		log.info("TEST DATA LOADED: ");

//		List<Double> probabilities = modelTrainer.getSentenceProbabilities(testData);
		
	}
	
	public void displayHelp() {
		log.info("Program accepts an absolute file path and a 0/1 to enable/disable Laplace smoothing");
	}
}