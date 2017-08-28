package com.socialteinc.socialate;

import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class CheckEmailActivityTest {

    @Rule
    public ActivityTestRule<CheckEmailActivity> mActivityRule = new ActivityTestRule<>(
            CheckEmailActivity.class);

    /** This is a test for CheckEmailActivity, which automates the form filling
        and clicks the button
    **/
    @Test
    public void InvalidEmailTest() throws InterruptedException {
        onView(withId(R.id.emailEditText)).perform(typeText("invalidemail.com"));
        onView(withId(R.id.nextButton)).perform(click());
    }

    @Test
    public void ValidEmailTest() throws InterruptedException {
        onView(withId(R.id.emailEditText)).perform(typeText("validemail@gmail.com"));
        onView(withId(R.id.nextButton)).perform(click());
    }
}