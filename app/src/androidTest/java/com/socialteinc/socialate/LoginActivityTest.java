package com.socialteinc.socialate;


import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void noValidPasswordTest() throws InterruptedException{
        onView(withId(R.id.email_field)).perform(typeText("invalid@socialate.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("furry"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(clearText());
        onView(withId(R.id.SinginButton)).perform(click());
    }

    @Test
    public void loginTesting() throws InterruptedException {
        onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
        onView(withId(R.id.SinginButton)).perform(click());
    }

//    @Test
//    public void signUpTesting() throws InterruptedException{
//        Thread.sleep(4000);
//        Intents.init();
//        onView(withId(R.id.creatAccountTextView)).check(matches(isClickable()));
//        onView(withId(R.id.creatAccountTextView)).perform(click());
//        //onView(withText("Need an account?")).perform(click());
//        intended(hasComponent(CheckEmailActivity.class.getName()));
//        Intents.release();
//
//    }

    @Test
    public void resetPasswordTesting(){
        Intents.init();
        onView(withId(R.id.resetPasswordTextView)).perform(click());
        intended(hasComponent(ResetPasswordActivity.class.getName()));
        Intents.release();

    }

    @Test
    public void isDisplayedTest() throws  InterruptedException{
        onView(withId(R.id.appNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.facebookButton)).check(matches(isDisplayed()));
        onView(withId(R.id.googleButton)).check(matches(isDisplayed()));
        onView(withId(R.id.creatAccountTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.resetPasswordTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.orTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.SinginButton)).check(matches(isDisplayed()));

    }

}
