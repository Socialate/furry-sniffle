package com.socialteinc.socialate;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.firebase.database.DatabaseReference.goOffline;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ViewEditProfileActivityTest {
    @Rule
    public ActivityTestRule<ViewEditProfileActivity> rule = new ActivityTestRule<ViewEditProfileActivity>(ViewEditProfileActivity.class){
        @Override
        protected Intent getActivityIntent() {
            //goOffline();
            getMock();
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, ViewEditProfileActivity.class);
            String userKey = "B7TbLOcLXggRL1TyQxrgrGlwMiO2";
            String userName = "Joe Manga";
            result.putExtra("userName", userName);
            result.putExtra("userKey", userKey);
            return result;
        }
    };

    FirebaseAuth mAuth;

    public FirebaseAuth getMock() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("joe@gmail.com","sandile");
        return mAuth;
    }

    @Test
    public void onCreateTest() throws InterruptedException {
        Thread.sleep(5000);
    }

    /*@Test
    public void testViewProfile() {
        goOffline();
        //onView(withId(R.id.action_view_edit_profile)).perform(click());
        onView(withId(R.id.FullNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.DisplayNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.emailTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.ProfileImageView)).check(matches(isDisplayed()));
    }*/

    /*
    @Test
    public void testViewLogic(){
        getMock();
        String val = onView(withId(R.id.DisplayNameTextView)).check(matches(isDisplayed())).toString();
        String val2 = onView(withId(R.id.FullNameTextView)).check(matches(isDisplayed())).toString();
        String val3 = onView(withId(R.id.emailTextView)).check(matches(isDisplayed())).toString();
        //String val4 = onView(withId(R.id.ProfileImageView)).check(matches(isDisplayed())).toString();

        assertEquals(true,val);
        assertEquals(true,val2);
        assertEquals(true,val3);
        //assertEquals(true,val4);
    }*/


}
