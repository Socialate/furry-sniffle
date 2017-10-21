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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    // Firebase instance variables
    private FirebaseAuth mAuth;

    //Reference variables
    private ProgressDialog mProgressDialog;
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private ConstraintLayout mConstraintLayout;
    private SignInButton mGoogleSignInButton;
    private LoginButton mFacebookLoginButton;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 1;

    //intentService variables
    private connect_receiver connect_receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);

        //Initialise reference variables
        mConstraintLayout = findViewById(R.id.loginConstraintLayout);
        inputEmail = findViewById(R.id.email_field);
        inputPassword = findViewById(R.id.password_field);
        inputLayoutEmail = findViewById(R.id.loginEmailTextInputLayout);
        inputLayoutPassword = findViewById(R.id.loginPasswordTextInputLayout);
        mGoogleSignInButton = findViewById(R.id.googleButton);
        mFacebookLoginButton = findViewById(R.id.facebookButton);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        // Google Button Setup
        TextView textView = (TextView) mGoogleSignInButton.getChildAt(0);
        textView.setText(R.string.continue_with_google);
        textView.setPadding(0,0,10,0);
        textView.setTextSize(18);

        // ===== GOOGLE SIGN IN =====
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Snackbar.make(mConstraintLayout, "Google Connection Failed.", Snackbar.LENGTH_LONG ).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        // ===== FACEBOOK SIGN IN =====
        // Configure Facebook Login
        mCallbackManager = CallbackManager.Factory.create();

        mFacebookLoginButton.setReadPermissions("email", "public_profile");
        mFacebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                mProgressDialog.setTitle("Logging In");
                mProgressDialog.setMessage("Setting up account...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                //Log.d(TAG, "facebook:onSuccess:" + loginResult);
                FirebaseAuthWithFacebook(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

                //Log.d(TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {

                //Log.d(TAG, "facebook:onError", error);
            }
        });

        startIntentService();
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
                            //Log.d("login activity", "signInWithEmail:success *******");
                            Intent activity = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(activity);
                            finish();

                        } else {
                            mProgressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            //Log.w("login activity", "signInWithEmail:failure", task.getException());
                            Snackbar.make(mConstraintLayout, "Authentication Failed, Invalid Email or Password!", Snackbar.LENGTH_LONG ).show();

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
        Intent activity = new Intent(this,RegisterActivity.class);
        startActivity(activity);
        //Log.d("login activity"," Need an account click successful");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Google Sign In was successful.
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                mProgressDialog.setTitle("Logging In");
                mProgressDialog.setMessage("Setting up account...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
            } else {
                // Google Sign In failed.
                Snackbar.make(mConstraintLayout, "Google Connection Failed.", Snackbar.LENGTH_LONG ).show();
            }

        } else {

           mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mProgressDialog.dismiss();
                            startActivity(mainIntent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(mConstraintLayout, "Google Connection Failed.", Snackbar.LENGTH_LONG ).show();
                        }
                    }
                });
    }

    private void FirebaseAuthWithFacebook(AccessToken token) {
        //Log.d(TAG, "FirebaseAuthWithFacebook:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success.
                            //Log.d(TAG, "signInWithCredential:success");
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mProgressDialog.dismiss();
                            startActivity(mainIntent);
                            finish();
                        } else {
                            // Sign in failed
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(mConstraintLayout, "Facebook Connection Failed.", Snackbar.LENGTH_LONG ).show();
                        }

                    }
                });
    }

    public void resetPassword(View v){
        Intent activity = new Intent(this,ResetPasswordActivity.class);
        startActivity(activity);
        //Log.d("login activity"," reset password click successful");

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

    private void startIntentService(){
        //intentService
        intentFilter = new IntentFilter(connect_receiver.PROCESS_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        connect_receiver = new connect_receiver();
        registerReceiver(connect_receiver,intentFilter);
        Intent service = new Intent(getApplicationContext(), connection_service.class);
        startService(service);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connect_receiver);
    }

    public class connect_receiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.socialteinc.socialate.intent.action.PROCESS_RESPONSE";
        boolean response = false;
        View fb_button = findViewById(R.id.facebookButton);
        View gmail_button = findViewById(R.id.googleButton);
        View reset_pwd = findViewById(R.id.resetPasswordTextView);
        View need_acc = findViewById(R.id.creatAccountTextView);
        View login_btn = findViewById(R.id.SinginButton);

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean response1 = intent.getBooleanExtra("response",true);
            if((!response1) && (response1 != response)){
                Snackbar sb = Snackbar.make(findViewById(R.id.loginConstraintLayout), "Oops, No data connection?", Snackbar.LENGTH_LONG);
                View v = sb.getView();
                v.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
                sb.show();
                fb_button.setClickable(false);
                gmail_button.setClickable(false);
                reset_pwd.setClickable(false);
                need_acc.setClickable(false);
                login_btn.setClickable(false);
            }
            else if((response1) && (response1 != response) ){
                fb_button.setClickable(true);
                gmail_button.setClickable(true);
                reset_pwd.setClickable(true);
                need_acc.setClickable(true);
                login_btn.setClickable(true);
            }
            response = response1;
        }
    }
}
