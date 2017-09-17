package com.socialteinc.socialate;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class aVEPtest {
    private FirebaseAuth mAuth;

   //@Rule
   public ActivityTestRule<ViewEditProfileActivity> main = new ActivityTestRule<>(ViewEditProfileActivity.class);
    Activity m;
    @Before
    public void login() throws InterruptedException {
        mAuth = FirebaseAuth.getInstance();
        System.out.println(" fffffffffffffffff "+ mAuth);
        mAuth.signOut();
        System.out.println(" fffffffffffffffff "+ mAuth);
        mAuth.signInWithEmailAndPassword("joe@gmail.com", "sandile");
        Thread.sleep(9000);
        System.out.println(" fffffffffffffffff "+ mAuth.getCurrentUser());
        Intent activity = new Intent(String.valueOf(MainActivity.class));
        m = main.launchActivity(activity);
        Thread.sleep(3000);

    }

    @Test
    public void UiTest() throws InterruptedException {
        onView(withId(R.id.textInputLayout5)).check(matches(isDisplayed()));
        onView(withId(R.id.fullNameEditText)).perform(ViewActions.clearText());
        onView(withId(R.id.fullNameEditText)).perform(ViewActions.typeText("Furry Sniffle"), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));

        onView(withId(R.id.textInputLayout6)).check(matches(isDisplayed()));
        onView(withId(R.id.displayNameEditText)).perform(ViewActions.clearText());
        onView(withId(R.id.displayNameEditText)).perform(ViewActions.typeText("Socialate"), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));

        onView(withId(R.id.textInputLayout7)).check(matches(isDisplayed()));
        onView(withId(R.id.describeEditText)).perform(ViewActions.clearText());
        onView(withId(R.id.describeEditText)).perform(ViewActions.typeText("I'm a geolocation service"), ViewActions.closeSoftKeyboard()).check(matches(isDisplayed()));


    }


}
