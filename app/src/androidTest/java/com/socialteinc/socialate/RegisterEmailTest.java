package com.socialteinc.socialate;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterEmailTest {
    private String mEmail;
    private String mPassword;

    @Rule
    public ActivityTestRule<RegisterEmailActivity> rule = new ActivityTestRule<>(RegisterEmailActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mEmail = "joes@gmail.com";
        mPassword = "sandiles";
    }
    /** This is a test for CheckEmailActivity, which automates the form filling
     and clicks the button
     **/
    @Test
    public void registerEmailLaunchTest() {
        // Type text and then press the button.
        onView(withId(R.id.loginEmailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.loginEmailEditText)).perform(typeText(mEmail));
        onView(withId(R.id.loginEmailEditText)).check(matches((isDisplayed())));
        onView(withId(R.id.loginPasswordEditText)).perform(typeText(mPassword)); //(ViewAction) closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());

    }
}
