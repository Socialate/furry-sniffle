package com.socialteinc.socialate;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class createProfileTest
{
    @Rule
    public IntentsTestRule<CreateProfileActivity> mCreateProfileActivityRule = new IntentsTestRule<>(CreateProfileActivity.class);

    @Test
    public void CreateProfileSuccess() {
        // Type text and then press the button.
        onView(withId(R.id.displayNameEditText)).perform(typeText("FrankMan")).perform(closeSoftKeyboard());
        onView(withId(R.id.fullNameEditText)).perform(typeText("Francis Tinashe Mudavanhu")).perform(closeSoftKeyboard());

        //check edit text fields are working correctly
        onView(withId(R.id.displayNameEditText)).check(matches(withText("FrankMan")));
        onView(withId(R.id.fullNameEditText)).check(matches(withText("Francis Tinashe Mudavanhu")));
    }
}
