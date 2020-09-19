package me.coryt.bigram.model;

import me.coryt.bigram.model.data.BiGram;
import me.coryt.bigram.util.ApplicationConstants;
import me.coryt.bigram.util.ResourceReader;
import me.coryt.bigram.util.TextProcessingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class BiGramModelTest {
	public static final String CORPUS_DUPLICATE_BIGRAMS = "I do not like green eggs and ham\nI do not like them Sam, I am.";
	private static final String KEY_I_DO = "i, do";
	private static final String KEY_DO_NOT = "do, not";
	private static final String KEY_LIKE_GREEN = "like, green";
	List<List<String>> tokens;
	BiGramModel biGramModel;
	
	UniGramModel uniGramModel = new UniGramModel();
	
	@BeforeEach
	void setup() {
		biGramModel = new BiGramModel();
		uniGramModel = new UniGramModel();
		tokens = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS);
	}
	
	@DisplayName("Should process sentence of length 1 into 2 bi-grams")
	@Test
	void testToBiGram_Case1() {
		List<BiGram> result = biGramModel.toBiGram(tokens.get(1));
		Assertions.assertEquals(2, result.size());
	}
	
	@DisplayName("Should process sentence of length 5 into 6 bi-grams and sentence of length 10 into 11 bi-grams")
	@Test
	void testToBiGram_Case2() {
		List<BiGram> result = biGramModel.toBiGram(tokens.get(0));
		Assertions.assertEquals(6, result.size());
		
		result = biGramModel.toBiGram(tokens.get(2));
		Assertions.assertEquals(11, result.size());
	}
	
	@DisplayName("Should process List of tokenized sentences with no duplicate ")
	@Test
	void testProcessTokens_Case1() {
		Map<String, BiGram> result = biGramModel.processTokens(tokens);
		Assertions.assertEquals(19, result.size());
		Assertions.assertEquals(19, biGramModel.getTotalBiGrams());
	}
	
	@DisplayName("Should process List with duplicate bigrams")
	@Test
	void testProcessTokens_Case2() {
		List<List<String>> testTokens = TextProcessingUtil.tokenizeCorpus(CORPUS_DUPLICATE_BIGRAMS);
		Map<String, BiGram> result = biGramModel.processTokens(testTokens);
		Assertions.assertEquals(14, result.size());
		Assertions.assertEquals(2, result.get(KEY_DO_NOT).getCount());
		Assertions.assertEquals(18, biGramModel.getTotalBiGrams());
	}
	
	@DisplayName("*****TRAINING DATA******: Should process training data into bigrams")
	@Test
	void testProcessTokens_Case3() {
		List<List<String>> testTokens = TextProcessingUtil
				.tokenizeCorpus(
						ResourceReader.readFileToString("train.txt"));
		Map<String, BiGram> result = biGramModel.processTokens(testTokens);
		Assertions.assertEquals(114300, result.size());
		Assertions.assertEquals(39, result.get(KEY_DO_NOT).getCount());
		Assertions.assertEquals(228413, biGramModel.getTotalBiGrams());
		
		result = biGramModel.normalizeCounts(result, 228413);
		Assertions.assertEquals(1.7074334648203035E-4, result.get(KEY_DO_NOT).getNormalizedCount(), 0.05);
	}
	
	@DisplayName("Should process List with duplicate bigrams and store in map field")
	@Test
	void testInitializeMode_Case1() {
		List<List<String>> testTokens = TextProcessingUtil.tokenizeCorpus(CORPUS_DUPLICATE_BIGRAMS);
		biGramModel.initializeModel(testTokens);
		Assertions.assertNotNull(biGramModel.getGrams());
		Assertions.assertFalse(biGramModel.getGrams().isEmpty());
	}
	
	@DisplayName("Should normalize bigram counts")
	@Test
	void testNormalizeCounts() {
		List<List<String>> testTokens = TextProcessingUtil.tokenizeCorpus(CORPUS_DUPLICATE_BIGRAMS);
		Map<String, BiGram> result = biGramModel.normalizeCounts(biGramModel.processTokens(testTokens), 18);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(0.1111111111111111, result.get(KEY_I_DO).getNormalizedCount());
		Assertions.assertEquals(0.05555555555555555, result.get(KEY_LIKE_GREEN).getNormalizedCount());
	}
	
	@DisplayName("Should process List of tokenized sentences with grams linked by sentence ")
	@Test
	void testProcessTestData_Case1() {
		
		List<List<String>> testTokens = TextProcessingUtil.tokenizeCorpus("I do not like green eggs and ham I do not like them Sam, I am.\nI do not like green eggs and ham I do not like them Sam, I am.");
		List<List<BiGram>> result = biGramModel.processTestData(testTokens, uniGramModel.getTestUniGram(testTokens));
		
		Assertions.assertSame(result.get(0).get(0), result.get(1).get(0));
		Assertions.assertEquals(2, result.get(0).get(0).getCount());
		
		Assertions.assertSame(result.get(1).get(2), result.get(1).get(10));
		Assertions.assertEquals(4, result.get(1).get(2).getCount());
	}
	
	@DisplayName("******ON TESTING DATA****** Should process List of tokenized sentences with grams linked by sentence")
	@Test
	void testProcessTestData_Case2() {
		List<List<String>> testTokens = TextProcessingUtil.tokenizeCorpus(ResourceReader.readFileToString("test.txt"));
		List<List<BiGram>> result = biGramModel.processTestData(testTokens, uniGramModel.getTestUniGram(testTokens));
		
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals(11, result.get(0).size());
		Assertions.assertEquals(0.09090909090909091, result.get(0).get(0).getNormalizedCount(), 0.05);
		Assertions.assertEquals(0.08333333333333333, result.get(1).get(0).getNormalizedCount(), 0.05);
	}
	
}