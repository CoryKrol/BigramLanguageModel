package me.coryt.bigram.model.test;

import me.coryt.bigram.model.data.BiGram;
import me.coryt.bigram.util.TextProcessingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class InputLanguageTest {
	
	private static final String TEST_CORPUS =
			"thus , because no man can follow another into these halls .\n" +
					"upon this the captain started , and eagerly desired to know more .";
	
	private List<List<String>> testCorpus;
	
	private InputLanguage inputLanguage;
	
	@BeforeEach
	void setup() {
		inputLanguage = new InputLanguage();
		testCorpus = TextProcessingUtil.tokenizeCorpus(TEST_CORPUS);
	}
	
	@Test
	void testProcessSentence() {
		List<BiGram> result = inputLanguage.processSentence(testCorpus.get(0));
		Assertions.assertEquals(11, result.size());
	}
}