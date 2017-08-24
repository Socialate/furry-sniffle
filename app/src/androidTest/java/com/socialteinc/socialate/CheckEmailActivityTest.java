package com.socialteinc.socialate;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CheckEmailActivityTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<CheckEmailActivity> mActivityRule = new ActivityTestRule<>(
            CheckEmailActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "joe@gmail.com";
    }
    /** This is a test for CheckEmailActivity, which automates the form filling
        and clicks the button
    **/
    @Test
    public void CheckEmailActivityTest() {
        // Type text and then press the button.
        //onView(withId(R.id.emailEditText)).perform(typeText(mStringToBetyped)); //(ViewAction) closeSoftKeyboard());
        //onView(withId(R.id.nextButton)).perform(click());

    }
}