package com.socialteinc.socialate;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class CommentsClassTest {
    /**
     * Test of constructor Entertainment method, of class Entertainment.
     */

    @Test
    public void testEntertainment() {
        Comments instance = new Comments(null, null, null, null, null, null);
        assertEquals(instance.getTimestamp(), null);
        assertEquals(instance.getEntertainmentUID(), null);
        assertEquals(instance.getComment(), null);
        assertEquals(instance.getPhotoUrl(), null);
        assertEquals(instance.getUID(), null);
        assertEquals(instance.getAuthor(), null);
    }
    /**
     * Test of setComment method, of class Comments.
     */
    @Test
    public void testSetComment() {
        String comment = "I like this place";
        Comments instance = new Comments();
        instance.setComment(comment);
        assertEquals(instance.getComment(), comment);
    }

    /**
     * Test of getComment method, of class Comments.
     */
    @Test
    public void testGetComment() {
        Comments instance = new Comments();
        String expResult = "I like this place";
        instance.setComment("I like this place");
        String result = instance.getComment();
        assertEquals(expResult, result);
    }
    /**
     * Test of setTimestamp method, of class Comments.
     */
    @Test
    public void testSetTimestamp() {
        String time = "2017-08-20 14:30";
        Comments instance = new Comments();
        instance.setTimestamp(time);
        assertEquals(instance.getTimestamp(), time);
    }

    /**
     * Test of getTimestamp method, of class Comments.
     */
    @Test
    public void testGetTimestamp() {
        Comments instance = new Comments();
        String expResult = "2017-08-20 14:30";
        instance.setTimestamp("2017-08-20 14:30");
        String result = instance.getTimestamp();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEntertainmentUID method, of class Comment.
     */
    @Test
    public void testSetEntertainmentUID() {
        String eUID = "-KuWs0YVBB_nWlC03LrE";
        Comments instance = new Comments();
        instance.setEntertainmentUID(eUID);
        assertEquals(instance.getEntertainmentUID(), eUID);
    }

    /**
     * Test of getEntertainmentUID method, of class Comments.
     */
    @Test
    public void testGetEntertainmentUID() {
        Comments instance = new Comments();
        String expResult = "-KuWs0YVBB_nWlC03LrE";
        instance.setEntertainmentUID("-KuWs0YVBB_nWlC03LrE");
        String result = instance.getEntertainmentUID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPhotoUrl method, of class Comments.
     */
    @Test
    public void testSetPhotoUrl() {
        String photoUrl = "photo of author";
        Comments instance = new Comments();
        instance.setPhotoUrl(photoUrl);
        assertEquals(instance.getPhotoUrl(), photoUrl);
    }

    /**
     * Test of getPhotoUrl method, of class Comments.
     */
    @Test
    public void testGetPhotoUrl() {
        Comments instance = new Comments();
        String expResult = "photo of author";
        instance.setPhotoUrl("photo of author");
        String result = instance.getPhotoUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUID method, of class Comments.
     */
    @Test
    public void testSetUID() {
        String UID = "commenter UID";
        Comments instance = new Comments();
        instance.setUID(UID);
        assertEquals(instance.getUID(), UID);
    }

    /**
     * Test of getUID method, of class Comments.
     */
    @Test
    public void testGetUID() {
        Comments instance = new Comments();
        String expResult = "commenter UID";
        instance.setUID("commenter UID");
        String result = instance.getUID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAuthor method, of class Comments.
     */
    @Test
    public void testSetAuthor() {
        String author = "author name";
        Comments instance = new Comments();
        instance.setAuthor(author);
        assertEquals(instance.getAuthor(), author);
    }

    /**
     * Test of getAuthor method, of class Comments.
     */
    @Test
    public void testGetAuthor() {
        Comments instance = new Comments();
        String expResult = "author";
        instance.setAuthor("author");
        String result = instance.getAuthor();
        assertEquals(expResult, result);
    }

}
