package com.socialteinc.socialate;

import java.util.ArrayList;
import java.util.Collections;


public class SummaryTool{
	private ArrayList<Sentence> sentences, contentSummary;
	private ArrayList<Paragraph> paragraphs;
	private int noOfSentences, noOfParagraphs;

	SummaryTool(){
		noOfSentences = 0;
		noOfParagraphs = 0;
	}

	void init(){
		sentences = new ArrayList<>();
		paragraphs = new ArrayList<>();
		contentSummary = new ArrayList<>();
		noOfSentences = 0;
		noOfParagraphs = 0;
	}

	/*Gets the sentences from the entire passage*/
	void extractSentenceFromContext(String description){

		String[] paragraphs = description.split("(?m)^--$\\R?");
		noOfParagraphs = paragraphs.length;
		for(int i = 0; i < noOfParagraphs; i++){
			String [] sentence = paragraphs[0].split("\\.|\\?|!");
			for (String aSentence : sentence) {
				sentences.add(new Sentence(noOfSentences, aSentence, aSentence.length(), i));
				noOfSentences++;

			}


		}
	}

	void groupSentencesIntoParagraphs(){
		int paraNum = 0;
		Paragraph paragraph = new Paragraph(0);

		for(int i=0;i<noOfSentences;i++){
			if (sentences.get(i).paragraphNumber != paraNum) {
				paragraphs.add(paragraph);
				paraNum++;
				paragraph = new Paragraph(paraNum);

			}
			paragraph.sentences.add(sentences.get(i));
		}

		paragraphs.add(paragraph);
	}

	private double noOfCommonWords(Sentence str1, Sentence str2){
		double commonCount = 0;

		for(String str1Word : str1.value.split("\\s+")){
			for(String str2Word : str2.value.split("\\s+")){
				if(str1Word.compareToIgnoreCase(str2Word) == 0){
					commonCount++;
				}
			}
		}

		return commonCount;
	}

	void createIntersectionMatrix(){
		double[][] intersectionMatrix = new double[noOfSentences][noOfSentences];
		for(int i=0;i<noOfSentences;i++){
			for(int j=0;j<noOfSentences;j++){

				if(i<=j){
					Sentence str1 = sentences.get(i);
					Sentence str2 = sentences.get(j);
					intersectionMatrix[i][j] = noOfCommonWords(str1,str2) / ((double)(str1.noOfWords + str2.noOfWords) /2);
				}else{
					intersectionMatrix[i][j] = intersectionMatrix[j][i];
				}
				
			}
		}
	}

	void createSummary(){

	      for(int j=0;j<noOfParagraphs;j++){
	      		int primary_set = paragraphs.get(j).sentences.size()/5;

	      		//Sort based on score (importance)
	      		Collections.sort(paragraphs.get(j).sentences,new SentenceComparator());
		      	for(int i=0;i<=primary_set;i++){
		      		contentSummary.add(paragraphs.get(j).sentences.get(i));
		      	}
	      }

	      //To ensure proper ordering
	      Collections.sort(contentSummary,new SentenceComparatorForSummary());
		
	}

	String printSummary(){
		String Summary = "";
		for(Sentence sentence : contentSummary){
			Summary = Summary+ " "+ sentence.value+".";
		}
		return Summary;
	}
}