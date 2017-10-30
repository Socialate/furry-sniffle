package com.socialteinc.socialate;

import java.util.ArrayList;

class Paragraph{
	int number;
	ArrayList<Sentence> sentences;

	Paragraph(int number){
		this.number = number;
		sentences = new ArrayList<>();
	}

	public Paragraph() {

	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setSentences(ArrayList<Sentence> sentences) {
		this.sentences = sentences;
	}

	public int getNumber() {
		return number;
	}

	public ArrayList<Sentence> getSentences() {
		return sentences;
	}
}