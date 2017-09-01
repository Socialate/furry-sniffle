package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewEditProfileActivity extends AppCompatActivity {

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

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_profile_activity);

        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage =FirebaseStorage.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users").child(mFirebaseUser.getUid());
        mStorageReference = mFirebaseStorage.getReference().child("profile_images");

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
        final String user_id = mFirebaseAuth.getCurrentUser().getUid();
        final String[] user_display = new String[2];
        String Name =  mUsersDatabaseReference.child(user_id).child("name").toString();
        String email = mFirebaseAuth.getCurrentUser().getEmail();
        //Uri picture = mFirebaseAuth.getCurrentUser().getPhotoUrl();

        //getProfilePicture.setImageURI(picture);

        mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users").child(user_id);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                // details fetched from database
                user_display[0] = user.DisplayName;
                user_display[1] = user.Name;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mUsersDatabaseReference.addValueEventListener(listener);

        // Display user details on the app
        getDisplayName.setText(user_display[0]);
        getEmail.setText(email);
        getFullName.setText(user_display[1]);
        System.out.println("Display name: "+user_display[0]);
        System.out.println("Full Name   : "+Name);

    }

    /**
     *
     */
    private void gotoEditAccount(){

    }
}
