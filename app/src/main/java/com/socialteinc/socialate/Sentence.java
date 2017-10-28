package com.socialteinc.socialate;

class Sentence{
	int stringLength;
	int paragraphNumber;
	int number;
	double score;
	int noOfWords;
	String value;

	Sentence(int number, String value, int stringLength, int paragraphNumber){
		this.number = number;
		this.stringLength = stringLength;
		this.value = new String(value);
		noOfWords = value.split("\\s+").length;
		score = 0.0;
		this.paragraphNumber=paragraphNumber;
	}
}