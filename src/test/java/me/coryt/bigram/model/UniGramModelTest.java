package me.coryt.bigram.model;

import me.coryt.bigram.model.data.UniGram;
import me.coryt.bigram.util.ResourceReader;
import me.coryt.bigram.util.TextProcessingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

class UniGramModelTest {
	UniGramModel uniGramModel;
	
	List<List<String>> tokens;
	
	@BeforeEach
	void setUp() {
		this.uniGramModel = new UniGramModel();
		tokens = TextProcessingUtil.tokenizeCorpus(BiGramModelTest.CORPUS_DUPLICATE_BIGRAMS);
	}
	
	@DisplayName("Should process single token sentence into unigram")
	@Test
	void testToUniGram_Case1() {
		List<UniGram> result = uniGramModel
				.toUniGram(
						Collections.singletonList("etymology"));
		
		Assertions.assertEquals(1, result.size());
	}
	
	@DisplayName("Should process multi token sentence into unigram list")
	@Test
	void testToUniGram_Case() {
		List<UniGram> result = uniGramModel.toUniGram(tokens.get(0));
		
		Assertions.assertEquals(8, result.size());
	}
	
	@DisplayName("Should process one sentence with one token")
	@Test
	void testProcessTokens_Case1() {
		List<List<UniGram>> result = uniGramModel
				.processTokens(
						Collections.singletonList(
								Collections.singletonList("etymology")));
		
		Assertions.assertEquals(1, uniGramModel.getTotalGrams());
		Assertions.assertEquals(1, result.get(0).get(0).getCount());
	}
	
	@DisplayName("Should process 2 sentences, filter out duplicate grams, and leave the gram 'i' w/ a count of 3")
	@Test
	void testProcessTokens_Case2() {
		List<List<UniGram>> result = uniGramModel
				.processTokens(tokens);
		
		Assertions.assertEquals(16, uniGramModel.getTotalGrams());
		Assertions.assertEquals(11, uniGramModel.getGrams().size());
		Assertions.assertSame(uniGramModel.getGram("i"), result.get(0).get(0));
		Assertions.assertEquals(3, result.get(0).get(0).getCount());
	}
	
	@DisplayName("*****TRAINING DATA***** Should process training data into unigrams")
	@Test
	void testProcessTokens_Case3() throws Exception {
		List<List<UniGram>> result = uniGramModel
				.processTokens(TextProcessingUtil
						.tokenizeCorpus(ResourceReader
								.readFileToString("train.txt")));
		
		Assertions.assertEquals(218382, uniGramModel.getTotalGrams());
		Assertions.assertEquals(16950, uniGramModel.getGrams().size());
		Assertions.assertSame(uniGramModel.getGram("all"), result.get(4).get(16));
		Assertions.assertEquals(1526, result.get(4).get(16).getCount());
	}
	
	@DisplayName("Should normalize unigram counts")
	@Test
	void testNormalizeCounts_Case1() {
		uniGramModel.processTokens(tokens);
		Map<String, UniGram> result = uniGramModel.normalizeCounts(uniGramModel.getGrams(), 16);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(0.1875, result.get("i").getNormalizedCount());
		Assertions.assertEquals(0.0625, result.get("green").getNormalizedCount());
	}
	
	@DisplayName("*****TRAINING DATA***** Should normalize unigram counts for training data")
	@Test
	void testNormalizeCounts_Case2() {
		tokens = TextProcessingUtil.tokenizeCorpus(BiGramModelTest.CORPUS_DUPLICATE_BIGRAMS);
		uniGramModel.processTokens(tokens);
		Map<String, UniGram> result = uniGramModel.normalizeCounts(uniGramModel.getGrams(), 218382);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1.3737395939225761E-5, result.get("i").getNormalizedCount());
		Assertions.assertEquals(4.57913197974192E-6, result.get("green").getNormalizedCount());
	}
}