package com.socialteinc.socialate;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.MediumTest;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.v4.content.ContextCompat.startActivity;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewEntertainmentTest2 {

    FirebaseAuth mAuth;

    @Rule
    public ActivityTestRule<ViewEntertainmentActivity> rule2 = new ActivityTestRule<>(ViewEntertainmentActivity.class);

    @Before
    public void login(){
        FirebaseApp.initializeApp(rule2.getActivity());
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword("musa@gmail.com", "password")
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


    @Test
    @SmallTest
    public void ViewTests1() throws InterruptedException{
        rule2.getActivity();
        Thread.sleep(20000);

        onView(withId(R.id.ViewAddedAreaOwnerText)).check(matches(isDisplayed()));
        onView(withId(R.id.ViewAddedAreaAddressText)).check(matches(isDisplayed()));
        onView(withId(R.id.likeFloatingActionButton)).check(matches(isDisplayed()));
        onView(withId(R.id.ViewAddedAreaImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.authorImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.navigationImageView)).check(matches(isDisplayed()));
    }

    @Test
    @SmallTest
    public void ViewTests2() throws InterruptedException{
        rule2.getActivity();
        Thread.sleep(20000);
        onView(isRoot()).perform(swipeUp());
        Thread.sleep(2000);
        onView(withId(R.id.descriptImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.averageCostImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.ViewAddedAreaDescText)).check(matches(isDisplayed()));
        onView(withId(R.id.averageCostSpinner)).check(matches(isDisplayed()));
    }

    @Test
    public void ViewTest3() throws InterruptedException{
        rule2.getActivity();
        Thread.sleep(20000);
        onView(isRoot()).perform(swipeUp());
        Thread.sleep(2000);

        String name = "Low Cost";
        onView(withId(R.id.averageCostSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(name))).perform(click());
        onView(withId(R.id.averageCostSpinner)).check(matches(withSpinnerText(containsString(name))));
        Thread.sleep(4000);
    }

    @Test
    public void ViewTest31() throws InterruptedException{
        rule2.getActivity();
        Thread.sleep(20000);
        onView(isRoot()).perform(swipeUp());
        Thread.sleep(2000);

        String name = "Medium Cost";
        onView(withId(R.id.averageCostSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(name))).perform(click());
        onView(withId(R.id.averageCostSpinner)).check(matches(withSpinnerText(containsString(name))));
        Thread.sleep(4000);
    }

    @Test
    public void ViewTest32() throws InterruptedException{
        rule2.getActivity();
        Thread.sleep(20000);
        onView(isRoot()).perform(swipeUp());
        Thread.sleep(2000);

        String name = "High Cost";
        onView(withId(R.id.averageCostSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(name))).perform(click());
        onView(withId(R.id.averageCostSpinner)).check(matches(withSpinnerText(containsString(name))));
        Thread.sleep(4000);
    }

    @Test
    public void ViewTest33() throws InterruptedException{
        rule2.getActivity();
        Thread.sleep(20000);
        onView(isRoot()).perform(swipeUp());
        Thread.sleep(2000);

        String name = "How costly is this place for you?";
        onView(withId(R.id.averageCostSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(name))).perform(click());
        onView(withId(R.id.averageCostSpinner)).check(matches(withSpinnerText(containsString(name))));
        Thread.sleep(4000);
    }

    @Test
    @MediumTest
    public void ViewTest4() throws InterruptedException{
        rule2.getActivity();
        Thread.sleep(20000);
        onView(isRoot()).perform(swipeUp());
        Thread.sleep(2000);

        onView(withId(R.id.comment_recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.commentEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.commentImageButton)).check(matches(isDisplayed()));

        onView(withId(R.id.commentEditText)).perform(typeText("Auto-Generated test #Testing"));
        onView(withId(R.id.commentImageButton)).perform(click());

        Thread.sleep(5000);
    }

}