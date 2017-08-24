package com.socialteinc.socialate;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginTestingWithWrongCredentials(){
        onView(withId(R.id.email_field)).perform(typeText("invalid@socialate.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("furry"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());
        onView(withText("Logging In")).check(matches(isDisplayed()));
    }


    @Test
    public void noValidEmailTest() throws InterruptedException {
        onView(withId(R.id.email_field)).perform(typeText("invalidsocialate.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("furry"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());

        Thread.sleep(3000);
        onView(withText("Authentication failed. Please enter valid username/password")).inRoot(withDecorView(not(is(main.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @Test
    public void logintesting(){
        onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());

    }

}
