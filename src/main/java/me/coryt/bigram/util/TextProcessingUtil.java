package me.coryt.bigram.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TextProcessingUtil {
	
	public List<List<String>> tokenizeCorpus(String corpus) {
		return splitSentences(splitCorpus(removeNonWordCharacters(corpus.toLowerCase()))).stream().filter(list -> !list.isEmpty()).collect(Collectors.toList());
	}
	
	/**
	 * Replace all non-letter/non-whitespace characters and remove any spurious whitespace around them
	 *
	 * @param corpus input text corpus string
	 * @return processed corpus string
	 */
	public String removeNonWordCharacters(String corpus) {
		return corpus
				.replaceAll(
						ApplicationConstants.NON_WORD_CHARACTERS_REGEX,
						ApplicationConstants.EMPTY_STRING)
				.trim();
	}
	
	/**
	 * Split corpus on new lines
	 *
	 * @param corpus string of text
	 * @return list of sentences
	 */
	public List<String> splitCorpus(String corpus) {
		return Arrays.asList(corpus.split(ApplicationConstants.NEWLINE_REGEX));
	}
	
	/**
	 * Split a list of sentences into a list of sentence tokens
	 *
	 * @param corpus list of sentences
	 * @return list of sentences represented as tokens
	 */
	public List<List<String>> splitSentences(List<String> corpus) {
		List<List<String>> sentenceList = new ArrayList<>();
		corpus.forEach(str -> {
			List<String> tmpList = new ArrayList<>(Arrays.asList(str.split(ApplicationConstants.WHITESPACE_REGEX)));
			tmpList.removeAll(Arrays.asList("", null));
			sentenceList.add(tmpList);
		});
		return sentenceList;
	}
	
}
