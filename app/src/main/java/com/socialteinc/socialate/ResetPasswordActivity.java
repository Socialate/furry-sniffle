package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.List;

import static com.socialteinc.socialate.LoginActivity.isValidEmail;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = ResetPasswordActivity.class.getSimpleName();

    // References variables
    private EditText mEmailEditText;
    private Button mPasswordResetButton;
    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;
    private TextInputLayout inputLayoutResetPassword;
    private FirebaseAuth mFirebaseAuth;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Initialize references to views
        mConstraintLayout = findViewById(R.id.resetPasswordConstraintLayout);
        mEmailEditText = findViewById(R.id.resetEmailEditText);
        mPasswordResetButton = findViewById(R.id.resetPasswordButton);
        inputLayoutResetPassword = findViewById(R.id.resetPasswordTextInputLayout);
        mToolbar = findViewById(R.id.resetPageToolBar);
        mProgressDialog = new ProgressDialog(this);

        mEmailEditText.addTextChangedListener(new MyTextWatcher());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reset Password");

        // Initialize Firebase components
        mFirebaseAuth = FirebaseAuth.getInstance();

        mPasswordResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmailEditText.getText().toString();
                checkAccountEmailExistsInFirebase(email);
            }
        });
    }

    private boolean validateEmail() {
        String email = mEmailEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutResetPassword.setError(getString(R.string.err_msg_email));
            return false;
        } else {
            inputLayoutResetPassword.setErrorEnabled(false);
        }

        return true;
    }

    /*
    this function checks if the user has a registered email address and if the user does
    then we can reset the password for the account.
     */
    private void checkAccountEmailExistsInFirebase(final String email) {

        if(!validateEmail()) return;

        mProgressDialog.setTitle("Resetting Password");
        mProgressDialog.setMessage("Please wait while we send password reset email.");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {

            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                Log.d(TAG, "checking email exists.");
                if(task.getResult().getProviders() != null){

                            resetPassword(task.getResult().getProviders(), email);

                }
            }
        });
    }

    /*
    this function send the user an account reset email so that they can change their password.
     */
    private void resetPassword(List<String> providers, final String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Boolean flag = false;
        for (String provider : providers) {
            if (provider.equals("password")) {
                flag = true;
                break;
            }
        }
        if(!flag){
            mProgressDialog.dismiss();
            Snackbar.make(mConstraintLayout, "Account with Email Address Doesn't Exists.", Snackbar.LENGTH_LONG ).show();
            return;
        }
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                        mProgressDialog.dismiss();
                        Snackbar.make(mConstraintLayout, "Check your Emails for Reset Password Email.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("LOGIN", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        loginActivity();
                                    }})
                                .setActionTextColor(Color.GREEN)
                                .show();
                    }else{
                        mProgressDialog.dismiss();
                        Snackbar.make(mConstraintLayout, "Failed to Send Reset Email", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        checkAccountEmailExistsInFirebase(email);
                                    }})
                                .setActionTextColor(Color.RED)
                                .show();

                    }
                }
            });
    }

    private void loginActivity() {
        Intent activity = new Intent(this,LoginActivity.class);
        startActivity(activity);
        finish();
        Log.d( TAG,"reset email sent successfully ");
    }

    private class MyTextWatcher implements TextWatcher {

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            validateEmail();
        }
    }
}
