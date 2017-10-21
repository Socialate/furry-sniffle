package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterEmailActivity extends AppCompatActivity {

    private static final String TAG = RegisterEmailActivity.class.getSimpleName();

    // References variables
    private ProgressDialog mProgressDialog;
    private EditText mEmail;
    private EditText mPassword;
    private Button mCreateAccountButton;
    private Toolbar mToolbar;
    private ConstraintLayout mConstraintLayout;
    private TextInputLayout mTextInputLayout;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;

    //intentService variables
    private connect_receiver connect_receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        Intent intent = getIntent();
        String email = intent.getStringExtra("signUpEmailAddress");

        // Initialize references to views
        mEmail = findViewById(R.id.loginEmailEditText);
        mPassword = findViewById(R.id.loginPasswordEditText);
        mCreateAccountButton = findViewById(R.id.createAccountButton);
        mToolbar = findViewById(R.id.registerEmailToolbar);
        mTextInputLayout = findViewById(R.id.registerPasswordTextInputLayout);
        mConstraintLayout = findViewById(R.id.registerEmailConstraintLayout);

        mPassword.addTextChangedListener(new MyTextWatcher());

        //Initialise toolbar
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Email Registration");
        }


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

        //starting the intentservice
        startIntentService();

    }

    /**
     * this function creates a new user account using email and paassword on firebase
     */
    private void startRegister() {

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && validatePassword()){

            mProgressDialog.setTitle("Creating Account");
            mProgressDialog.setMessage("Please wait while we create your account.");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        // Sign up success.
                        //Log.d(TAG, "createUserWithEmail:success");
                        mProgressDialog.dismiss();

                        Intent createProfileIntent = new Intent(RegisterEmailActivity.this, CreateProfileActivity.class);
                        createProfileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(createProfileIntent);
                        finish();

                    } else {
                        // Sign up failed.
                        mProgressDialog.hide();
                        //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Snackbar.make(mConstraintLayout, "Failed to Create Account, Try Again.", Snackbar.LENGTH_LONG ).show();

                    }
                }
            });
        }
    }

    private boolean validatePassword() {
        if (mPassword.getText().toString().trim().isEmpty() || mPassword.getText().toString().trim().length() < 8) {
            mTextInputLayout.setError(getString(R.string.error_password_length));
            return false;
        } else {
            mTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private class MyTextWatcher implements TextWatcher {

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            validatePassword();
        }
    }

    public void startIntentService(){
        //intentService
        intentFilter = new IntentFilter(connect_receiver.PROCESS_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        connect_receiver = new connect_receiver();
        registerReceiver(connect_receiver,intentFilter);
        Intent service = new Intent(getApplicationContext(), connection_service.class);
        startService(service);
    }

    public class connect_receiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.socialteinc.socialate.intent.action.PROCESS_RESPONSE";
        boolean response = false;
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean response1 = intent.getBooleanExtra("response",true);
            if((!response1) && (response1 != response)){
                Snackbar sb = Snackbar.make(findViewById(R.id.registerEmailConstraintLayout), "Oops, No data connection?", Snackbar.LENGTH_LONG);
                View v = sb.getView();
                v.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
                sb.show();
            }
            response = response1;
        }
    }
}
