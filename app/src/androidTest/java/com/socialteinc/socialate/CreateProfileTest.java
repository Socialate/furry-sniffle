package com.socialteinc.socialate;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateProfileTest {

    private String displayName;
    private String fullName;

    @Rule
    public ActivityTestRule<CreateProfileActivity> rule = new ActivityTestRule<>(CreateProfileActivity.class);

    @Before
    public void initValideString(){
        displayName = "Musa";
        fullName = "Musa Rikhotso";
    }

    @Test
    public void createProfileLaunchTest(){

       /* onView(withId(R.id.accountSetupTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.displayNameEditText)).perform(typeText(displayName));
        onView(withId(R.id.fullNameEditText)).perform(typeText(fullName));
        //onView(ViewMatchers.withId(R.id.setupPictureButton)).perform(ViewActions.openLinkWithText("choose_profile_picture")); // select picture
        //onView(withId(R.id.setupSubmitButton)).perform(click());
        */
    }
}
