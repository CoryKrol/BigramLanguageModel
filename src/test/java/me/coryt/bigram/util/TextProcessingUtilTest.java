package me.coryt.bigram.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.coryt.bigram.util.TextProcessingUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TextProcessingUtilTest {
	private static final String TEST_CORPUS = "[ moby dick by herman melville 1851 ]\n" +
			"etymology .\n" +
			"( supplied by a late consumptive usher to a grammar school )";
	private static final int TEST_CORPUS_NUM_SENTENCES = 3;
	
	private static final int TEST_SENTENCE_ONE_NUMBER_OF_TOKENS = 8;
	private static final int TEST_SENTENCE_TWO_NUMBER_OF_TOKENS = 2;
	private static final int TEST_SENTENCE_THREE_NUMBER_OF_TOKENS = 12;
	
	private static final int TEST_SENTENCE_CLEAN_NUM_TOKENS = 16;
	
	@DisplayName("Should run full tokenization pipeline on 3 sentences and convert to lowercase")
	@Test
	void testTokenizeCorpus() {
		List<List<String>> result = tokenizeCorpus(TEST_CORPUS);
		assertEquals(
				TEST_CORPUS_NUM_SENTENCES,
				result.size());
	}
	
	
	@DisplayName("Should remove non-character & non-white space leaving 16 tokens")
	@Test
	void testRemoveNonWordCharacters() {
		String result = removeNonWordCharacters(TEST_CORPUS);
		assertEquals(
				TEST_SENTENCE_CLEAN_NUM_TOKENS,
				result.split(ApplicationConstants.WHITESPACE_REGEX).length);
	}
	
	@DisplayName("Should split test corpus into ArrayList of size 3 containing token array lists")
	@Test
	void testTokenizeWords() {
		List<List<String>> result = splitSentences(splitCorpus(TEST_CORPUS));
		assertEquals(
				TEST_SENTENCE_ONE_NUMBER_OF_TOKENS,
				result.get(0).size());
		assertEquals(
				TEST_SENTENCE_TWO_NUMBER_OF_TOKENS,
				result.get(1).size());
		assertEquals(
				TEST_SENTENCE_THREE_NUMBER_OF_TOKENS,
				result.get(2).size());
		
	}
}