package com.socialteinc.socialate;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Francis on 10/28/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SentenceTest {

    @Test
    public void testSentence() {
        System.out.println("constructorSentence");
        Sentence instance = new Sentence(0,"This is a test sentence.",0, 0);
        assertEquals(instance.noOfWords, 5);
        assertEquals(instance.stringLength, 0);
        assertEquals(instance.score, 0.0);
        assertEquals(instance.number, 0);
        assertEquals(instance.value, "This is a test sentence.");
        assertEquals(instance.paragraphNumber, 0);
    }


}