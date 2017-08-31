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
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Date;

public class AddEntertainmentActivity extends AppCompatActivity {

    private String TAG =AddEntertainmentActivity.class.getSimpleName();
    private static final String ANONYMOUS = "anonymous";


    private ProgressDialog mProgressDialog;
    private ImageView mEntertainmentImageView;
    private EditText mEntertainmentTitleEditText;
    private EditText mEntertainmentDescriptionEditText;
    private EditText mEntertainmentAddress;
    private EditText mEntertainmentOwner;
    private Button mChooseImageButton;
    private Button mSubmitButton;
    private String mAuthor;
    private Uri imageUri;

    // Firebase instance variables
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mEntertainmentsDatabaseReference;
    private DatabaseReference mUserDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private static final int GALLERY_REQUEST_CODE = 1;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entertainment);

        mAuthor = ANONYMOUS;

        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage =FirebaseStorage.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mEntertainmentsDatabaseReference = mFireBaseDatabase.getReference().child("Entertainments");
        mUserDatabaseReference = mFireBaseDatabase.getReference().child("users").child(mFirebaseUser.getUid());
        mStorageReference = mFirebaseStorage.getReference().child("Entertainment_images");


        // Initialize references to views
        mEntertainmentImageView = findViewById(R.id.EntertainmentImageView);
        mEntertainmentOwner = findViewById(R.id.NameOfOwnerEditText);
        mEntertainmentTitleEditText = findViewById(R.id.entertainmentTitleEditText);
        mEntertainmentDescriptionEditText = findViewById(R.id.entertainmentDescrptionEditText);
        mEntertainmentAddress = findViewById(R.id.entertainmentAddressEditText);
        mChooseImageButton = findViewById(R.id.chooseImageButton);
        mSubmitButton = findViewById(R.id.addEntertainmentAreaButton);
        mToolbar = findViewById(R.id.addEntertainmentToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Entertainment Area");

        // Initialize progress bar
        mProgressDialog = new ProgressDialog(this);

        //Choose image button select image
        mChooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), GALLERY_REQUEST_CODE);

            }
        });
        // Submit button create Entertainment
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressDialog.setTitle("Creating Entertainment Spot");
                mProgressDialog.setMessage("Please wait while we add the new entertainment spot.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                // Create Entertainment on click
                startPosting();
            }
        });

    }



    private void startPosting() {

        final String title_val = mEntertainmentTitleEditText.getText().toString();
        final String owner_val = mEntertainmentOwner.getText().toString();
        final String description_val = mEntertainmentDescriptionEditText.getText().toString();
        final String address_val = mEntertainmentAddress.getText().toString();

        if(imageUri == null){
           imageUri = Uri.parse("android.resourse://com.socialteinc.socialate/drawable/eventplaceholder.jpg");
        }

        if(!TextUtils.isEmpty(mAuthor) && !TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(address_val) && !TextUtils.isEmpty(description_val) &&  imageUri != null){

            Log.d("MyAPP","started Upload");

            StorageReference filepath = mStorageReference.child(imageNameGenerator());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.d("MyAPP","Upload is successful");

                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    mUserDatabaseReference.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            mAuthor = (String) dataSnapshot.child("name").getValue();

                            assert downloadUrl != null;

                            Entertainment Entertainment = new Entertainment(
                                    mFirebaseUser.getUid(),
                                    title_val,
                                    address_val,
                                    null,
                                    null,
                                    description_val,
                                    downloadUrl.toString(),
                                    mAuthor,
                                    owner_val,
                                    null );

                            mEntertainmentsDatabaseReference.push().setValue(Entertainment).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        mProgressDialog.dismiss();
                                        finish();
                                    } else {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Failed to create Entertainment spot. Try Again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mProgressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("MyAPP","Upload failed");
                }
            });
        }
    }

    private String imageNameGenerator(){

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return mFirebaseUser.getUid() + currentDateTimeString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){

            imageUri = data.getData();
            mEntertainmentImageView.setImageURI(imageUri);
        }else {
            Toast.makeText(getApplicationContext(), "Failed to get image. Try Again!", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();

        }
    }
}