package com.socialteinc.socialate;

import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class CheckEmailTest {

    @Rule
    public ActivityTestRule<CheckEmailActivity> rule = new ActivityTestRule<>(CheckEmailActivity.class);

    @Test
    @SmallTest
    public void checkEmailLaunchTest() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()));

        onView(withId(R.id.emailEditText)).perform(typeText("invalid@socialate.com"), closeSoftKeyboard());
    }
}
