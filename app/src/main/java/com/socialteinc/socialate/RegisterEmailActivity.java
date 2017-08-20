package com.socialteinc.socialate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterEmailActivity extends Activity {

    private static final String TAG = RegisterEmailActivity.class.getSimpleName();

    // References variables
    private ProgressDialog mProgressDialog;
    private EditText mEmail;
    private EditText mPassword;
    private Button mCreateAccountButton;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        Intent intent = getIntent();
        String email = intent.getStringExtra("signUpEmailAddress");

        // Initialize references to views
        mEmail = (EditText) findViewById(R.id.loginEmailEditText);
        mPassword = (EditText) findViewById(R.id.loginPasswordEditText);
        mCreateAccountButton = (Button) findViewById(R.id.createAccountButton);

        mEmail.setText(email);

        // Initialize Firebase components
        mFirebaseAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

    }

    private void startRegister() {

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mProgressDialog.setTitle("Creating Account");
            mProgressDialog.setMessage("Please wait while we create your account.");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        // Sign up success.
                        Log.d(TAG, "createUserWithEmail:success");
                        mProgressDialog.dismiss();

                        Intent createProfileIntent = new Intent(RegisterEmailActivity.this, CreateProfileActivity.class);
                        createProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(createProfileIntent);
                        finish();

                    } else {
                        // Sign up failed.
                        mProgressDialog.hide();
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterEmailActivity.this, "Failed to Create Account, Try Again.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
