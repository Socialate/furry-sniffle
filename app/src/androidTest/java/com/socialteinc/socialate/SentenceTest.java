package com.socialteinc.socialate;

import android.support.test.filters.SmallTest;
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
    @SmallTest
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
    @SmallTest
    public void testSetStringLength() {
        System.out.println("setStringLength");
        int length = 1;
        Sentence instance = new Sentence();
        instance.setStringLength(length);
        assertEquals(instance.getStringLength(), length);
    }

    @Test
    @SmallTest
    public void testGetStringLength() {
        System.out.println("getStringLength");
        Sentence instance = new Sentence();
        int expResult = 1;
        instance.setStringLength(1);
        int result = instance.getStringLength();
        assertEquals(expResult, result);
    }

    @Test
    @SmallTest
    public void testSetParagraphNumber() {
        System.out.println("setParagraphNumber");
        int number = 1;
        Sentence instance = new Sentence();
        instance.setParagraphNumber(number);
        assertEquals(instance.getParagraphNumber(), number);
    }

    @Test
    @SmallTest
    public void testGetPargraphNumber() {
        System.out.println("getParagraphNumber");
        Sentence instance = new Sentence();
        int expResult = 1;
        instance.setParagraphNumber(1);
        int result = instance.getParagraphNumber();
        assertEquals(expResult, result);
    }

    @Test
    @SmallTest
    public void testSetNumber() {
        System.out.println("setNumber");
        int number = 1;
        Sentence instance = new Sentence();
        instance.setNumber(number);
        assertEquals(instance.getNumber(), number);
    }

    @Test
    @SmallTest
    public void testGetNumber() {
        System.out.println("getNumber");
        Sentence instance = new Sentence();
        int expResult = 1;
        instance.setNumber(1);
        int result = instance.getNumber();
        assertEquals(expResult, result);
    }

    @Test
    @SmallTest
    public void testSetScore() {
        System.out.println("setScore");
        double score = 1.0;
        Sentence instance = new Sentence();
        instance.setScore(score);
        assertEquals(instance.getScore(), score);
    }

    @Test
    @SmallTest
    public void testGetScore() {
        System.out.println("getScore");
        Sentence instance = new Sentence();
        double expResult = 1.0;
        instance.setScore(1.0);
        double result = instance.getScore();
        assertEquals(expResult, result);
    }

    @Test
    @SmallTest
    public void testSetNoOfWords() {
        System.out.println("setNoOfWords");
        int number = 1;
        Sentence instance = new Sentence();
        instance.setNoOfWords(number);
        assertEquals(instance.getNoOfWords(), number);
    }

    @Test
    @SmallTest
    public void testGetNoOfWords() {
        System.out.println("getNoOfWords");
        Sentence instance = new Sentence();
        int expResult = 1;
        instance.setNoOfWords(1);
        int result = instance.getNoOfWords();
        assertEquals(expResult, result);
    }

    @Test
    @SmallTest
    public void testSetValue() {
        System.out.println("setValue");
        String name = "Francis";
        Sentence instance = new Sentence();
        instance.setValue(name);
        assertEquals(instance.getValue(), name);
    }

    @Test
    @SmallTest
    public void testGetValue() {
        System.out.println("getValue");
        Sentence instance = new Sentence();
        String expResult = "james";
        instance.setValue("james");
        String result = instance.getValue();
        assertEquals(expResult, result);
    }

    @Test
    @SmallTest
    public void testSentenceCharacter() {
        System.out.println("testSentenceCharacter");
        String name = "This website is aw3som3.";
        Sentence instance = new Sentence();
        instance.sentenceCount(name);
        assertEquals(instance.getVowels(), 7);
        assertEquals(instance.getConsonants(), 11);
        assertEquals(instance.getDigits(), 2);
        assertEquals(instance.getSpaces(), 3);
    }
}