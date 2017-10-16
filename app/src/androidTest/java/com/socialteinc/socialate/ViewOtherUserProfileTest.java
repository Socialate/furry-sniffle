package com.socialteinc.socialate;

import android.os.Looper;
import android.support.annotation.NonNull;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ViewOtherUserProfileTest {

    FirebaseAuth mAuth;
    DatabaseReference mUsersDatabaseReference;
    FirebaseDatabase mFireBaseDatabase;

    @Rule
    public ActivityTestRule<ViewOtherUserProfile> rule2 = new ActivityTestRule<>(ViewOtherUserProfile.class);

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


    @Test
    public void profileViewTests1() throws InterruptedException{
//        Thread.sleep(10000);
//        Looper.prepare();
//        ViewOtherUserProfile obj = new ViewOtherUserProfile();
//        obj.checker = true;

//        onView(withId(R.id.displayNameTextView)).check(matches(isDisplayed()));
//        onView(withId(R.id.describeEditText)).check(matches(isDisplayed()));
//        onView(withId(R.id.fullNameTextView)).check(matches(isDisplayed()));
//        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));
        //Thread.sleep(1000);
    }


}