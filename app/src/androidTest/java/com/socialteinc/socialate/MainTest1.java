package com.socialteinc.socialate;

import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainTest1 {

    FirebaseAuth mAuth;
    DatabaseReference mUsersDatabaseReference;
    FirebaseDatabase mFireBaseDatabase;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void login(){
        FirebaseApp.initializeApp(rule.getActivity());
        mAuth = FirebaseAuth.getInstance();

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
    }

//    @Test
//    public void recyclerViewTest() throws InterruptedException{
//        login();
//        Thread.sleep(4000);
//        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToPosition(3));
//        //onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToHolder(...));
//    }

    @Test
    public void signOutTest() throws InterruptedException {
        Thread.sleep(6000);
        mAuth.signOut();
        Thread.sleep(2000);
        login();
        Thread.sleep(2000);
    }


    @Test
    public void checkProfileTest() throws InterruptedException{
        Thread.sleep(6000);
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users");
        FirebaseApp.initializeApp(rule.getActivity());
        mAuth = FirebaseAuth.getInstance();

        Looper.prepare();
        final MainActivity obj = new MainActivity();

        if(mAuth.getCurrentUser() != null){

            final String user_id = mAuth.getCurrentUser().getUid();
            mUsersDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(user_id)){
                        obj.startActivity(new Intent(obj.getApplicationContext(), CreateProfileActivity.class));
                        obj.finish();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        Thread.sleep(4000);
    }

    @Test
    public void testView() throws InterruptedException{
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.entertainmentSpotRecyclerView),
                    withParent(allOf(withId(R.id.cardView1),
                        withParent(withId(android.R.id.content)))),
                    isDisplayed()));
        //recyclerView.check(matches(isDisplayed()));

        ViewInteraction action = onView(
                allOf(withId(R.id.titleTextView), withContentDescription("Bikini Beach"), isDisplayed()));
        //action.perform(click());
    }

    @Test
    public void ViewCommentsTest() throws InterruptedException{
        Thread.sleep(15000);

        onView(withId(R.id.action_add_entertainment)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.entertainmentSpotRecyclerView))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.entertainmentSpotRecyclerView),
                withParent(withId(R.id.cardView1)),
                    withParent(withId(android.R.id.content)), isDisplayed()));
        //onView(withId(R.menu.main_menu)).check(matches(isDisplayed()));

        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToPosition(1),click());
        Thread.sleep(9000);
        onView(withId(R.id.ViewAddedAreaImageView)).perform(swipeUp());
        onView(withId(R.id.ViewAddedAreaDescText)).perform(swipeUp());
        onView(isRoot()).perform(swipeUp());
        onView(isRoot()).perform(ViewActions.swipeUp());

        onView(withId(R.id.commentEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.commentImageButton)).check(matches(isDisplayed()));
        onView(withId(R.id.commentEditText)).check(matches(isFocusable()));

        onView(withId(R.id.commentEditText)).perform(typeText("This is an automated comment!!!"));
        onView(withId(R.id.commentImageButton)).perform(click());
        Thread.sleep(5000);

        onView(withId(R.id.commentEditText)).perform(click()).perform(typeText("This is an automated comment!!!"),pressBack());
        onView(withId(R.id.commentImageButton)).perform(click());
        Thread.sleep(5000);
        
    }

    @Test
    public void testComments() throws InterruptedException{
        Thread.sleep(15000);
        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToPosition(1),click());
        Thread.sleep(2000);
        onView(withId(R.id.ViewAddedAreaImageView)).perform(swipeUp());
        onView(withId(R.id.ViewAddedAreaDescText)).perform(swipeUp());
        onView(isRoot()).perform(swipeUp());
        onView(isRoot()).perform(ViewActions.swipeUp());

        onView(withId(R.id.comment_recyclerView)).check(matches(isDisplayed())); //
        onView(allOf(withId(R.id.comment_recyclerView),
                withParent(withId(R.id.cardView2)),
                withParent(withId(android.R.id.content)), isDisplayed()));

        onView(withId(R.id.comment_recyclerView)).perform(RecyclerViewActions.scrollToPosition(1), click());
        onView(withId(R.id.comment_recyclerView)).perform(swipeUp()).perform(swipeUp());
        Thread.sleep(4000);
    }


    @Test
    public void recyclerViewClickTest() throws InterruptedException{
        Thread.sleep(10000);
        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToPosition(3),click());
        Thread.sleep(4000);
        //onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToHolder(...));
    }

    @Test
    public void addEntertainmentTest() throws InterruptedException{
        Thread.sleep(9000);

        onView(withId(R.id.action_add_entertainment)).check(matches(isClickable()));
        onView(withId(R.id.action_add_entertainment)).perform(click());

        Thread.sleep(4000);
    }

}