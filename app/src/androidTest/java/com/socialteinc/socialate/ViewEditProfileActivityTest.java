package com.socialteinc.socialate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.google.firebase.database.DatabaseReference.goOffline;
import static com.google.firebase.database.DatabaseReference.goOnline;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewEditProfileActivityTest {
    @Rule
    public ActivityTestRule<ViewEditProfileActivity> rule = new ActivityTestRule<>(ViewEditProfileActivity.class);

    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUserDatabaseReference;
    private DatabaseReference mProfileDatabaseReference;
    private String mUsersKey;


    @Before
    public void login(){
        FirebaseApp.initializeApp(rule.getActivity());
        mAuth = FirebaseAuth.getInstance();
        mFireBaseDatabase = FirebaseDatabase.getInstance();

        mAuth.signInWithEmailAndPassword("musa@gmail.com", "password")
                .addOnCompleteListener(rule.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("login activity", "signInWithEmail:failure", task.getException());

                        }
                    }
                });


//        mFirebaseUser = mAuth.getCurrentUser();
//        mProfileDatabaseReference = mFireBaseDatabase.getReference().child("users");
//        mUserDatabaseReference = mFireBaseDatabase.getReference().child("users").child(mFirebaseUser.getUid());
//        mUsersKey = mAuth.getCurrentUser().getUid();

    }

    @Test
    public void isDisplayedTest1() throws InterruptedException{
        Thread.sleep(15000);
        onView(withId(R.id.ProfileToolbar1)).check(matches(isDisplayed()));
        onView(withId(R.id.addImageTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.fullNameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.displayNameEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.describeEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.phoneEditText)).check(matches(isDisplayed()));

        onView(withId(R.id.homeAddressEditText)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.spinnerGender)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.imageView)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.submitChangesButton)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));

        Thread.sleep(5000);

    }

    @Test
    public void editProfileTest2() throws InterruptedException{
        Thread.sleep(15000);
        //onView(withId(R.id.addImageTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.fullNameEditText)).perform(clearText())
                .perform(typeText("ChristianoRonaldo"), closeSoftKeyboard());

        onView(withId(R.id.displayNameEditText)).perform(clearText())
                .perform(typeText("CR7"), closeSoftKeyboard());

        onView(withId(R.id.describeEditText)).perform(clearText())
                .perform(ViewActions.scrollTo()).perform(typeText("I work hard"), closeSoftKeyboard());

        onView(withId(R.id.phoneEditText)).perform(clearText())
                .perform(ViewActions.scrollTo()).perform(typeText("0793472862"), closeSoftKeyboard());

        onView(withId(R.id.homeAddressEditText)).perform(clearText())
                .perform(ViewActions.scrollTo()).perform(typeText("Madrid, Spain"), closeSoftKeyboard());

        //onView(withId(R.id.spinnerGender)).perform(ViewActions.scrollTo()).perform(click()).perform(click());

        onView(withId(R.id.submitChangesButton)).perform(ViewActions.scrollTo()).perform(click());



        //Thread.sleep(8000);

    }

//    @Test
//    public void Test1() throws InterruptedException{
//        Thread.sleep(8000);
//        // Display current user profile details
//        mProfileDatabaseReference.child(mUsersKey).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String user_display = (String) dataSnapshot.child("displayName").getValue();
////                String user_name = (String) dataSnapshot.child("name").getValue();
////                String user_image = (String) dataSnapshot.child("profileImage").getValue();
////                String user_descrip = (String) dataSnapshot.child("description").getValue();
////                String user_phone = (String) dataSnapshot.child("phone number").getValue();
////                String user_address = (String) dataSnapshot.child("physical address").getValue();
////                String user_gender = (String) dataSnapshot.child("gender").getValue();
////                String user_email = mAuth.getCurrentUser().getEmail();
//
//                onView(withId(R.id.displayNameEditText)).perform(typeText(user_display), closeSoftKeyboard());
////                getEmail.setText(user_email);
////                getFullName.setText(user_name);
////                getDescrip.setText(user_descrip);
////                getHome_address.setText(user_address);
////                getPhone.setText(user_phone);
////                getGender.setSelection(((ArrayAdapter<String>)getGender.getAdapter()).getPosition(user_gender));
////
////                Picasso.with(getApplicationContext())
////                        .load(user_image)
////                        .into(getProfilePicture);
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
    //}




//    @Test
//    public void viewTest(){
//        /*onView(withId(R.id.fullNameEditText)).perform(typeText("James"), closeSoftKeyboard());
//        onView(withId(R.id.displayNameEditText)).perform(typeText("James"), closeSoftKeyboard());
//        onView(withId(R.id.phoneEditText)).perform(typeText("112"), closeSoftKeyboard());
//        onView(withId(R.id.describeEditText)).perform(typeText("James"), closeSoftKeyboard());
//        onView(withId(R.id.homeAddressEditText)).perform(typeText("James"), closeSoftKeyboard());
//        */
//    }
//
//    public ActivityTestRule<ViewEditProfileActivity> rule = new ActivityTestRule<ViewEditProfileActivity>(ViewEditProfileActivity.class){
//        @Override
//        protected Intent getActivityIntent() {
//            FirebaseDatabase.getInstance().goOnline();
//            //FirebaseAuth.getInstance().signInAnonymously();
//            //goOffline();
//            Context targetContext = InstrumentationRegistry.getInstrumentation()
//                    .getTargetContext();
//            Intent result = new Intent(targetContext, ViewEditProfileActivity.class);
//            String userKey = "B7TbLOcLXggRL1TyQxrgrGlwMiO2";
//            String userName = "Joe Manga";
//            result.putExtra("userName", userName);
//            result.putExtra("userKey", userKey);
//            return result;
//        }
//    };
//
//    @Test
//    public void onCreateTest() throws InterruptedException {
//       Thread.sleep(5000);
//    }
//
//
//    public FirebaseAuth getMock() throws InterruptedException {
//
//        //mUsersKey = mAuth.getCurrentUser().getUid();
//
//        //String User = mAuth.getCurrentUser().getProviderId().toString();
//        //mAuth.signInWithCustomToken(User);
//        //Thread.sleep(6000);
//
//        //final String val = onView(withId(R.id.DisplayNameTextView)).check(matches(isDisplayed())).toString();
//        //String user_email = mAuth.getCurrentUser().getEmail();
//        //assertEquals(val, user_email);
//
//        return mAuth;
//    }
//
//    @Test
//    public void testViewProfile() throws InterruptedException {
//        //getMock();
//        //FirebaseDatabase.getInstance().goOnline();
//        //FirebaseAuth.getInstance().signInAnonymously();
//        //Thread.sleep(6000);
//        /*onView(withId(R.id.ProfileToolbar1)).check(matches(isDisplayed()));
//        onView(withId(R.id.fullNameEditText)).check(matches(isDisplayed()));
//        onView(withId(R.id.describeEditText)).check(matches(isDisplayed()));
//        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()));
//        onView(withId(R.id.describeEditText)).check(matches(isDisplayed()));
//        //onView(withId(R.id.maleRadioButton)).check(matches(isChecked()));
//        //onView(withId(R.id.femaleRadioButton)).check(matches(isChecked()));
//        onView(withId(R.id.homeAddressEditText)).check(matches(isDisplayed()));
//        onView(withId(R.id.phoneEditText)).check(matches(isDisplayed()));
//        onView(withId(R.id.addImageTextView)).check(matches(isDisplayed()));
//        onView(withId(R.id.imageView)).check(matches(isDisplayed()));*/
//
//        assertEquals(true, CheckEmailActivity.isValidEmail("socialate@gmail.com"));
//        assertEquals(true, CheckEmailActivity.isValidEmail("1234@gmail.com"));
//        assertEquals(false, LoginActivity.isValidEmail("yakka"));
//        assertEquals(true, LoginActivity.isValidEmail("sandile.cyber@gmail.com"));
//        assertEquals(true, LoginActivity.isValidEmail("12@gmail.com"));
//
//    }
//
//
//    @Test
//    public void testViewLogic() throws InterruptedException {
//        getMock();
//        /*final String val = onView(withId(R.id.DisplayNameTextView)).check(matches(isDisplayed())).toString();
//        final String val2 = onView(withId(R.id.FullNameTextView)).check(matches(isDisplayed())).toString();
//        final String val3 = onView(withId(R.id.emailTextView)).check(matches(isDisplayed())).toString();
//        //final String val4 = onView(withId(R.id.ProfileImageView)).check(matches(isDisplayed())).toString();
//        */
//        //String user_email = mAuth.getCurrentUser().getEmail();
//        //assertEquals(val, user_email);
//
//        //assertEquals(true,val);
//        // assertEquals(user_name,val2);
//        //assertEquals(user_email,val3);
//        //assertEquals(true ,user_image);
//
//    }


}
