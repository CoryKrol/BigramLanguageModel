package me.coryt.bigram.model;

import lombok.Getter;
import me.coryt.bigram.model.data.BiGram;
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
	
	public void initializeMode(List<List<String>> tokenizedSentences) {
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
		} else {
			resultList.add(new BiGram(tokens.get(0), true));
			resultList.add(new BiGram(tokens.get(0), false));
		}
		return resultList;
	}
}
