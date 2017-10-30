package com.socialteinc.socialate;

import java.util.ArrayList;

class Paragraph{
	int number;
	ArrayList<Sentence> sentences;
	int vowels = 0;
	int consonants = 0;
	int digits = 0;
	int spaces = 0;

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

	public int getVowels() {
		return vowels;
	}

	public void setVowels(int vowels) {
		this.vowels = vowels;
	}

	public int getConsonants() {
		return consonants;
	}

	public void setConsonants(int consonants) {
		this.consonants = consonants;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public int getSpaces() {
		return spaces;
	}

	public void setSpaces(int spaces) {
		this.spaces = spaces;
	}


	public void paragraphCount(String sentence) {
		setConsonants(0);
		setDigits(0);
		setVowels(0);
		setSpaces(0);
		String line = sentence;
		line = line.toLowerCase();
		for(int i = 0; i < line.length(); ++i)
		{
			char ch = line.charAt(i);
			if(ch == 'a' || ch == 'e' || ch == 'i'
					|| ch == 'o' || ch == 'u') {
				++vowels;
			}
			else if((ch >= 'a'&& ch <= 'z')) {
				++consonants;
			}
			else if( ch >= '0' && ch <= '9')
			{
				++digits;
			}
			else if (ch ==' ')
			{
				++spaces;
			}
		}
		getConsonants();
		getDigits();
		getVowels();
		getSpaces();
	}
}