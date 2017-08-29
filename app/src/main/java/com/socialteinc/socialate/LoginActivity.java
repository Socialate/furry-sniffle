package com.socialteinc.socialate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity {

    // Firebase instance variables
    private FirebaseAuth mAuth;

    //Reference variables
    private ProgressDialog mProgressDialog;
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

        //Initialise reference variables
        constraintLayout = findViewById(R.id.loginConstraintLayout);
        inputEmail = findViewById(R.id.email_field);
        inputPassword = findViewById(R.id.password_field);
        inputLayoutEmail = findViewById(R.id.loginEmailTextInputLayout);
        inputLayoutPassword = findViewById(R.id.loginPasswordTextInputLayout);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
    }


    public void signIn(View v) {
         /* ***
         * This is the sign in method, it is going to check if users are signed in.
         * if they are there then we don't do anything we'll just adjust the views.
         * if they aren't signed in then we try to sign them in, provided they gave
         * correct credentials.
         * Author: Sandile Shongwe
         * ***/
         if(!validateEmail()){return;}
         if(!validatePassword()){return;}

        String email = ((EditText) findViewById(R.id.email_field)).getText().toString();
        String password = ((EditText) findViewById(R.id.password_field)).getText().toString();



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
                            Snackbar.make(constraintLayout, "Authentication Failed, Invalid Email or Password!", Snackbar.LENGTH_LONG ).show();

                        }
                    }
                });

    }


    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    public void signUp(View v){
        Intent activity = new Intent(this,CheckEmailActivity.class);
        startActivity(activity);
        Log.d("login activity"," Need an account click successful");

    }

    public void resetPassword(View v){
        Intent activity = new Intent(this,ResetPasswordActivity.class);
        startActivity(activity);
        Log.d("login activity"," reset password click successful");

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

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.email_field:
                    validateEmail();
                    break;
                case R.id.password_field:
                    validatePassword();
                    break;
            }
        }
    }
}
