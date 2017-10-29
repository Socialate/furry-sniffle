//package com.socialteinc.socialate;
//
//import android.app.Activity;
//import android.app.Instrumentation;
//import android.content.Intent;
//import android.os.Looper;
//import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.test.espresso.ViewAction;
//import android.support.test.espresso.action.ViewActions;
//import android.support.test.espresso.contrib.RecyclerViewActions;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.util.Log;
//import android.view.View;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.*;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.intent.Intents.intending;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
//import static android.support.test.espresso.matcher.ViewMatchers.*;
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertFalse;
//import static org.hamcrest.Matchers.allOf;
//
//
//@RunWith(AndroidJUnit4.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class CommentsTest2 {
//    private FirebaseAuth mAuth;
////    FirebaseDatabase mFireBaseDatabase;
////    DatabaseReference mLikesDatabaseReference;
////    FloatingActionButton mLikeButton;
//
//
//    @Rule
//    public ActivityTestRule<ViewEntertainmentActivity> rule = new ActivityTestRule<>(ViewEntertainmentActivity.class);
//
//    @Before
//    public void login(){
//        FirebaseApp.initializeApp(rule.getActivity());
//        mAuth = FirebaseAuth.getInstance();
//        mAuth.signInWithEmailAndPassword("joe@gmail.com", "sandile")
//                .addOnCompleteListener(rule.getActivity(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("login activity", "signInWithEmail:failure", task.getException());
//
//                        }
//                    }
//                });
//    }
//
//
//    @Test
//    public void isDisplayedTest() throws InterruptedException {
//        Thread.sleep(15000);
//
//        onView(withId(R.id.ViewAddedAreaOwnerText)).check(matches(isDisplayed()));
//        onView(withId(R.id.ViewAddedAreaAddressText)).check(matches(isDisplayed()));
//        onView(withId(R.id.ViewAddedAreaImageView)).check(matches(isDisplayed()));
//        onView(withId(R.id.authorImageView)).check(matches(isDisplayed()));
//
//        onView(withId(R.id.ViewAddedAreaImageView)).perform(swipeUp());
//
//        onView(withId(R.id.ViewAddedAreaDescText)).check(matches(isDisplayed()));
//        onView(withId(R.id.navigationImageView)).check(matches(isDisplayed()));
//        //onView(withId(R.id.de)).check(matches(isDisplayed()));
//
//    }
//
//    @Test
//    public void testComment() throws InterruptedException{
//        Thread.sleep(15000);
//        onView(withId(R.id.ViewAddedAreaImageView)).perform(swipeUp());
//        onView(withId(R.id.ViewAddedAreaDescText)).perform(swipeUp());
//        onView(isRoot()).perform(swipeUp());
//        onView(isRoot()).perform(ViewActions.swipeUp());
//
////        onView(withId(R.id.commentEditText)).check(matches(isDisplayed()));
////        onView(withId(R.id.commentImageButton)).check(matches(isDisplayed()));
////        Thread.sleep(4000);
//
//    }
//
////    @Test
////    public void commentsTest() throws InterruptedException{
////        Thread.sleep(9000);
////        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
////
////        Thread.sleep(3000);
////        onView(withId(R.id.displayNameTextView)).check(matches(isDisplayed()));
////        onView(withId(R.id.describeEditText)).check(matches(isDisplayed()));
////        onView(withId(R.id.fullNameTextView)).check(matches(isDisplayed()));
////        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
////    }
//
//    @Test
//    public void likeTest() throws InterruptedException {
//        Thread.sleep(15000);
//        onView(withId(R.id.likeFloatingActionButton)).perform(click());
//        //onView(isRoot()).perform(ViewActions.pressBack());
//        Thread.sleep(4000);
//    }
//
////    @Test
////    public void viewAuthorTest() throws InterruptedException {
////        Thread.sleep(15000);
////        onView(withId(R.id.ViewAddedAreaOwnerText)).check(matches(isClickable()));
////        //onView(withId(R.id.ViewAddedAreaOwnerText)).perform(click());
////        //onView(isRoot()).perform(pressBack());
////        Thread.sleep(4000);
////    }
////
////
////    @Test
////    public void NavigationTest() throws InterruptedException{
////        Thread.sleep(15000);
////        onView(withId(R.id.navigationImageView)).check(matches(isClickable()));
////        //onView(withId(R.id.navigationImageView)).check(matches(isFocusable()));
////        onView(withId(R.id.navigationImageView)).perform(click());
////        Thread.sleep(2000);
////
////    }
//
//    @Test
//    public void testComments() throws InterruptedException{
//        Thread.sleep(15000);
//        onView(withId(R.id.ViewAddedAreaImageView)).perform(swipeUp());
//        onView(withId(R.id.ViewAddedAreaDescText)).perform(swipeUp());
//        onView(isRoot()).perform(swipeUp());
//        Thread.sleep(1000);
//        onView(isRoot()).perform(ViewActions.swipeUp());
//
//        onView(withId(R.id.comment_recyclerView)).check(matches(isDisplayed())); //
//        onView(allOf(withId(R.id.comment_recyclerView),
//                withParent(withId(R.id.cardView2)),
//                withParent(withId(android.R.id.content)), isDisplayed()));
//
//        onView(withId(R.id.comment_recyclerView)).perform(RecyclerViewActions.scrollToPosition(1));
//        //onView(withId(R.id.comment_recyclerView)).perform(swipeUp());
//        Thread.sleep(4000);
//    }
//
////    @Test
////    public void navigationTest2() throws InterruptedException{
////        // Build the result to return when the activity is launched.
////        Intent resultData = new Intent();
////        //String phoneNumber = "123-345-6789";
////        //resultData.putExtra("phone", phoneNumber);
////
////        Instrumentation.ActivityResult result =
////                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
////
////        // Set up result stubbing when an intent sent to "contacts" is seen.
////        intending(toPackage("com.google.android.apps.maps")).respondWith(result);
////
////        // User action that results in "contacts" activity being launched.
////        // Launching activity expects phoneNumber to be returned and displayed.
////        onView(withId(R.id.navigationImageView)).perform(click());
////
////        // Assert that the data we set up above is shown.
////        //onView(withId(R.id.phoneNumber)).check(matches(withText(phoneNumber)));
////
////    }
//
//
//}