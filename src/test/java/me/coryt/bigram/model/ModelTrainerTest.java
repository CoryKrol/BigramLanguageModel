package me.coryt.bigram.model;

import me.coryt.bigram.model.data.BiGram;
import me.coryt.bigram.util.ResourceReader;
import me.coryt.bigram.util.TextProcessingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ModelTrainerTest {
	
	UniGramModel uniGramModel;
	
	BiGramModel biGramModel;
	
	ModelTrainer modelTrainer;
	
	@BeforeEach
	void setup() {
		uniGramModel = new UniGramModel();
		biGramModel = new BiGramModel();
		
		modelTrainer = new ModelTrainer(uniGramModel, biGramModel);
	}
	
	@Test
	void testTrainModel() {
		modelTrainer.trainModel("/Users/coryt/IdeaProjects/Bigram Language Model/src/main/resources/train.txt");
		
		Assertions.assertEquals(228413, modelTrainer.getTotalBiGrams());
	}
	
	@Test
	void testPredictSentences() {
		modelTrainer.trainModel("/Users/coryt/IdeaProjects/Bigram Language Model/src/main/resources/train.txt");
		List<List<BiGram>> testData = modelTrainer.loadTestData(TextProcessingUtil.tokenizeCorpus(ResourceReader.readFileToString("test.txt")));
		List<Double> predictions = modelTrainer.getTrainedSentenceProbabilities(testData);
		Assertions.assertEquals(0.01, predictions.get(0), 0.05);
	}
	
	@Test
	void testLoadData() {
		List<List<String>> input = TextProcessingUtil.tokenizeCorpus(BiGramModelTest.CORPUS_DUPLICATE_BIGRAMS);
		List<List<BiGram>> result = modelTrainer.loadTestData(input);
		
		Assertions.assertEquals("<s>, i", result.get(0).get(0).getKey());
	}
	
	@Test
	void testPrintResults() {
		modelTrainer.trainModel("/Users/coryt/IdeaProjects/Bigram Language Model/src/main/resources/train.txt");
		List<List<BiGram>> testData = modelTrainer.loadTestData(TextProcessingUtil.tokenizeCorpus(ResourceReader.readFileToString("test.txt")));
		modelTrainer.printResults(testData);
	}
}
