package me.coryt.bigram.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.coryt.bigram.util.TextProcessingUtil.splitCorpus;
import static me.coryt.bigram.util.TextProcessingUtil.splitSentences;

class TextProcessingUtilTest {
	private static final String TEST_CORPUS = "[ moby dick by herman melville 1851 ]\n" +
			"etymology .\n" +
			"( supplied by a late consumptive usher to a grammar school )";
	private static final int TEST_CORPUS_NUM_SENTENCES = 3;
	
	private static final int TEST_SENTENCE_ONE_NUMBER_OF_TOKENS = 8;
	private static final int TEST_SENTENCE_TWO_NUMBER_OF_TOKENS = 2;
	private static final int TEST_SENTENCE_THREE_NUMBER_OF_TOKENS = 12;
	
	
	@DisplayName("Should split test corpus into ArrayList of size 3")
	@Test
	void testSplitCorpus() {
		List<String> result = splitCorpus(TEST_CORPUS);
		Assertions.assertEquals(TEST_CORPUS_NUM_SENTENCES, result.size());
	}
	
	@DisplayName("Should split test corpus into ArrayList of size 3 containing token array lists")
	@Test
	void testTokenizeWords() {
		List<List<String>> result = splitSentences(splitCorpus(TEST_CORPUS));
		
		Assertions.assertEquals(TEST_CORPUS_NUM_SENTENCES, result.size());
		Assertions.assertEquals(TEST_SENTENCE_ONE_NUMBER_OF_TOKENS, result.get(0).size());
		Assertions.assertEquals(TEST_SENTENCE_TWO_NUMBER_OF_TOKENS, result.get(1).size());
		Assertions.assertEquals(TEST_SENTENCE_THREE_NUMBER_OF_TOKENS, result.get(2).size());
		
	}
}