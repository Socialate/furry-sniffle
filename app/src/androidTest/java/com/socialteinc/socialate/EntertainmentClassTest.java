package com.socialteinc.socialate;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EntertainmentClassTest {

    /**
     * Test of constructor Entertainment method, of class Entertainment.
     */
    @Rule
    public ActivityTestRule<AddEntertainmentActivity> addEnt = new ActivityTestRule<>(AddEntertainmentActivity.class);

    @Test
    public void testEntertainment() {
        System.out.println("constructorEntertainment");
        Entertainment instance = new Entertainment(null,null,null,null,null,null,null,null,null,null);
        assertEquals(instance.getName(), null);
        assertEquals(instance.getAddress(), null);
        assertEquals(instance.getDescription(), null);
        assertEquals(instance.getLongitude(), null);
        assertEquals(instance.getLatitude(), null);
        assertEquals(instance.getPhotoUrl(), null);
        assertEquals(instance.getUID(), null);
        assertEquals(instance.getAuthor(), null);
        assertEquals(instance.getEstablishmentCategory(), null);
    }
    /**
     * Test of setOwner method, of class Entertainment.
     */
    @Test
    public void testSetOwner() {
        System.out.println("setOwner");
        String name = "james";
        Entertainment instance = new Entertainment();
        instance.setOwner(name);
        assertEquals(instance.getOwner(), name);
    }

    /**
     * Test of getOwner method, of class Entertainment.
     */
    @Test
    public void testGetOwner() {
        System.out.println("getOwner");
        Entertainment instance = new Entertainment();
        String expResult = "james";
        instance.setOwner("james");
        String result = instance.getOwner();
        assertEquals(expResult, result);
    }
    /**
     * Test of setName method, of class Entertainment.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "Truth";
        Entertainment instance = new Entertainment();
        instance.setName(name);
        assertEquals(instance.getName(), name);
    }

    /**
     * Test of getName method, of class Entertainment.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Entertainment instance = new Entertainment();
        String expResult = "Truth";
        instance.setName("Truth");
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAddress method, of class Entertainment.
     */
    @Test
    public void testSetAddress() {
        System.out.println("setAddress");
        String address = "289 10th Ave, New York, NY 10001, USA";
        Entertainment instance = new Entertainment();
        instance.setAddress(address);
        assertEquals(instance.getAddress(), address);
    }

    /**
     * Test of getAddress method, of class Entertainment.
     */
    @Test
    public void testGetAddress() {
        System.out.println("getAddress");
        Entertainment instance = new Entertainment();
        String expResult = "289 10th Ave, New York, NY 10001, USA";
        instance.setAddress("289 10th Ave, New York, NY 10001, USA");
        String result = instance.getAddress();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescription method, of class Entertainment.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDesciption");
        String description = "Colorful, multi-tiered dance club featuring 30-foot ceilings, LED screens & plenty of night owls.";
        Entertainment instance = new Entertainment();
        instance.setDescription(description);
        assertEquals(instance.getDescription(), description);
    }

    /**
     * Test of getDescription method, of class Entertainment.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Entertainment instance = new Entertainment();
        String expResult = "Colorful, multi-tiered dance club featuring 30-foot ceilings, LED screens & plenty of night owls.";
        instance.setDescription("Colorful, multi-tiered dance club featuring 30-foot ceilings, LED screens & plenty of night owls.");
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLongitude method, of class Entertainment.
     */
    @Test
    public void testSetLongitude() {
        System.out.println("setLongitude");
        String longitude = "28.0481282";
        Entertainment instance = new Entertainment();
        instance.setLongitude(longitude);
        assertEquals(instance.getLongitude(), longitude);
    }

    /**
     * Test of getlongitude method, of class Entertainment.
     */
    @Test
    public void testGetLongitude() {
        System.out.println("getLongitude");
        Entertainment instance = new Entertainment();
        String expResult = "28.0481282";
        instance.setLongitude("28.0481282");
        String result = instance.getLongitude();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLatitude method, of class Entertainment.
     */
    @Test
    public void testSetLatitude() {
        System.out.println("setLatitude");
        String latitude = "-26.1818356";
        Entertainment instance = new Entertainment();
        instance.setLatitude(latitude);
        assertEquals(instance.getLatitude(), latitude);
    }

    /**
     * Test of getLatitude method, of class Entertainment.
     */
    @Test
    public void testGetLatitude() {
        System.out.println("getLongitude");
        Entertainment instance = new Entertainment();
        String expResult = "-26.1818356";
        instance.setLatitude("-26.1818356");
        String result = instance.getLatitude();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPhotoUrl method, of class Entertainment.
     */
    @Test
    public void testSetPhotoUrl() {
        System.out.println("setPhotoUrl");
        String photoUrl = "photo of place";
        Entertainment instance = new Entertainment();
        instance.setPhotoUrl(photoUrl);
        assertEquals(instance.getPhotoUrl(), photoUrl);
    }

    /**
     * Test of getPhotoUrl method, of class Entertainment.
     */
    @Test
    public void testGetPhotoUrl() {
        System.out.println("getPhotoUrl");
        Entertainment instance = new Entertainment();
        String expResult = "photo of plac";
        instance.setPhotoUrl("photo of plac");
        String result = instance.getPhotoUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUID method, of class Entertainment.
     */
    @Test
    public void testSetUID() {
        System.out.println("setUID");
        String UID = "creater UID";
        Entertainment instance = new Entertainment();
        instance.setUID(UID);
        assertEquals(instance.getUID(), UID);
    }

    /**
     * Test of getUID method, of class Entertainment.
     */
    @Test
    public void testGetUID() {
        System.out.println("getUID");
        Entertainment instance = new Entertainment();
        String expResult = "creater UID";
        instance.setUID("creater UID");
        String result = instance.getUID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAuthor method, of class Entertainment.
     */
    @Test
    public void testSetAuthor() {
        System.out.println("setAuthor");
        String author = "author name";
        Entertainment instance = new Entertainment();
        instance.setAuthor(author);
        assertEquals(instance.getAuthor(), author);
    }

    /**
     * Test of getAuthor method, of class Entertainment.
     */
    @Test
    public void testGetAuthor() {
        System.out.println("getAuthor");
        Entertainment instance = new Entertainment();
        String expResult = "author";
        instance.setAuthor("author");
        String result = instance.getAuthor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEstablishmentCategory method, of class Entertainment.
     */
    @Test
    public void testSetEstablishmentCategory() {
        System.out.println("setEstablishmentCategory");
        String category = "club";
        Entertainment instance = new Entertainment();
        instance.setEstablishmentCategory(category);
        assertEquals(instance.getEstablishmentCategory(), category);
    }

    /**
     * Test of getEstablishmentCategory method, of class Entertainment.
     */
    @Test
    public void testGetEstablishmentCategory() {
        System.out.println("getEstablishmentCategory");
        Entertainment instance = new Entertainment();
        String expResult = "club";
        instance.setEstablishmentCategory("club");
        String result = instance.getEstablishmentCategory();
        assertEquals(expResult, result);
    }

}
