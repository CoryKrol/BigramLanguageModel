package me.coryt.bigram.data.mapper;

import me.coryt.bigram.data.model.BiGram;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BiGramMapper {
	public List<BiGram> processTokens(List<List<String>> tokenizedSentences) {
		return new ArrayList<>();
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
