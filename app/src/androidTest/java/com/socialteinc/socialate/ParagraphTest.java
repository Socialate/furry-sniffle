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

}