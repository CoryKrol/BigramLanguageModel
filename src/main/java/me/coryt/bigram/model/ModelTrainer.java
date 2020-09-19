package me.coryt.bigram.model;

import lombok.RequiredArgsConstructor;
import me.coryt.bigram.model.data.BiGram;
import me.coryt.bigram.model.data.UniGram;
import me.coryt.bigram.util.TextProcessingUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ModelTrainer {
	final UniGramModel uniGramModel;
	
	final BiGramModel biGramModel;
	
	public void trainModel(String trainingDataPath) {
		String trainingCorpus = TextProcessingUtil.readFromFile(trainingDataPath);
		List<List<String>> tokens = TextProcessingUtil.tokenizeCorpus(trainingCorpus);
		
		uniGramModel.processTokens(tokens);
		biGramModel.initializeModel(tokens);
		setBiGramProbability();
	}
	
	public int getTotalBiGrams() {
		return biGramModel.getTotalBiGrams();
	}
	
	public List<List<BiGram>> loadTestData(List<List<String>> input) {
		Map<String, UniGram> uniGramMap = uniGramModel.getTestUniGram(input);
		int totalUniGramsInTest = 0;
		for (UniGram value : uniGramMap.values()) {
			totalUniGramsInTest += value.getCount();
		}
		
		int finalTotalUniGramsInTest = totalUniGramsInTest;
		uniGramMap.values().forEach(uniGram -> uniGram.setNormalizedCount(finalTotalUniGramsInTest));
		
		return biGramModel.processTestData(input, uniGramMap);
	}
	
	public void addStartUniGram(List<List<String>> input) {
		input.forEach(list -> list.add(0, "<s>"));
	}
	
	public void setBiGramProbability() {
		biGramModel.setBiGramProbabilities(uniGramModel.getGrams());
	}
	
	public double predictSentence(List<BiGram> sentence) {
		return Math.exp(sentence.stream().mapToDouble(biGram -> biGramModel.getGram(biGram.getKey()).getProbability()).sum());
	}
	
	public List<Double> getTrainedSentenceProbabilities(List<List<BiGram>> sentences) {
		List<Double> sentenceProbabilities = new ArrayList<>();
		for (List<BiGram> sentence : sentences) {
			sentenceProbabilities.add(predictSentence(sentence));
		}
		
		return sentenceProbabilities;
	}
	
	public void printResults(List<List<BiGram>> testData) {
		List<Double> trainedSentenceProbabilities = getTrainedSentenceProbabilities(testData);
		
		List<List<String>> outputTable = new ArrayList<>();
		
		outputTable.add(Arrays.asList("BiGram", "BiGram Count", "BiGram Probability"));
	}
}
