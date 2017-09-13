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
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewEditProfileActivity extends AppCompatActivity {

    private String TAG =ViewEditProfileActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private ImageView getProfilePicture;
    private RadioButton getFGender;
    private RadioButton getMGender;
    private TextView addPicture;
    private TextView getDisplayName;
    private TextView getFullName;
    private TextView getEmail;
    private EditText getDescrip;
    private EditText getPhone;
    private EditText getHome_address;
    private Button EditButton;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private String mUsersKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_profile1);

        /* entertainment key
        Intent intent = getIntent();
        mUsersKey = intent.getStringExtra("usersKey");
        Log.d(TAG, "onCreate: "+ mUsersKey);*/

        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage =FirebaseStorage.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users");
        mUsersKey = mFirebaseAuth.getCurrentUser().getUid();

        // Initialize references to views
        mToolbar = findViewById(R.id.ProfileToolbar1);
        getProfilePicture = findViewById(R.id.imageView);
        addPicture = findViewById(R.id.addImageTextView);
        getDisplayName = findViewById(R.id.displayNameEditText);
        getFullName = findViewById(R.id.fullNameEditText);
        getEmail = findViewById(R.id.emailEditText);
        EditButton = findViewById(R.id.submitChangesButton);
        getDescrip =findViewById(R.id.describeEditText);
        getPhone = findViewById(R.id.phoneEditText);
        getHome_address = findViewById(R.id.homeAddressEditText);
        getFGender = findViewById(R.id.femaleRadioButton);
        getMGender = findViewById(R.id.maleRadioButton);

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
                getEmail.setText(user_email);
                getFullName.setText(user_name);

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
