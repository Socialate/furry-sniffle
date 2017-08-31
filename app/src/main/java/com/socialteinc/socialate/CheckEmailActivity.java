package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class CheckEmailActivity extends AppCompatActivity {

    private static final String TAG = CheckEmailActivity
            .class.getSimpleName();

    // References variables
    private EditText mEmailEditText;
    private TextInputLayout mTextInputLayout;
    private Button mNextButton;
    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;
    private ConstraintLayout mConstraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);

        // Initialize references to views
        mEmailEditText = findViewById(R.id.emailEditText);
        mTextInputLayout = findViewById(R.id.checkEmailTextInputLayout);
        mNextButton = findViewById(R.id.nextButton);
        mToolbar = findViewById(R.id.checkEmailToolbar);
        mProgressDialog = new ProgressDialog(this);
        mConstraintLayout = findViewById(R.id.checkEmailConstraintLayout);

        mEmailEditText.addTextChangedListener(new MyTextWatcher());

        //Initialise toolbar
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Email Registration");

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateEmail()){return;}
                String email = mEmailEditText.getText().toString();

                mProgressDialog.setTitle("Checking Email Address");
                mProgressDialog.setMessage("Please wait while we check email.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                checkAccountEmailExistsInFirebase(email);

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
                    mProgressDialog.dismiss();
                    Snackbar.make(mConstraintLayout, "Account with Email Address Already Exists.", Snackbar.LENGTH_LONG ).show();
                }
            }
        });
    }

    /*
     This is the signUpUser method, it takes a user to the register email activity
     Author Francis Tinashe Mudavanhu
    */
    private void signUpUser(String email) {

        mProgressDialog.dismiss();
        Intent emailRegisterIntent = new Intent(CheckEmailActivity.this, RegisterEmailActivity.class);
        emailRegisterIntent.putExtra("signUpEmailAddress", email);
        emailRegisterIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(emailRegisterIntent);
    }

    private boolean validateEmail() {
        String email = mEmailEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            mTextInputLayout.setError(getString(R.string.err_msg_email));
            return false;
        } else {
            mTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    static boolean isValidEmail(CharSequence target) {
        /* ***
         * Little helper method for verifying if the password pattern matches
         * ***/
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(target);
        return matcher.matches() && target != null ;
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
