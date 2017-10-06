package com.socialteinc.socialate;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;


@RunWith(AndroidJUnit4.class)

public class CommentsTest2 {
    private FirebaseAuth mAuth;
    //private FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mFireBaseDatabase;
    DatabaseReference mLikesDatabaseReference;
    FloatingActionButton mLikeButton;


    @Rule
    public ActivityTestRule<ViewEntertainmentActivity> rule = new ActivityTestRule<>(ViewEntertainmentActivity.class);

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
    public void likesTest(){
        // create an object from ViewEntertainmentActivity
        //Looper.prepare();
       //final ViewEntertainmentActivity obj = new ViewEntertainmentActivity();
       // assertEquals(rule.getActivity().doMySearch("Bikini"), true);
        //mLikeButton = obj.findViewById(R.id.likeFloatingActionButton);
        onView(withId(R.id.likeFloatingActionButton)).perform(click());
        /*(obj.findViewById(R.id.likeFloatingActionButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj.processLike();
            }
        });*/

    }

}