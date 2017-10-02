package com.socialteinc.socialate;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.GONE;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;


import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SearchableTest {

    FirebaseAuth mAuth;
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void login(){
        FirebaseApp.initializeApp(rule.getActivity());
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("joe@gmail.com", "sandile")
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

     @Test
     public void searchTest() throws InterruptedException {
         FirebaseUser user = mAuth.getCurrentUser();
         if(user == null) {
             onView(withId(R.id.email_field)).perform(typeText("joe@gmail.com"), closeSoftKeyboard());
             onView(withId(R.id.password_field)).perform(typeText("sandile"), closeSoftKeyboard());
             onView(withId(R.id.SinginButton)).perform(click());
         }

         Thread.sleep(3500);
         openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
         onView(withContentDescription("search option")).perform(ViewActions.click());
         //onView((withId(R.id.search_btn))).check(matches(isDisplayed()));
         //onView(allOf(withId(R.id.search_btn), withEffectiveVisibility(VISIBLE))).perform(click());
         onView((withHint("Search for a spot"))).perform(ViewActions.typeText("Bikini"), pressImeActionButton());
         Thread.sleep(2500);
         onView(withId(R.id.search_recyclerView))
                 .perform(RecyclerViewActions.scrollToPosition(0)).perform(click());
     }
}
