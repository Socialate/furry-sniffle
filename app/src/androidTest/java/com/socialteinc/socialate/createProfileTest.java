package com.socialteinc.socialate;

import android.content.ComponentName;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;


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

//        //check edit text fields are working correctly
//        onView(withId(R.id.displayNameEditText)).check(matches(withText("Frankman")));
//        onView(withId(R.id.fullNameEditText)).check(matches(withText("Francis Tinashe Mudavanhu")));

        onView(withId(R.id.setupSubmitButton)).perform(click());

        //TODO (5) add intent entertainmentIntent
        // successful createProfile moves user to next screen
       intended(hasComponent(new ComponentName(getTargetContext(), MainActivity.class)));
    }

    @Test
    public void CreateProfileFailed()
    {
        // Type text and then press the button.
        onView(withId(R.id.displayNameEditText)).perform(typeText("FrankMan")).perform(closeSoftKeyboard());
        onView(withId(R.id.fullNameEditText)).perform(typeText("Francis Tinashe Mudavanhu")).perform(closeSoftKeyboard());

        //check edit text fields are working correctly
        onView(withId(R.id.displayNameEditText)).check(matches(withText("Frankman")));
        onView(withId(R.id.fullNameEditText)).check(matches(withText("Francis Tinashe Mudavanhu")));

        onView(withId(R.id.setupSubmitButton)).perform(click());

        //TODO (6) add toast message for fail
        //failed createProfile shows error message
        CreateProfileActivity activity = mCreateProfileActivityRule.getActivity();
        onView(withText("Display name is taken")).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
