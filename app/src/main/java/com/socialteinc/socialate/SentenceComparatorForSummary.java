package com.socialteinc.socialate;

import java.util.Comparator;

class SentenceComparatorForSummary  implements Comparator<Sentence>{
	@Override
	public int compare(Sentence sentence, Sentence sentence1) {
		if(sentence.number > sentence1.number){
			return 1;
		}else if(sentence.number < sentence1.number){
			return -1;
		}else{
			return 0;
		}
	}

}