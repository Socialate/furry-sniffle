package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        viewProfile();

        // Click button to edit profile picture
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoEditAccount();
            }
        });

    }

    /**
     * This method displays all the profile details of the current user
     */
    private void viewProfile(){
        // Get user details from the database
        //final String user_id = mFirebaseAuth.getCurrentUser().getUid();
       final String[] user_display = new String[3];
       // String Name =  mUsersDatabaseReference.child(user_id).child("name").toString();
        //String email = mFirebaseAuth.getCurrentUser().getEmail();
        //Uri picture = mFirebaseAuth.getCurrentUser().getPhotoUrl();

        //getProfilePicture.setImageURI(picture);

       // mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users").child(user_id);

        mUsersDatabaseReference.child(mUsersKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               /* User user = dataSnapshot.getValue(User.class);

                // details fetched from database
                user_display[0] = user.DisplayName;
                user_display[1] = user.Name;*/
                user_display[0] = (String) dataSnapshot.child("displayName").getValue();
                user_display[1] = (String) dataSnapshot.child("name").getValue();
                String user_image = (String) dataSnapshot.child("photoUrl").getValue();
                user_display[2] = mFirebaseAuth.getCurrentUser().getEmail();


                getDisplayName.setText(user_display[0]);
                getEmail.setText( user_display[1]);
                getFullName.setText(user_display[2]);

                Picasso.with(getApplicationContext())
                        .load(user_image)
                        .into(getProfilePicture);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       // mUsersDatabaseReference.addValueEventListener(listener);

        // Display user details on the app


        //System.out.println("Display name: "+);
        //System.out.println("Full Name   : "+Name);

    }

    /**
     *
     */
    private void gotoEditAccount(){

    }
}
