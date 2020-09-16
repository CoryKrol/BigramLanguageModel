package me.coryt.bigram.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class TextProcessingUtil {
	
	public List<String> splitCorpus(String corpus) {
		return Arrays.asList(corpus.split("\\n"));
	}
	
	public List<List<String>> splitSentences(List<String> corpus) {
		List<List<String>> sentenceList = new ArrayList<>();
		corpus.forEach(str -> sentenceList.add(Arrays.asList(str.split("\\s"))));
		return sentenceList;
	}
	
}
