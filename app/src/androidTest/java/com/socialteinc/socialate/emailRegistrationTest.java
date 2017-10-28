package com.socialteinc.socialate;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class emailRegistrationTest {

    @Rule
    public ActivityTestRule<RegisterActivity> rule = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void init(){
        rule.getActivity();
    }

    @Test
    public void isDisplayedTest() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.email_registration_button)).check(matches(isDisplayed()));
        onView(withId(R.id.alreadyHaveAccountTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in_textview)).check(matches(isDisplayed()));
        Thread.sleep(2000);

    }

    @Test
    public void test1() throws InterruptedException {
        Thread.sleep(3000);
        onView(withId(R.id.email_registration_button)).perform(click());
        Thread.sleep(3000);
    }

    @Test
    public void test2() throws InterruptedException{
        Thread.sleep(3000);
        onView(withId(R.id.sign_in_textview)).perform(click());
        Thread.sleep(3000);
    }

    @After
    public void stopActivity(){
        rule.finishActivity();
    }

}