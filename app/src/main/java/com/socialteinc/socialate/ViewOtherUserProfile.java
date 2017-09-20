package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewOtherUserProfile extends AppCompatActivity{

    private String TAG =ViewEntertainmentActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private ImageView getProfilePicture;
    private TextView getDisplayName;
    private TextView getFullName;
    private EditText getDescrip;
    private String getOwnerName;
    private String mUsersKey;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mProfileDatabaseReference;
    private FirebaseStorage mFirebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_profile_activity);

        // get identifier key
        Intent intent = getIntent();
        mUsersKey = intent.getStringExtra("entertainmentKey");
        getOwnerName = intent.getStringExtra("entertainmentUploader");
        Log.d(TAG, "onCreate: "+ mUsersKey);

        // Initialize references to views
        mToolbar = findViewById(R.id.ProfileToolbar2);
        getProfilePicture = findViewById(R.id.imageView2);
        getDisplayName = findViewById(R.id.displayNameEditText2);
        getFullName = findViewById(R.id.fullNameEditText2);
        getDescrip = findViewById(R.id.describeEditText2);
        //getOwnerName = findViewById(R.id.ownerTextView);

        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        //mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //mProfileDatabaseReference = mFireBaseDatabase.getReference().child("users").child(mFirebaseUser.getUid());
        mProfileDatabaseReference = mFireBaseDatabase.getReference().child("users");
        //mUsersKey = mFirebaseAuth.

        //Initialise toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Profile");

        // progress bar
        mProgressDialog = new ProgressDialog(this);

        // Display current user profile details
        mProfileDatabaseReference.child(mUsersKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String user_display = (String) dataSnapshot.child("displayName").getValue();
                String user_name = (String) dataSnapshot.child("name").getValue();
                String user_image = (String) dataSnapshot.child("profileImage").getValue();
                String user_descrip = (String) dataSnapshot.child("description").getValue();

                getDisplayName.setText(user_display);
                getFullName.setText(user_name);
                getDescrip.setText(user_descrip);

                Picasso.with(getApplicationContext())
                        .load(user_image)
                        .into(getProfilePicture);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}
