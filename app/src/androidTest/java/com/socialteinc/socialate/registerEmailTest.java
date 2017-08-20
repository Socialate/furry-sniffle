package com.socialteinc.socialate;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class registerEmailTest
{
    @Rule
    public IntentsTestRule<RegisterEmailActivity> mRegisterEmailActivityRule = new IntentsTestRule<>(RegisterEmailActivity.class);

//    @Test
//    public void CreateAccountSuccess() {
//        // Type text and then press the button.
//        onView(withId(R.id.loginPasswordEditText)).perform(typeText("Password1234")).perform(closeSoftKeyboard());
//        onView(withId(R.id.loginPasswordEditText)).check(matches(withText("Password1234")));
//    }
}
