package com.socialteinc.socialate;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Francis on 10/28/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SentenceComparatorForSummaryTest {
    private final SentenceComparatorForSummary sentence = new SentenceComparatorForSummary();
    @Test
    public void comparator() throws Exception {
        Sentence instance = new Sentence(1,"This is a test sentence.",0, 0);
        Sentence instance1 = new Sentence(2,"This is a test sentence.",0, 0);

        assertEquals(sentence.compare(instance1, instance), 1);
        assertEquals(sentence.compare(instance, instance1), -1);

        instance1.number = 1;
        assertEquals(sentence.compare(instance, instance1), 0);
    }
}