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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProfileActivity extends AppCompatActivity {

    private String TAG = CreateProfileActivity.class.getSimpleName();

    //Reference variables
    private CircleImageView mProfilePicture;
    private TextView mProfilePictureButton;
    private EditText mDisplayNameEditText;
    private EditText mFullNameEditText;
    private Button mSubmitButton;
    private Uri mImageUri = null;
    private Toolbar mToolbar;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private ProgressDialog mProgressDialog;

    private static int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        // Initialize references to views
        mProfilePicture = findViewById(R.id.profileImageView);
        mProfilePictureButton = findViewById(R.id.setupPictureButton);
        mDisplayNameEditText = findViewById(R.id.displayNameEditText);
        mFullNameEditText = findViewById(R.id.fullNameEditText);
        mSubmitButton = findViewById(R.id.setupSubmitButton);
        mToolbar = findViewById(R.id.createProfileToolbar);

        mProgressDialog = new ProgressDialog(this);

        //Initialise toolbar
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profile Setup");
        }

        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage =FirebaseStorage.getInstance();

        mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users");
        mStorageReference = mFirebaseStorage.getReference().child("profile_images");

        mProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSetupAccount();
            }
        });
    }

    public void galleryIntent() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    /**
     * this function creates a profile for the newly created account making
     * sure we have a profile picture, username and the full name of the user.
     */
    public void startSetupAccount() {

        assert mFirebaseAuth.getCurrentUser() != null;
        final String user_id = mFirebaseAuth.getCurrentUser().getUid();
        final String displayName = mDisplayNameEditText.getText().toString().trim();
        final String name = mFullNameEditText.getText().toString().trim();

        if(mImageUri == null){
            mImageUri = Uri.parse("android.resourse://com.socialteinc.socialate/drawable/default_avatar.png");
        }

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(displayName)&& mImageUri != null){

            mProgressDialog.setTitle("Setting Up Profile");
            mProgressDialog.setMessage("Please wait while we setup your profile");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            Log.d("MyAPP","started Upload");

            StorageReference filepath = mStorageReference.child(user_id);

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.d("MyAPP","Upload is successful");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    mUsersDatabaseReference.child(user_id).child("name").setValue(name);
                    mUsersDatabaseReference.child(user_id).child("displayName").setValue(displayName);
                    assert downloadUrl != null;
                    mUsersDatabaseReference.child(user_id).child("profileImage").setValue(downloadUrl.toString());
                    mProgressDialog.dismiss();

                    Intent mainIntent = new Intent(CreateProfileActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Log.d("MyAPP","Upload failed");
                    Toast.makeText(CreateProfileActivity.this, "Failed to Create Profile, Try Again.", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){

            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();
                mProfilePicture.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(CreateProfileActivity.this, "Failed to get profile picture, Try Again.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
