package com.socialteinc.socialate;

class Sentence{
	int stringLength;
	int paragraphNumber;
	int number;
	double score;
	int noOfWords;
	String value;
    int vowels = 0;
    int consonants = 0;
    int digits = 0;
    int spaces = 0;

	Sentence(int number, String value, int stringLength, int paragraphNumber){
		this.number = number;
		this.stringLength = stringLength;
		this.value = value;
		noOfWords = value.split("\\s+").length;
		score = 0.0;
		this.paragraphNumber=paragraphNumber;
	}

	public Sentence() {

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

	public void sentenceCount(String sentence) {
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