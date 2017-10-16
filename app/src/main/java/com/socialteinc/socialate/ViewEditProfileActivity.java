package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.app.Activity.RESULT_OK;

public class ViewEditProfileActivity extends AppCompatActivity {

    private String TAG =ViewEditProfileActivity.class.getSimpleName();
    private static final String ANONYMOUS = "anonymous";

    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private ImageView getProfilePicture;
    private Uri imageUri;
    private Spinner getGender;
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
    private DatabaseReference mUserDatabaseReference;
    private DatabaseReference mProfileDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private static final int GALLERY_REQUEST_CODE = 1;
    private String mAuthor;
    public String mUsersKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_profile1);

        /* entertainment key
        Intent intent = getIntent();
        mUsersKey = intent.getStringExtra("usersKey");
        Log.d(TAG, "onCreate: "+ mUsersKey);*/
        mAuthor = ANONYMOUS;

        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage =FirebaseStorage.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mProfileDatabaseReference = mFireBaseDatabase.getReference().child("users");
        //mUserDatabaseReference = mFireBaseDatabase.getReference().child("users").child(mFirebaseUser.getUid());
        mStorageReference = mFirebaseStorage.getReference().child("Entertainment_images");

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
        getGender = findViewById(R.id.spinnerGender);

        //Initialise toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View and Edit Profile");

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
                String user_phone = (String) dataSnapshot.child("phone number").getValue();
                String user_address = (String) dataSnapshot.child("physical address").getValue();
                String user_gender = (String) dataSnapshot.child("gender").getValue();
                String user_email = mFirebaseAuth.getCurrentUser().getEmail();

                getDisplayName.setText(user_display);
                getEmail.setText(user_email);
                getFullName.setText(user_name);
                getDescrip.setText(user_descrip);
                getHome_address.setText(user_address);
                getPhone.setText(user_phone);
                getGender.setSelection(((ArrayAdapter<String>)getGender.getAdapter()).getPosition(user_gender));

                Picasso.with(getApplicationContext())
                        .load(user_image)
                        .into(getProfilePicture, new Callback() {
                            @Override
                            public void onSuccess() {
                                (findViewById(R.id.ViewProfileProgress4)).setVisibility(View.GONE);
                            }
                            @Override
                            public void onError() {

                            }
                        });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Click textview to select a new image
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), GALLERY_REQUEST_CODE);

            }
        });
        // Submit button for updating profile
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.setTitle("Updating Profile");
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                // update account changes on click
                updateAccount();
            }
        });

    }

    public void updateAccount(){
        final String user_id = mFirebaseAuth.getCurrentUser().getUid();
        final String full_name = getFullName.getText().toString();
        final String display_name = getDisplayName.getText().toString();
        final String email = getEmail.getText().toString();
        final String description = getDescrip.getText().toString();
        final String phone_number = getPhone.getText().toString();
        final String home_address = getHome_address.getText().toString();
        final String gender_selected = getGender.getSelectedItem().toString();

       /* if(imageUri == null){
            imageUri = Uri.parse("android.resourse://com.socialteinc.socialate/drawable/eventplaceholder.jpg");
        }*/

        if(!TextUtils.isEmpty(mAuthor) && !TextUtils.isEmpty(full_name) && !TextUtils.isEmpty(display_name) && !TextUtils.isEmpty(email)) {

            //Log.d("MyAPP", "started Upload");

            mProfileDatabaseReference.child(user_id).child("name").setValue(full_name);
            mProfileDatabaseReference.child(user_id).child("displayName").setValue(display_name);
            mProfileDatabaseReference.child(user_id).child("description").setValue(description);
            mProfileDatabaseReference.child(user_id).child("phone number").setValue(phone_number);
            mProfileDatabaseReference.child(user_id).child("physical address").setValue(home_address);
            mProfileDatabaseReference.child(user_id).child("gender").setValue(gender_selected);

            if(imageUri != null){
                StorageReference filepath = mStorageReference.child(user_id);

                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Log.d("MyAPP","Upload is successful");
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        assert downloadUrl != null;
                        mProfileDatabaseReference.child(user_id).child("profileImage").setValue(downloadUrl.toString());

                        mProgressDialog.dismiss();
                        Toast.makeText(ViewEditProfileActivity.this, "Profile successfully updated", Toast.LENGTH_LONG).show();

                        Intent mainIntent = new Intent(ViewEditProfileActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgressDialog.dismiss();
                        //Log.d("MyAPP","Upload failed");
                        Toast.makeText(ViewEditProfileActivity.this, "Failed to update your profile, please try again.", Toast.LENGTH_LONG).show();
                    }
                });
            }else {
                mProgressDialog.dismiss();
                Toast.makeText(ViewEditProfileActivity.this, "Profile successfully updated", Toast.LENGTH_LONG).show();

                Intent mainIntent = new Intent(ViewEditProfileActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                finish();

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                getProfilePicture.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(ViewEditProfileActivity.this, "Failed to get profile picture, please try again.", Toast.LENGTH_LONG).show();
            }
        }
    }


}
