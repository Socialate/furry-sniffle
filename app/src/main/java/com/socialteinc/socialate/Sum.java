package com.socialteinc.socialate;

public class Sum {
	public static String summarize(String description){
		SummaryTool summary = new SummaryTool();
		summary.init();
		summary.extractSentenceFromContext(description);
		summary.groupSentencesIntoParagraphs();
		summary.createIntersectionMatrix();
		summary.createSummary();
		return summary.printSummary();

	}
}