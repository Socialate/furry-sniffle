package com.socialteinc.socialate;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Francis on 10/28/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ParagraphTest {

    @Test
    public void testParagraph() {
        System.out.println("constructorParagraph");
        Paragraph instance = new Paragraph(0);
        assertEquals(instance.number, 0);
        ArrayList<Sentence> sentencesTest = new ArrayList<>();
        assertEquals(instance.sentences,sentencesTest);
    }

    @Test
    public void testSetNumber() {
        System.out.println("setNumber");
        int number = 1;
        Paragraph instance = new Paragraph();
        instance.setNumber(number);
        assertEquals(instance.getNumber(), number);
    }


    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        Paragraph instance = new Paragraph();
        int expResult = 1;
        instance.setNumber(1);
        int result = instance.getNumber();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetSentences() {
        System.out.println("setSentences");
        ArrayList<Sentence> init = null;
        Paragraph instance = new Paragraph();
        instance.setSentences(init);
        assertEquals(instance.getSentences(), init);
    }

    @Test
    public void testGetSentences() {
        System.out.println("getSentences");
        Paragraph instance = new Paragraph();
        ArrayList<Sentence> expResult = null;
        instance.setSentences(null);
        ArrayList<Sentence> result = instance.getSentences();
        assertEquals(expResult, result);
    }

    @Test
    public void testParagraphCharacter() {
        System.out.println("testSentenceCharacter");
        String name = "This website is aw3som3.";
        Paragraph instance = new Paragraph();
        instance.paragraphCount(name);
        assertEquals(instance.getVowels(), 7);
        assertEquals(instance.getConsonants(), 11);
        assertEquals(instance.getDigits(), 2);
        assertEquals(instance.getSpaces(), 3);
    }
}