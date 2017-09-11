package com.socialteinc.socialate;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;
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

    //private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    //private DatabaseReference mUsersDatabaseReference;
    //private String mUsersKey;

    public FirebaseAuth getMock() {
        mAuth = FirebaseAuth.getInstance();
        String User = FirebaseAuth.getInstance().getCurrentUser().getProviderId();
        mAuth.signInWithCustomToken(User);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAuth;
    }

    @Test
    public void onCreateTest() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Test
    public void testViewProfile() {
        getMock();
        onView(withId(R.id.FullNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.DisplayNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.emailTextView)).check(matches(isDisplayed()));
        //onView(withId(R.id.ProfileImageView)).check(matches(isDisplayed()));
    }


    @Test
    public void testViewLogic(){
        getMock();
        final String val = onView(withId(R.id.DisplayNameTextView)).check(matches(isDisplayed())).toString();
        final String val2 = onView(withId(R.id.FullNameTextView)).check(matches(isDisplayed())).toString();
        final String val3 = onView(withId(R.id.emailTextView)).check(matches(isDisplayed())).toString();
        //final String val4 = onView(withId(R.id.ProfileImageView)).check(matches(isDisplayed())).toString();

        String user_email = mAuth.getCurrentUser().getEmail();
        assertEquals(val, user_email);

        //assertEquals(true,val);
        // assertEquals(user_name,val2);
        //assertEquals(user_email,val3);
        //assertEquals(true ,user_image);

    }


}
