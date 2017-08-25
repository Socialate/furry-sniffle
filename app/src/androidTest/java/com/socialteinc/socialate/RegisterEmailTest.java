package com.socialteinc.socialate;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
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
        mEmail = "invalid@socialate.com";
        mPassword = "furry";

    }
    /** This is a test for CheckEmailActivity, which automates the form filling
     and clicks the button
     **/

    @Test
    public void registerEmailLaunchTest() {
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, RegisterEmailActivity.class);
        intent.putExtra("signUpEmailAddress", mEmail);
        rule.launchActivity(intent);

        /* Your activity is initialized and ready to go. */
        onView(withId(R.id.loginPasswordEditText)).perform(typeText(mPassword)); //(ViewAction) closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());
    }
}
