package com.socialteinc.socialate;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;

@RunWith(AndroidJUnit4.class)

public class VEPtest {

    @Rule
    public ActivityTestRule<ViewEditProfileActivity> main = new ActivityTestRule<>(ViewEditProfileActivity.class);

    @Test
    public void UiTest(){
        onView(withId(R.id.textInputLayout5)).check(matches(isDisplayed()));
        onView(withId(R.id.fullNameEditText)).perform(ViewActions.typeText("Furry Sniffle"), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));

        onView(withId(R.id.textInputLayout6)).check(matches(isDisplayed()));
        onView(withId(R.id.displayNameEditText)).perform(ViewActions.typeText("Socialate"), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));

        onView(withId(R.id.textInputLayout7)).check(matches(isDisplayed()));
        onView(withId(R.id.describeEditText)).perform(ViewActions.typeText("I'm a geolocation service"), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));


    }


}
