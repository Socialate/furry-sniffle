package com.socialteinc.socialate;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ResetPasswordTest{

 @Rule
 public ActivityTestRule<SearchableActivity> rule = new ActivityTestRule<>(SearchableActivity.class);

    @Test
    public void checkEmailExistsTest(){

        onView(withId(R.id.resetEmailEditText)).perform(typeText("valid@socialate.com"), closeSoftKeyboard());
//        onView(withId(R.id.resetPasswordButton)).check(matches(isDisplayed()));
        onView(withId(R.id.resetPasswordButton)).check(matches(isClickable()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        onView(withId(R.id.resetPasswordButton)).perform(click());
    }
}
