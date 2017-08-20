package com.socialteinc.socialate;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class CheckEmailTest
{
    @Rule
    public IntentsTestRule<CheckEmailActivity> mCheckEmailActivityRule = new IntentsTestRule<>(CheckEmailActivity.class);

//    @Test
//    public void EmailSuccess() {
//        // Type text and then press the button.
//        onView(withId(R.id.emailEditText)).perform(typeText("ftmuda@yahoo.com")).perform(closeSoftKeyboard());
//        onView(withId(R.id.nextButton)).perform(click());
//
//        //COMPLETED (1) add intent registerEmailIntent
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // successful email check moves user to next screen
//        intended(hasComponent(new ComponentName(getTargetContext(), RegisterEmailActivity.class)));
//    }
//
//    @Test
//    public void EmailFailed()
//    {
//        // Type text and then press the button.
//        onView(withId(R.id.emailEditText)).perform(typeText("sandile.cyber@gmail.com")).perform(closeSoftKeyboard());
//        onView(withId(R.id.nextButton)).perform(click());
//
//        //COMPLETED(2) add toast message for fail
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //failed check email shows error message
//        onView(withText(R.string.email_taken)).inRoot(withDecorView(not(is(mCheckEmailActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
//    }
}
