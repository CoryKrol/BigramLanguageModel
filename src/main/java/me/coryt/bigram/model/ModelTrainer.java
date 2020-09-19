package me.coryt.bigram.model;

import lombok.RequiredArgsConstructor;
import me.coryt.bigram.model.data.BiGram;
import me.coryt.bigram.model.data.UniGram;
import me.coryt.bigram.util.TextProcessingUtil;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
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
		uniGramModel.processTestUni(uniGramMap);
		
		return biGramModel.processTestData(input, uniGramMap);
	}
	
	public void setBiGramProbability() {
		biGramModel.setBiGramProbabilities(uniGramModel.getGrams());
	}
	
	public double predictSentence(List<BiGram> sentence) {
		// TODO: Unknown bigrams produce NPE
		return Math.exp(sentence.stream().mapToDouble(biGram -> Math.log(biGramModel.getGram(biGram.getKey()).getProbability()) / Math.log(Math.E)).sum());
	}
	
	public List<Double> getTrainedSentenceProbabilities(List<List<BiGram>> sentences) {
		List<Double> sentenceProbabilities = new ArrayList<>();
		for (List<BiGram> sentence : sentences) {
			sentenceProbabilities.add(predictSentence(sentence));
		}
		
		return sentenceProbabilities;
	}
	
	public void printResults(List<List<BiGram>> testData) {
		DecimalFormat decimalFormat = new DecimalFormat("0.####E0");
		List<Double> trainedSentenceProbabilities = getTrainedSentenceProbabilities(testData);
		
		List<List<String>> outputTable = new ArrayList<>();
		
		outputTable.add(Arrays.asList("Sentence # ", "BiGram", "BiGram Count", "BiGram Probability"));
		for (int i = 0; i < testData.size(); i++) {
			for (int j = 0; j < testData.get(i).size(); j++) {
				List<String> rowData = new ArrayList<>();
				if (j == 0) {
					rowData.add(String.valueOf(i + 1));
				} else if (j == 1) {
					rowData.add("Sentence Prob: " + decimalFormat.format(trainedSentenceProbabilities.get(i)));
				} else {
					rowData.add("");
				}
				
				BiGram biGram = testData.get(i).get(j);
				
				rowData.add(biGram.getKey());
				rowData.add(String.valueOf(biGram.getCount()));
				rowData.add(String.valueOf(biGram.getProbability()));
				
				outputTable.add(rowData);
			}
		}
		
		for (List<String> row : outputTable) {
			for (String value : row) {
				System.out.printf("%30s", value);
			}
			System.out.print("\n");
		}
	}
}
