package me.coryt.bigram.model.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.coryt.bigram.util.ApplicationConstants;

@EqualsAndHashCode(callSuper = true)
@Data
public class BiGram extends UniGram {
	@EqualsAndHashCode.Include
	private final String secondValue;
	
	private double probability;
	
	
	/**
	 * A constructor used to create a start/end of sentence word
	 *
	 * @param value      word value
	 * @param firstValue true if the value is the first word in the sentence
	 */
	public BiGram(String value, boolean firstValue) {
		super();
		if (firstValue) {
			this.setValue(ApplicationConstants.START_OF_SENTENCE_WORD);
			this.secondValue = value;
		} else {
			this.setValue(value);
			this.secondValue = ApplicationConstants.END_OF_SENTENCE_WORD;
		}
	}
	
	public BiGram(String firstValue, String secondValue) {
		super();
		this.setValue(firstValue);
		this.secondValue = secondValue;
	}
	
	public void setProbability(double biGramCount, double uniGramCount) {
//		this.probability = Math.log(biGramCount / uniGramCount) / Math.log(Math.E);
		this.probability = biGramCount / uniGramCount;
	}
	
	/**
	 * Set probability for no-smoothing case
	 *
	 * @param biGramCount
	 * @param uniGramCount
	 */
	public void setProbability(int biGramCount, int uniGramCount) {
//		this.probability = Math.log(biGramCount / uniGramCount) / Math.log(Math.E);
		this.probability = (double) biGramCount / (double) uniGramCount;
	}
	
	/**
	 * Set probability for Lapace smoothing case
	 *
	 * @param biGramCount
	 * @param uniGramCount
	 * @param vocabSize
	 */
	public void setProbability(int biGramCount, int uniGramCount, int vocabSize) {
		this.probability = ((double) biGramCount + 1.0) / ((double) uniGramCount + (double) vocabSize);
	}
	
	
	@Override
	public String getKey() {
		return super.getKey() + ", " + secondValue;
	}
}