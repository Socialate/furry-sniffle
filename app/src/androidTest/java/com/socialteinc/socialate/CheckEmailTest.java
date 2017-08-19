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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class CheckEmailTest
{
    @Rule
    public IntentsTestRule<CheckEmailActivity> mCheckEmailActivityRule = new IntentsTestRule<>(CheckEmailActivity.class);

    @Test
    public void EmailSuccess() {
        // Type text and then press the button.
        onView(withId(R.id.emailEditText)).perform(typeText("ftmuda@yahoo.com"));
        onView(withId(R.id.nextButton)).perform(click());

        //TODO (1) add intent registerEmailIntent
        // successful login moves user to next screen
       intended(hasComponent(new ComponentName(getTargetContext(), RegisterEmailActivity.class)));
    }

    @Test
    public void EmailFailed()
    {
        // Type text and then press the button.
        onView(withId(R.id.emailEditText)).perform(typeText("ftmuda@yahoo.com"));
        onView(withId(R.id.nextButton)).perform(click());

        //TODO (2) add toast message for fail
        //failed login shows error message
        CheckEmailActivity activity = mCheckEmailActivityRule.getActivity();
        onView(withText(R.string.email_taken)).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
