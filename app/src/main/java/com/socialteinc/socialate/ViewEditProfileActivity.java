package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewEditProfileActivity extends AppCompatActivity {

    private String TAG =ViewEditProfileActivity.class.getSimpleName();

    private CircleImageView getProfilePicture;
    private TextView getDisplayName;
    private TextView getFullName;
    private TextView getEmail;
    private Button EditButton;
    private Toolbar mToolbar;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private String mUsersKey;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_profile_activity);

        // entertainment key
        Intent intent = getIntent();
        mUsersKey = intent.getStringExtra("usersKey");
        Log.d(TAG, "onCreate: "+ mUsersKey);


        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage =FirebaseStorage.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users");
        mUsersKey = mFirebaseAuth.getCurrentUser().getUid();

        // Initialize references to views
        mToolbar = findViewById(R.id.viewProfileToolbar);
        getProfilePicture = findViewById(R.id.ProfileImageView);
        getDisplayName = findViewById(R.id.DisplayNameTextView);
        getFullName = findViewById(R.id.FullNameTextView);
        getEmail = findViewById(R.id.emailTextView);
        EditButton = findViewById(R.id.editButton);

        //Initialise toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View and Edit Profile");

        // progress bar
        mProgressDialog = new ProgressDialog(this);

        // Display current user profile details
        mUsersDatabaseReference.child(mUsersKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String user_display = (String) dataSnapshot.child("displayName").getValue();
                String user_name = (String) dataSnapshot.child("name").getValue();
                String user_image = (String) dataSnapshot.child("profileImage").getValue();
                String user_email = mFirebaseAuth.getCurrentUser().getEmail();

                getDisplayName.setText(user_display);
                getEmail.setText( user_email);
                getFullName.setText(user_name);

                //System.out.println(user_image);

                Picasso.with(getApplicationContext())
                        .load(user_image)
                        .into(getProfilePicture);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Click button to edit profile picture
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoEditAccount();
            }
        });

    }

    /**
     *
     */
    private void gotoEditAccount(){

    }
}
