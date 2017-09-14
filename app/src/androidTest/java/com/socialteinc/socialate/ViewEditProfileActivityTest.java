/*package com.socialteinc.socialate;


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
import com.google.firebase.storage.FirebaseStorage;
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
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.firebase.database.DatabaseReference.goOffline;
import static com.google.firebase.database.DatabaseReference.goOnline;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ViewEditProfileActivityTest {
    @Rule
    public ActivityTestRule<ViewEditProfileActivity> main = new ActivityTestRule<>(ViewEditProfileActivity.class);

   /* @Test
    public void viewTest(){
        onView(withId(R.id.fullNameEditText)).perform(typeText("James"), closeSoftKeyboard());
        onView(withId(R.id.displayNameEditText)).perform(typeText("James"), closeSoftKeyboard());
        onView(withId(R.id.phoneEditText)).perform(typeText("112"), closeSoftKeyboard());
        onView(withId(R.id.describeEditText)).perform(typeText("James"), closeSoftKeyboard());
        onView(withId(R.id.homeAddressEditText)).perform(typeText("James"), closeSoftKeyboard());

    }*/
    /*
    public ActivityTestRule<ViewEditProfileActivity> rule = new ActivityTestRule<ViewEditProfileActivity>(ViewEditProfileActivity.class){
        @Override
        protected Intent getActivityIntent() {
            FirebaseDatabase.getInstance().goOnline();
            FirebaseAuth.getInstance().signInAnonymously();
            //goOffline();
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

    //@Test
    //public void onCreateTest() throws InterruptedException {
        //Thread.sleep(5000);
    //}


    /*private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabaseReference;
    private String mUsersKey;

    public FirebaseAuth getMock() throws InterruptedException {

        //mUsersKey = mAuth.getCurrentUser().getUid();

        //String User = mAuth.getCurrentUser().getProviderId().toString();
        //mAuth.signInWithCustomToken(User);
        //Thread.sleep(6000);

        final String val = onView(withId(R.id.DisplayNameTextView)).check(matches(isDisplayed())).toString();
        String user_email = mAuth.getCurrentUser().getEmail();
        assertEquals(val, user_email);

        return mAuth;
    }*/

    /*@Test
    public void testViewProfile() throws InterruptedException {
        //getMock();
        //FirebaseDatabase.getInstance().goOnline();
        //FirebaseAuth.getInstance().signInAnonymously();
        //Thread.sleep(6000);
        onView(withId(R.id.ProfileToolbar1)).check(matches(isDisplayed()));
        onView(withId(R.id.fullNameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.describeEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.describeEditText)).check(matches(isDisplayed()));
        //onView(withId(R.id.maleRadioButton)).check(matches(isChecked()));
        //onView(withId(R.id.femaleRadioButton)).check(matches(isChecked()));
        onView(withId(R.id.homeAddressEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.phoneEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.addImageTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));

        assertEquals(true, CheckEmailActivity.isValidEmail("socialate@gmail.com"));
        assertEquals(true, CheckEmailActivity.isValidEmail("1234@gmail.com"));
        assertEquals(false, LoginActivity.isValidEmail("yakka"));
        assertEquals(true, LoginActivity.isValidEmail("sandile.cyber@gmail.com"));
        assertEquals(true, LoginActivity.isValidEmail("12@gmail.com"));

    }*/

    /*
    @Test
    public void testViewLogic() throws InterruptedException {
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
    */

//}
