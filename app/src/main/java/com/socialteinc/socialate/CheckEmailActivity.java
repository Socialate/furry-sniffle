package com.socialteinc.socialate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

public class CheckEmailActivity extends Activity {

    private static final String TAG = CheckEmailActivity
            .class.getSimpleName();

    // References variables
    EditText mEmailEditText;
    Button mNextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);

        // Initialize references to views
        mEmailEditText = findViewById(R.id.emailEditText);
        mNextButton = findViewById(R.id.nextButton);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmailEditText.getText().toString();
                if(!TextUtils.isEmpty(email)){
                    checkAccountEmailExistsInFirebase(email);
                }
            }
        });
    }

    /*
     This is the checkAccountEmailExistsInFirebase, it checks if an account exists
     for a specific email if it does it toasts and if it doesnt it will let a user
     sign up.
     Author Francis Tinashe Mudavanhu
     */
    private void checkAccountEmailExistsInFirebase(final String email) {
        FirebaseApp.initializeApp(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {

            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if((task.getResult().getProviders() != null && task.getResult().getProviders().isEmpty())){

                    signUpUser(email);

                }else{
                    Toast.makeText(CheckEmailActivity.this, "Email address is taken", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
     This is the signUpUser method, it takes a user to the register email activity
     Author Francis Tinashe Mudavanhu
    */
    private void signUpUser(String email) {

        Intent emailRegisterIntent = new Intent(CheckEmailActivity.this, RegisterEmailActivity.class);
        emailRegisterIntent.putExtra("signUpEmail", email);
        emailRegisterIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(emailRegisterIntent);
    }
}
