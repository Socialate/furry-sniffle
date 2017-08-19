package com.socialteinc.socialate;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d("main-login activity", "the user is logged in redirect to a revelant UI ");
            //setContentView(R.layout.main_activity);
        }
        else {
            // No user is signed in
            Log.d("main-login activity", " user is not signed in let's sign them in");
            //setContentView(R.layout.main_activity);
        }
        Log.d("main - login activity", "successfully launched .........");
    }



    public void signIn(View view) {
         /* ***
         * This is the sign in method, it is going to check if users are signed in.
         * if they are there then we don't do anything we'll just adjust the views.
         * if they aren't signed in then we try to sign them in, provided they gave
         * correct credentials.
         * Author: Sandile Shongwe
         * ***/

        String email = ((EditText) findViewById(R.id.email_field)).getText().toString();
        String password = ((EditText) findViewById(R.id.password_field)).getText().toString();

        if (isValidEmail(email) && password != null) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("login activity", "signInWithEmail:success *******");
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("login activity", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Authentication failed. Please enter valid username/password",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isValidEmail(CharSequence target) {
        /* ***
         * Little helper method for verifying if the password pattern matches
         * Author: Sandile Shongwe
         * ***/
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}