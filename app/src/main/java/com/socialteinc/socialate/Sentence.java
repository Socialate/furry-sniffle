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
		this.value = value;
		noOfWords = value.split("\\s+").length;
		score = 0.0;
		this.paragraphNumber=paragraphNumber;
	}

	public void setStringLength(int stringLength) {
		this.stringLength = stringLength;
	}

	public void setParagraphNumber(int paragraphNumber) {
		this.paragraphNumber = paragraphNumber;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void setNoOfWords(int noOfWords) {
		this.noOfWords = noOfWords;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getStringLength() {
		return stringLength;
	}

	public int getParagraphNumber() {
		return paragraphNumber;
	}

	public int getNumber() {
		return number;
	}

	public double getScore() {
		return score;
	}

	public int getNoOfWords() {
		return noOfWords;
	}

	public String getValue() {
		return value;
	}
}