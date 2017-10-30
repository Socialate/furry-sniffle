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

    @Test
    public void testSetStringLength() {
        System.out.println("setStringLength");
        int length = 1;
        Sentence instance = new Sentence();
        instance.setStringLength(length);
        assertEquals(instance.getStringLength(), length);
    }

    @Test
    public void testGetStringLength() {
        System.out.println("getStringLength");
        Sentence instance = new Sentence();
        int expResult = 1;
        instance.setStringLength(1);
        int result = instance.getStringLength();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetParagraphNumber() {
        System.out.println("setParagraphNumber");
        int number = 1;
        Sentence instance = new Sentence();
        instance.setParagraphNumber(number);
        assertEquals(instance.getParagraphNumber(), number);
    }

    @Test
    public void testGetPargraphNumber() {
        System.out.println("getParagraphNumber");
        Sentence instance = new Sentence();
        int expResult = 1;
        instance.setParagraphNumber(1);
        int result = instance.getParagraphNumber();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetNumber() {
        System.out.println("setNumber");
        int number = 1;
        Sentence instance = new Sentence();
        instance.setNumber(number);
        assertEquals(instance.getNumber(), number);
    }

    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        Sentence instance = new Sentence();
        int expResult = 1;
        instance.setNumber(1);
        int result = instance.getNumber();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetScore() {
        System.out.println("setScore");
        double score = 1.0;
        Sentence instance = new Sentence();
        instance.setScore(score);
        assertEquals(instance.getScore(), score);
    }

    @Test
    public void testGetScore() {
        System.out.println("getScore");
        Sentence instance = new Sentence();
        double expResult = 1.0;
        instance.setScore(1.0);
        double result = instance.getScore();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetNoOfWords() {
        System.out.println("setNoOfWords");
        int number = 1;
        Sentence instance = new Sentence();
        instance.setNoOfWords(number);
        assertEquals(instance.getNoOfWords(), number);
    }

    @Test
    public void testGetNoOfWords() {
        System.out.println("getNoOfWords");
        Sentence instance = new Sentence();
        int expResult = 1;
        instance.setNoOfWords(1);
        int result = instance.getNoOfWords();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetValue() {
        System.out.println("setValue");
        String name = "Francis";
        Sentence instance = new Sentence();
        instance.setValue(name);
        assertEquals(instance.getValue(), name);
    }

    @Test
    public void testGetValue() {
        System.out.println("getValue");
        Sentence instance = new Sentence();
        String expResult = "james";
        instance.setValue("james");
        String result = instance.getValue();
        assertEquals(expResult, result);
    }
}