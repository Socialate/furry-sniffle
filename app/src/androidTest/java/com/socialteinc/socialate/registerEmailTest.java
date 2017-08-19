package com.socialteinc.socialate;

import android.content.ComponentName;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class registerEmailTest
{
    @Rule
    public IntentsTestRule<RegisterEmailActivity> mRegisterEmailActivityRule = new IntentsTestRule<>(RegisterEmailActivity.class);

    @Test
    public void CreateAccountSuccess() {
        // Type text and then press the button.
        onView(withId(R.id.loginPasswordEditText)).perform(typeText("Password1234")).perform(closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());

        //TODO (3) add intent createProfileIntent
        // successful createAccount moves user to next screen
       intended(hasComponent(new ComponentName(getTargetContext(), CreateProfileActivity.class)));
    }

    @Test
    public void CreateAccountFailed()
    {
        // Type text and then press the button.
        onView(withId(R.id.loginPasswordEditText)).perform(typeText("Password1234"));
        onView(withId(R.id.createAccountButton)).perform(click());

        //TODO (4) add toast message for fail
        //failed createAccount shows error message
        RegisterEmailActivity activity = mRegisterEmailActivityRule.getActivity();
        onView(withText(R.string.password_short)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
