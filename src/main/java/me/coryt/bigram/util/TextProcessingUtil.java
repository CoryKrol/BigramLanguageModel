package me.coryt.bigram.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class TextProcessingUtil {
	
	/**
	 * Split corpus on new lines
	 *
	 * @param corpus string of text
	 * @return list of sentences
	 */
	public List<String> splitCorpus(String corpus) {
		return Arrays.asList(corpus.split("\\n"));
	}
	
	/**
	 * Split a list of sentences into a list of sentence tokens
	 *
	 * @param corpus list of sentences
	 * @return list of sentences represented as tokens
	 */
	public List<List<String>> splitSentences(List<String> corpus) {
		List<List<String>> sentenceList = new ArrayList<>();
		corpus.forEach(str -> sentenceList.add(Arrays.asList(str.split("\\s"))));
		return sentenceList;
	}
	
	/**
	 * Replace all non-letter/non-whitespace characters and remove any spurious whitespace around them
	 *
	 * @param corpus input text corpus string
	 * @return processed corpus string
	 */
	public String removeNonWordCharacters(String corpus) {
		return corpus.replaceAll("\\h*([^a-zA-z\\s]|\\[|])+\\h*", "").trim();
	}
	
}
