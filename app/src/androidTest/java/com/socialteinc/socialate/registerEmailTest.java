package com.socialteinc.socialate;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class registerEmailTest
{
    @Rule
    public IntentsTestRule<RegisterEmailActivity> mRegisterEmailActivityRule = new IntentsTestRule<>(RegisterEmailActivity.class);

    @Test
    public void CreateAccountSuccess() {
        // Type text and then press the button.
        onView(withId(R.id.loginPasswordEditText)).perform(typeText("Password1234")).perform(closeSoftKeyboard());
        onView(withId(R.id.loginPasswordEditText)).check(matches(withText("Password1234")));
    }
}
