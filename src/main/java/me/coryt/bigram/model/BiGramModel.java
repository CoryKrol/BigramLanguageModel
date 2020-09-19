package me.coryt.bigram.model;

import lombok.Getter;
import me.coryt.bigram.model.data.BiGram;
import me.coryt.bigram.model.data.UniGram;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BiGramModel {
	@Getter
	private Map<String, BiGram> grams;
	
	@Getter
	private int totalBiGrams = 0;
	
	public void initializeModel(List<List<String>> tokenizedSentences) {
		this.grams = normalizeCounts(processTokens(tokenizedSentences), totalBiGrams);
	}
	
	public Map<String, BiGram> normalizeCounts(Map<String, BiGram> biGramMap, int totalBiGrams) {
		biGramMap.forEach((s, biGram) -> biGram.setNormalizedCount(totalBiGrams));
		return biGramMap;
	}
	
	public Map<String, BiGram> processTokens(List<List<String>> tokenizedSentences) {
		this.totalBiGrams = 0;
		Map<String, BiGram> biGramMap = new HashMap<>();
		tokenizedSentences.forEach(sentence -> {
			List<BiGram> newBiGrams = toBiGram(sentence);
			this.totalBiGrams += newBiGrams.size();
			
			for (int i = 0; i < newBiGrams.size(); i++) {
				BiGram newBigram = newBiGrams.get(i);
				String biGramKey = newBigram.getKey();
				
				if (biGramMap.containsKey(biGramKey)) {
					BiGram foundBiGram = biGramMap.get(biGramKey);
					foundBiGram.incrementCount();
					newBiGrams.set(i, foundBiGram);
				} else {
					newBigram.incrementCount();
					biGramMap.put(biGramKey, newBigram);
				}
			}
		});
		return biGramMap;
	}
	
	public List<List<BiGram>> processTestData(List<List<String>> tokenizedSentences, Map<String, UniGram> testUniGramMap) {
		List<List<BiGram>> returnList = new ArrayList<>();
		Map<String, BiGram> newGramMap = new HashMap<>();
		
		tokenizedSentences.forEach(sentence -> {
			List<BiGram> newBiGrams = toBiGram(sentence);
			
			for (int i = 0; i < newBiGrams.size(); i++) {
				BiGram newBigram = newBiGrams.get(i);
				String biGramKey = newBigram.getKey();
				
				if (newGramMap.containsKey(biGramKey)) {
					BiGram foundBiGram = newGramMap.get(biGramKey);
					foundBiGram.incrementCount();
					newBiGrams.set(i, foundBiGram);
				} else {
					newBigram.incrementCount();
					newGramMap.put(biGramKey, newBigram);
				}
			}
			
			
			returnList.add(newBiGrams);
		});
		
		setTestNormCount(returnList, newGramMap);
		newGramMap.values().forEach(biGram -> biGram.setProbability(biGram.getNormalizedCount(), testUniGramMap.get(biGram.getValue()).getNormalizedCount()));
		return returnList;
	}
	
	public void setTestNormCount(List<List<BiGram>> testData, Map<String, BiGram> biGramMap) {
		int i = 0;
		for (List<BiGram> sentence : testData) {
			i += sentence.size();
		}
		
		int finalI = i;
		biGramMap.forEach((s, biGram) -> biGram.setNormalizedCount(finalI));
	}
	
	public List<BiGram> toBiGram(List<String> tokens) {
		List<BiGram> resultList = new ArrayList<>();
		if (tokens.size() > 1) {
			for (int i = 0; i <= tokens.size(); i++) {
				if (i == tokens.size()) {
					resultList.add(new BiGram(tokens.get(i - 1), false));
				} else if (i == 0) {
					resultList.add(new BiGram(tokens.get(i), true));
				} else {
					resultList.add(new BiGram(tokens.get(i - 1), tokens.get(i)));
				}
			}
		} else if (tokens.size() == 1) {
			resultList.add(new BiGram(tokens.get(0), true));
			resultList.add(new BiGram(tokens.get(0), false));
		}
		return resultList;
	}
	
	public BiGram getGram(String key) {
		return this.grams.get(key);
	}
	
	public void setBiGramProbabilities(Map<String, UniGram> uniGramCounts) {
		List<BiGram> biGrams = new ArrayList<>(grams.values());
		try {
			for (BiGram biGram : biGrams) {
				biGram.setProbability(
						biGram.getNormalizedCount(),
						uniGramCounts.get(biGram.getValue()).getNormalizedCount());
			}
		} catch (NullPointerException nullPointerException) {
			System.out.println(nullPointerException.getMessage());
		}
	}
}
