package com.socialteinc.socialate;

import android.os.Looper;
import android.support.test.espresso.Espresso;
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
        onView(withId(R.id.resetEmailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.resetPasswordButton)).check(matches(isClickable()));
    }

    @Test
    public void test2() throws InterruptedException{
        onView(withId(R.id.resetEmailEditText)).perform(typeText("abcd@gmail.com"));
        Thread.sleep(1000);

    }

    @Test
    public void checkEmailExistsTest() throws InterruptedException{
        onView(withId(R.id.resetEmailEditText)).perform(typeText("musa950820@gmail.com"));
        Thread.sleep(1000);
    }

//    @Test
//    public void test3() throws InterruptedException{
//        Thread.sleep(1000);
//        onView(withId(R.id.resetEmailEditText)).perform(typeText("abcd@gmail.com"), closeSoftKeyboard());
//        onView(withId(R.id.resetPasswordButton)).perform(click());
//        Thread.sleep(9000);
//        //onView(withId(R.id.resetPasswordConstraintLayout)).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void test4() throws InterruptedException{
//        Thread.sleep(1000);
//        onView(withId(R.id.resetEmailEditText)).perform(typeText("musa@gmail.com"), closeSoftKeyboard());
//        onView(withId(R.id.resetPasswordButton)).perform(click());
//        Thread.sleep(9000);
//        //onView(withId(R.id.resetPasswordConstraintLayout)).check(matches(isDisplayed()));
//    }


}
