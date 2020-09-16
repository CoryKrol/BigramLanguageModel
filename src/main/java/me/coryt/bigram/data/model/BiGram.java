package me.coryt.bigram.data.model;

import lombok.Data;
import me.coryt.bigram.util.ApplicationConstants;

@Data
public class BiGram {
	private final String firstValue;
	private final String secondValue;
	private int count;
	private double probability;
	
	/**
	 * A constructor used to create a start/end of sentence word
	 *
	 * @param value      word value
	 * @param firstValue true if the value is the first word in the sentence
	 */
	public BiGram(String value, boolean firstValue) {
		if (firstValue) {
			this.firstValue = ApplicationConstants.START_OF_SENTENCE_WORD;
			this.secondValue = value;
		} else {
			this.firstValue = value;
			this.secondValue = ApplicationConstants.END_OF_SENTENCE_WORD;
		}
	}
}