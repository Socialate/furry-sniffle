package com.socialteinc.socialate;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loginTestingWithWrongCredentials(){
        onView(withId(R.id.email_field)).perform(typeText("zzz@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());
    }

    @Test
    public void logintesting(){
        onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());

    }

}
