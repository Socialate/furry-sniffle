package com.socialteinc.socialate;

import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class CommentsTest1 {

    FirebaseAuth mAuth;
    DatabaseReference mUsersDatabaseReference;
    FirebaseDatabase mFireBaseDatabase;

    @Rule
    public ActivityTestRule<ViewEntertainmentActivity> rule2 = new ActivityTestRule<>(ViewEntertainmentActivity.class);

    @Before
    public void login(){
        FirebaseApp.initializeApp(rule2.getActivity());
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword("joe@gmail.com", "sandile")
                .addOnCompleteListener(rule2.getActivity(), new OnCompleteListener<AuthResult>() {
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
    }

//    @Test
//    public void recyclerViewTest() throws InterruptedException{
//        Thread.sleep(2000);
//        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToPosition(5));
//        Thread.sleep(9000);
//        //onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToHolder(...));
//    }

//    @Test
//    public void signOutTest() throws InterruptedException {
//        Thread.sleep(6000);
//        mAuth.signOut();
//        Thread.sleep(3000);
//        login();
//    }

    @Test
    public void ViewEntertainmentAreaTest() throws InterruptedException {
        Thread.sleep(9000);
        //onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6,click()));

        onView(withId(R.id.ViewAddedAreaOwnerText)).check(matches(isDisplayed()));
        onView(withId(R.id.ViewAddedAreaAddressText)).check(matches(isDisplayed()));
        onView(withId(R.id.ViewAddedAreaDescText)).check(matches(isDisplayed()));
        onView(withId(R.id.ViewAddedAreaImageView)).check(matches(isDisplayed()));

//        onView(withId(R.id.commentImageButton)).check(matches(isDisplayed()));
//        onView(withId(R.id.commentEditText)).check(matches(isDisplayed()));

        onView(withId(R.id.likeFloatingActionButton)).perform(click());
        onView(withId(R.id.ViewAddedAreaOwnerText)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.displayNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.describeEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.fullNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));

//        Thread.sleep(90000);
    }

//    @Test
//    public void ViewCommentsTest() throws InterruptedException{
//        Thread.sleep(9000);
//        //onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
//        onView(withId(R.id.comment_recyclerView)).check(matches(isDisplayed()));
//        Thread.sleep(4000);
//        onView(withId(R.id.comment_recyclerView)).perform(RecyclerViewActions.scrollToPosition(2));
//        onView(withId(R.id.commentorNameTextView)).check(matches(isDisplayed()));
//        onView(withId(R.id.commentorProfileImageView)).check(matches(isDisplayed()));
//        onView(withId(R.id.commentMultiAutoCompleteTextView)).check(matches(isDisplayed()));
//        onView(withId(R.id.dateTextView)).check(matches(isDisplayed()));
//        onView(withId(R.id.likeTextView)).check(matches(isDisplayed()));
//        onView(withId(R.id.likeCommentCounterTextView)).check(matches(isDisplayed()));
//
//    }
//
//    @Test
//    public void checkProfileTest() throws InterruptedException{
//        Thread.sleep(6000);
//        mFireBaseDatabase = FirebaseDatabase.getInstance();
//        mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users");
//        FirebaseApp.initializeApp(rule2.getActivity());
//        mAuth = FirebaseAuth.getInstance();
//
//        Looper.prepare();
//        final MainActivity obj = new MainActivity();
//
//        if(mAuth.getCurrentUser() != null){
//
//            final String user_id = mAuth.getCurrentUser().getUid();
//            mUsersDatabaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if(!dataSnapshot.hasChild(user_id)){
//                        obj.startActivity(new Intent(obj.getApplicationContext(), CreateProfileActivity.class));
//                        obj.finish();
//                    }
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//    }


}