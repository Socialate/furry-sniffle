package com.socialteinc.socialate;


import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AddEntertainmentTest {

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLaunchActivity() throws InterruptedException {
        onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());
        Thread.sleep(5000);

        init();
        onView(withId(R.id.action_add_entertainment)).perform(click());
        intended(hasComponent(AddEntertainmentActivity.class.getName()));
        Intents.release();



    }

}
