package com.socialteinc.socialate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity {
    private FirebaseAuth mAuth;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

    }


    public void signIn(View v) {
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

            mProgressDialog.setTitle("Logging In");
            mProgressDialog.setMessage("Please wait while we get your account.");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                mProgressDialog.dismiss();
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("login activity", "signInWithEmail:success *******");
                                Intent activity = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(activity);
                                finish();

                            } else {
                                mProgressDialog.dismiss();
                                // If sign in fails, display a message to the user.
                                Log.w("login activity", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed. Please enter valid username/password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Authentication failed. Please enter valid username/password",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void signUp(View v){
        Intent activity = new Intent(this,CheckEmailActivity.class);
        startActivity(activity);
        Log.d("login activity"," Need an account click successful");

    }


    static boolean isValidEmail(CharSequence target) {
        /* ***
         * Little helper method for verifying if the password pattern matches
         * Author: Sandile Shongwe
         * ***/
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(target);
        return matcher.matches() && target != null ;
    }
}
