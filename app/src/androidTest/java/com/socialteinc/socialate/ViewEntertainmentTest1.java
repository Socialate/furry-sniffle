//package com.socialteinc.socialate;
//
//import android.support.annotation.NonNull;
//import android.support.test.espresso.contrib.RecyclerViewActions;
//import android.support.test.rule.ActivityTestRule;
//import android.util.Log;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
//public class ViewEntertainmentTest1 {
//
//    private FirebaseAuth mAuth;
//
//    @Rule
//    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
//
//    @Before
//    public void login(){
//        FirebaseApp.initializeApp(rule.getActivity());
//        mAuth = FirebaseAuth.getInstance();
//
//        mAuth.signInWithEmailAndPassword("musa@gmail.com", "password")
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
//    @Test
//    public void recyclerViewTest() throws InterruptedException{
//        login();
//        Thread.sleep(4000);
//        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToPosition(3));
//        //onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.scrollToHolder(...));
//    }
//
//    @Test
//    public void isDisplayedTest() throws InterruptedException {
//        Thread.sleep(15000);
//        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
//        Thread.sleep(3000);
//        onView(withId(R.id.ViewAddedAreaOwnerText)).check(matches(isDisplayed()));
//        onView(withId(R.id.ViewAddedAreaAddressText)).check(matches(isDisplayed()));
//        onView(withId(R.id.ViewAddedAreaDescText)).check(matches(isDisplayed()));
//        onView(withId(R.id.ViewAddedAreaImageView)).check(matches(isDisplayed()));
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
//        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
//        onView(withId(R.id.likeFloatingActionButton)).perform(click());
//        Thread.sleep(4000);
//    }
//
//    @Test
//    public void viewAuthorTest() throws InterruptedException {
//        Thread.sleep(15000);
//        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
//
//        Thread.sleep(4000);
//    }
//
//    @Test
//    public void NavigationTest() throws InterruptedException{
//        Thread.sleep(15000);
//        onView(withId(R.id.entertainmentSpotRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
//        onView(withId(R.id.navigationImageView)).check(matches(isDisplayed()));
//        onView(withId(R.id.navigationImageView)).perform(click());
//
//        Thread.sleep(9000);
//        //onView(withId(R.id.comment_recyclerView)).perform(RecyclerViewActions.scrollToPosition(2));
////        onView(withId(R.id.commentorNameTextView)).check(matches(isDisplayed()));
////        onView(withId(R.id.commentorProfileImageView)).check(matches(isDisplayed()));
////        onView(withId(R.id.commentMultiAutoCompleteTextView)).check(matches(isDisplayed()));
////        onView(withId(R.id.dateTextView)).check(matches(isDisplayed()));
////        onView(withId(R.id.likeTextView)).check(matches(isDisplayed()));
////        onView(withId(R.id.likeCommentCounterTextView)).check(matches(isDisplayed()));
////        Thread.sleep(4000);
//    }
//
//}
