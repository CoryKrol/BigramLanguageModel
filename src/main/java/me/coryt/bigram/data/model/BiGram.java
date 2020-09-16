package me.coryt.bigram.data.model;

import lombok.Data;

@Data
public class BiGram {
	private String first;
	private String second;
	private int count;
}