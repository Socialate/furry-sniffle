package com.socialteinc.socialate;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class ResetPasswordTest{

 @Rule
 public ActivityTestRule<ResetPasswordActivity> rule = new ActivityTestRule<>(ResetPasswordActivity.class);

    @Test
    public void test1() throws InterruptedException{
        onView(withId(R.id.resetPasswordButton)).check(matches(isDisplayed()));
        onView(withId(R.id.resetPasswordButton)).check(matches(isClickable()));
    }

    @Test
    public void test2() throws InterruptedException{

        onView(withId(R.id.resetEmailEditText)).perform(typeText("abcd@gmail.com"), pressBack());
        Thread.sleep(1000);
        //onView(withId(R.id.resetPasswordButton)).perform(click());
        //Thread.sleep(9000);
        //onView(withId(R.id.resetPasswordConstraintLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void checkEmailExistsTest() throws InterruptedException{

        onView(withId(R.id.resetEmailEditText)).perform(typeText("musa950820@gmail.com"), pressBack());
        Thread.sleep(1000);
        //onView(withId(R.id.resetPasswordButton)).perform(click());
        //Thread.sleep(9000);
        //onView(withId(R.id.resetPasswordConstraintLayout)).check(matches(isDisplayed()));
        //Thread.sleep(3000);
        //onView(withId(R.id.resetPasswordConstraintLayout)).check(matches(isClickable()));
        //onView(ViewMatchers.withId(R.id.resetPasswordConstraintLayout)).perform(ViewActions.openLinkWithText("LOGIN"));
        //Thread.sleep(3000);
    }

    
}
