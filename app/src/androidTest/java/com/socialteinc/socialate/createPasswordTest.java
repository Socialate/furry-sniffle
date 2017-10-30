package com.socialteinc.socialate;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class createPasswordTest {

    @Rule
    public ActivityTestRule<RegisterEmailActivity> rule = new ActivityTestRule<>(RegisterEmailActivity.class);

    @Test
    public void init() {
        rule.getActivity();
    }

    @Test
    public void isDisplayedTest() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.createPasswordTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.loginEmailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.loginPasswordEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.createAccountButton)).check(matches(isDisplayed()));
        Thread.sleep(2000);

    }

    @Test
    public void test1() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.loginEmailEditText)).perform(click());
        onView(withId(R.id.loginEmailEditText)).perform(typeText("joe@gmail.com"));
        //onView(withId(R.id.loginPasswordEditText)).perform(click()).perform(typeText("sandile11"));
        onView(withId(R.id.createAccountButton)).perform(click());
        Thread.sleep(5000);
    }

    @Test
    public void test2() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.loginEmailEditText)).perform(typeText("joe11@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.loginPasswordEditText)).perform(typeText("sandile11"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());
        Thread.sleep(5000);
    }

    @Test
    public void test3() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.loginEmailEditText)).perform(click()).perform(typeText("joe11@gmail.com"));
        onView(withId(R.id.loginPasswordEditText)).perform(click()).perform(typeText("sandile11"));
        onView(withId(R.id.createAccountButton)).perform(click());
        Thread.sleep(5000);
    }

    @After
    public void stopActivity(){
        rule.finishActivity();
    }

}