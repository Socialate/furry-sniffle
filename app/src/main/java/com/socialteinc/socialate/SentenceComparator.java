package com.socialteinc.socialate;

import java.util.Comparator;

public class SentenceComparator  implements Comparator<Sentence>{
	@Override
	public int compare(Sentence sentence, Sentence sentence1) {
		if(sentence.score > sentence1.score){
			return -1;
		}else if(sentence.score < sentence1.score){
			return 1;
		}else{
			return 0;
		}
	}
}