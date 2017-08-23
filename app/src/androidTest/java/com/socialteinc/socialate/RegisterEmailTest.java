package com.socialteinc.socialate;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterEmailTest {

    @Rule
    public ActivityTestRule<RegisterEmailActivity> rule = new ActivityTestRule<>(RegisterEmailActivity.class);

    @Test
    public void registerEmailLaunchTest(){

        onView(withId(R.id.loginEmailEditText)).check(matches(isDisplayed()));
    }
}
