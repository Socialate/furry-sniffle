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
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CheckEmailActivityTest {

    @Rule
    public ActivityTestRule<CheckEmailActivity> mActivityRule = new ActivityTestRule<>(
            CheckEmailActivity.class);

    /**
     * This is a test for Checking if the username has already being taken or not,
     * if username doesn't exist, you'll be allowed to create a password
    **/

    @Test
    public void EmailTest(){

        assertEquals(false, CheckEmailActivity.isValidEmail("mmmmm"));
        assertEquals(false, CheckEmailActivity.isValidEmail("abcd.com"));
        assertEquals(true, CheckEmailActivity.isValidEmail("socialate@gmail.com"));
        assertEquals(true, CheckEmailActivity.isValidEmail("1234@gmail.com"));
    }

    @Test
    public void InvalidEmailTest() throws InterruptedException {
        onView(withId(R.id.emailEditText)).perform(typeText("invalidemail.com"));
        onView(withId(R.id.nextButton)).perform(click());
    }

    @Test
    public void ValidEmailTest(){
        onView(withId(R.id.emailEditText)).perform(typeText("musar@gmail.com"));
        onView(withId(R.id.nextButton)).perform(click());
    }
}