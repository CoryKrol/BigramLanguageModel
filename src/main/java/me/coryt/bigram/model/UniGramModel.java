package me.coryt.bigram.model;

import lombok.Getter;
import me.coryt.bigram.model.data.UniGram;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UniGramModel {
	@Getter
	private Map<String, UniGram> grams;
	
	@Getter
	private int totalGrams = 0;
	
	public Map<String, UniGram> normalizeCounts(Map<String, UniGram> uniGramMap, int totalUniGrams) {
		uniGramMap.forEach((s, uniGram) -> uniGram.setNormalizedCount(totalUniGrams));
		return uniGramMap;
	}
	
	public List<List<UniGram>> processTokens(List<List<String>> tokenizedSentences) {
		this.totalGrams = 0;
		grams = new HashMap<>();
		List<List<UniGram>> gramReturnList = new ArrayList<>();
		tokenizedSentences.forEach(sentence -> {
			List<UniGram> newUniGrams = toUniGram(sentence);
			this.totalGrams += newUniGrams.size();
			
			for (int i = 0; i < newUniGrams.size(); i++) {
				UniGram newUniGram = newUniGrams.get(i);
				String key = newUniGram.getKey();
				
				if (grams.containsKey(key)) {
					UniGram foundUniGram = grams.get(key);
					foundUniGram.incrementCount();
					newUniGrams.set(i, foundUniGram);
				} else {
					newUniGram.incrementCount();
					grams.put(key, newUniGram);
				}
			}
			gramReturnList.add(newUniGrams);
		});
		
		return gramReturnList;
	}
	
	public List<UniGram> toUniGram(List<String> tokens) {
		List<UniGram> resultList = new ArrayList<>();
		tokens.forEach(token -> resultList.add(new UniGram(token)));
		return resultList;
	}
	
	public UniGram getGram(String key) {
		return this.grams.get(key);
	}
}
