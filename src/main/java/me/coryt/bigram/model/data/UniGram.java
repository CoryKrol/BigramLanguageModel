package me.coryt.bigram.model.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class UniGram {
	private int count = 0;
	private double normalizedCount = 0.0;
	
	@EqualsAndHashCode.Include
	private String value;
	
	public UniGram(String value) {
		this.value = value;
	}
	
	public void incrementCount() {
		this.count++;
	}
	
	public void setNormalizedCount(int totalBiGrams) {
		this.normalizedCount = (double) this.getCount() / (double) totalBiGrams;
	}
	
	public String getKey() {
		return this.value;
	}
}
