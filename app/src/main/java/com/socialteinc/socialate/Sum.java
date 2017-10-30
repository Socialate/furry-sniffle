package com.socialteinc.socialate;

/**
 * Created by Francis on 10/29/2017.
 */
class Sum {
        static String summarize(String description){
            SummaryTool summary = new SummaryTool();
            summary.extractSentenceFromContext(description);
            summary.groupSentencesIntoParagraphs();
            summary.createIntersectionMatrix();
            summary.createSummary();
            return summary.printSummary();
        }
    }

