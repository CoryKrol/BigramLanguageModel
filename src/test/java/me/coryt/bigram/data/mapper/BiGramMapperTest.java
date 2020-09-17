package me.coryt.bigram.data.mapper;

import me.coryt.bigram.data.model.BiGram;
import me.coryt.bigram.util.ApplicationConstants;
import me.coryt.bigram.util.TextProcessingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class BiGramMapperTest {
	List<List<String>> tokens;
	BiGramMapper biGramMapper;
	
	@BeforeEach
	void setup() {
		biGramMapper = new BiGramMapper();
		tokens = TextProcessingUtil.tokenizeCorpus(ApplicationConstants.TEST_CORPUS);
	}
	
	@DisplayName("Should process sentence of length 1 into 2 bi-grams")
	@Test
	void testToBiGram_Case1() {
		List<BiGram> result = biGramMapper.toBiGram(tokens.get(1));
		Assertions.assertEquals(2, result.size());
	}
	
	@DisplayName("Should process sentence of length 5 into 6 bi-grams")
	@Test
	void testToBiGram_Case2() {
		List<BiGram> result = biGramMapper.toBiGram(tokens.get(0));
		Assertions.assertEquals(6, result.size());
		
		result = biGramMapper.toBiGram(tokens.get(2));
		Assertions.assertEquals(11, result.size());
	}
}