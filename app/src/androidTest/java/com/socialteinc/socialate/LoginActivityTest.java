package com.socialteinc.socialate;


import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void noValidEmailTest() throws InterruptedException {
        onView(withId(R.id.email_field)).perform(typeText("invalidsocialate.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("furry"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());
    }

    @Test
    public void noValidPasswordTest(){
        onView(withId(R.id.email_field)).perform(typeText("invalid@socialate.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("furry"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(clearText());
        onView(withId(R.id.SinginButton)).perform(click());
    }

    @Test
    public void logintesting() throws InterruptedException {
        Intents.init();
        onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());
        Thread.sleep(10000);
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();

    }

    @Test
    public void signUptesting(){
        Intents.init();
        onView(withId(R.id.creatAccountTextView)).perform(click());
        intended(hasComponent(CheckEmailActivity.class.getName()));
        Intents.release();

    }

}
