package com.socialteinc.socialate;

import android.os.Looper;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
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
    @SmallTest
    public void test1() throws InterruptedException{
        onView(withId(R.id.resetEmailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.resetPasswordButton)).check(matches(isClickable()));
    }

    @Test
    @SmallTest
    public void checkEmailExistsTest() throws InterruptedException{
        onView(withId(R.id.resetEmailEditText)).perform(typeText("musa950820@gmail.com"));
    }
}
