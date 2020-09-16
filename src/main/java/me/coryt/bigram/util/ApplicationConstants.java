package me.coryt.bigram.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstants {
	public final String[] TEST_SENTENCES = {
			"thus , because no man can follow another into these halls .",
			"upon this the captain started , and eagerly desired to know more ."
	};
	
	
	// Regexes
	public final String NEWLINE_REGEX = "\\n";
	public final String WHITESPACE_REGEX = "\\s+";
	public final String NON_WORD_CHARACTERS_REGEX = "([^a-zA-z\\s]|\\[|])+";
	
	public final String EMPTY_STRING = "";
	
	public final String CLASSPATH_STRING = "classpath:";
}
