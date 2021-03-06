package com.socialteinc.socialate;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.filters.MediumTest;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateProfileTest {

    private String displayName;
    private String fullName;
    private FirebaseAuth mAuth;

    @Rule
    public ActivityTestRule<CreateProfileActivity> rule = new ActivityTestRule<>(CreateProfileActivity.class);

    @Before
    public void initValideString(){
        displayName = "Musa";
        fullName = "MusaRikhotso";

        FirebaseApp.initializeApp(rule.getActivity());
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword("testgmail.com", "password")
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
    @MediumTest
    public void test1() throws InterruptedException{
        Thread.sleep(3000);
        onView(withId(R.id.displayNameEditText)).check(matches(isClickable()));
        onView(withId(R.id.setupPictureButton)).check(matches(isClickable()));

        onView(withId(R.id.displayNameEditText)).perform(typeText(displayName),pressBack());
        onView(withId(R.id.fullNameEditText)).perform(typeText(fullName),pressBack());
    }


}
