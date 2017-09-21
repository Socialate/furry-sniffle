package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class ViewOtherUserProfile extends AppCompatActivity{

    private String TAG =ViewEntertainmentActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private ImageView getProfilePicture;
    private TextView getDisplayName;
    private TextView getFullName;
    private EditText getDescrip;
    private String ownerUID;
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
        ownerUID = intent.getStringExtra("entertainmentUploader");

        // Initialize references to views
        mToolbar = findViewById(R.id.ProfileToolbar2);
        getProfilePicture = findViewById(R.id.imageView2);
        getDisplayName = findViewById(R.id.displayNameTextView);
        getFullName = findViewById(R.id.fullNameTextView);
        getDescrip = findViewById(R.id.describeEditText);
        //ownerUID = findViewById(R.id.ownerTextView);

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
        mProfileDatabaseReference.child(ownerUID).addValueEventListener(new ValueEventListener() {
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
