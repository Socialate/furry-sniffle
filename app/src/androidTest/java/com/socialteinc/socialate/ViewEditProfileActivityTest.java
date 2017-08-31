package com.socialteinc.socialate;


import android.net.Uri;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ViewEditProfileActivityTest {
    @Rule
    public ActivityTestRule<ViewEditProfileActivity> rule = new ActivityTestRule<>(ViewEditProfileActivity.class);

    @Test
    public void testViewProfile() {
        /*String User_id = "abcd";
        String displayName = "XP";
        String name =  "Bross";
        String Email = "xp126@gmail.com";
        Uri Picture = Uri.parse("android.resourse://com.socialteinc.socialate/drawable/eventplaceholder.jpg");
        */
        onView(withId(R.id.FullNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.DisplayNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.emailTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.ProfileImageView)).check(matches(isDisplayed()));

        ViewInteraction val = onView(withId(R.id.DisplayNameTextView)).check(matches(isDisplayed()));
        ViewInteraction val2 = onView(withId(R.id.FullNameTextView)).check(matches(isDisplayed()));
        ViewInteraction val3 = onView(withId(R.id.emailTextView)).check(matches(isDisplayed()));
        ViewInteraction val4 = onView(withId(R.id.ProfileImageView)).check(matches(isDisplayed()));

        assertEquals(true,val);
        assertEquals(true,val2);
        assertEquals(true,val3);
        assertEquals(true,val4);
    }


}
