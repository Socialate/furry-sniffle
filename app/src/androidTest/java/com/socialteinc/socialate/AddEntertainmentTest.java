package com.socialteinc.socialate;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class AddEntertainmentTest {
    private String nameOfPlace;
    private String nameOfOwner;
    private String physicalAddress;
    private String description;
    @Rule
    public ActivityTestRule<AddEntertainmentActivity> rule = new ActivityTestRule<>(AddEntertainmentActivity.class);
    @Before
    public void initValidString() {
        // Specify a valid string.
        nameOfPlace = "Stones";
        //tyuujyjyjy
        nameOfOwner = "Siphe";
        physicalAddress = "Men's Hall of Residence";
        description = "Kuyafiwa la baba!!!";

    }

    @Test
    public void test1() throws InterruptedException{
        Thread.sleep(3000);
        onView(withId(R.id.entertainmentTitleEditText)).check(matches(isClickable()));
        onView(withId(R.id.entertainmentTitleEditText)).perform(typeText(nameOfPlace), pressBack());
    }

    @Test
    public void test2() throws InterruptedException{
        Thread.sleep(3000);
        onView(withId(R.id.chooseImageButton)).check(matches(isClickable()));
       // onView(withId(R.id.chooseImageButton)).perform(click());
    }

    /*@Test
    public void test3() throws InterruptedException{
        Thread.sleep(3000);
        onView(withId(R.id.scrollView)).perform(swipeUp());
    }*/

    @Test
    public void test4() throws InterruptedException{
        Thread.sleep(3000);
        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.NameOfOwnerEditText)).check(matches(isClickable()));
        onView(withId(R.id.NameOfOwnerEditText)).perform(typeText(nameOfOwner), pressBack());
    }

    @Test
    public void test5() throws InterruptedException{
        Thread.sleep(3000);
        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.entertainmentAddressEditText)).check(matches(isClickable()));
        onView(withId(R.id.entertainmentAddressEditText)).perform(typeText(physicalAddress), pressBack());
    }

    @Test
    public void test6() throws InterruptedException{
        Thread.sleep(3000);
        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.entertainmentDescrptionEditText)).check(matches(isClickable()));
        onView(withId(R.id.entertainmentDescrptionEditText)).perform(typeText(description), pressBack());
    }

    @Test
    public void test7() throws InterruptedException{
        Thread.sleep(3000);
        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.addEntertainmentAreaButton)).check(matches(isClickable()));
        onView(withId(R.id.addEntertainmentAreaButton)).perform(click());
    }




}
