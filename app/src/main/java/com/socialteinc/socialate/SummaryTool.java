package com.socialteinc.socialate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;


class SummaryTool{
	ArrayList<Sentence> sentences, contentSummary;
	ArrayList<Paragraph> paragraphs;
	private int noOfSentences, noOfParagraphs;
	private double[][] intersectionMatrix;
	LinkedHashMap<Sentence,Double> dictionary;


	SummaryTool(){
		noOfSentences = 0;
		noOfParagraphs = 0;
	}

	void init(){
		sentences = new ArrayList<Sentence>();
		paragraphs = new ArrayList<Paragraph>();
		contentSummary = new ArrayList<Sentence>();
		dictionary = new LinkedHashMap<Sentence,Double>();
		noOfSentences = 0;
		noOfParagraphs = 0;
	}

	/*Gets the sentences from the entire passage*/
	void extractSentenceFromContext(String description){

		String[] paragraphs = description.split("(?m)^--$\\R?");
		noOfParagraphs = paragraphs.length;
		for(int i = 0; i < noOfParagraphs; i++){
			String [] sentence = paragraphs[0].split("\\.|\\?|\\!");
				for (int j = 0; j < sentence.length; j++){
					sentences.add(new Sentence(noOfSentences,sentence[j],sentence[j].length(),i));
					noOfSentences++;

				}


		}
	}

	void groupSentencesIntoParagraphs(){
		int paraNum = 0;
		Paragraph paragraph = new Paragraph(0);

		for(int i=0;i<noOfSentences;i++){
			if(sentences.get(i).paragraphNumber == paraNum){
				//continue
			}else{
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
		intersectionMatrix = new double[noOfSentences][noOfSentences];
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

	void createDictionary(){
		for(int i=0;i<noOfSentences;i++){
			double score = 0;
			for(int j=0;j<noOfSentences;j++){
				score+=intersectionMatrix[i][j];
			}
			dictionary.put(sentences.get(i), score);
			sentences.get(i).score = score;
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
		String Summary = null;
		for(Sentence sentence : contentSummary){
			Summary = Summary+ " "+ sentence+".";
		}
		return Summary;
	}
}