package com.socialteinc.socialate;

import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //the user is signed in
            Log.d("MainActivity", "User is signed in redirect to a relevant UI ");
            setContentView(R.layout.main_activity);
        }
        else {
            //User is not signed in
            Log.d("MainActivity", "User is not signed in let's sign them in");
            Intent activity = new Intent(this,LoginActivity.class);
            startActivity(activity);
            finish();
        }
        Log.d("MainActivity", "successfully launched .........");
    }

}