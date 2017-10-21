package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import pub.devrel.easypermissions.EasyPermissions;

import java.text.DateFormat;
import java.util.Date;

public class AddEntertainmentActivity extends AppCompatActivity {

    //private String TAG =AddEntertainmentActivity.class.getSimpleName();
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

    //intentService variables
    private connect_receiver connect_receiver;
    private IntentFilter intentFilter;


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
        mToolbar = findViewById(R.id.ViewAddedAreaToolbar);

        setSupportActionBar(mToolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Entertainment Area");
        }


        // Initialize progress bar
        mProgressDialog = new ProgressDialog(this);

        //Choose image button select image
        final String[] galleryPermissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        mChooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (EasyPermissions.hasPermissions(AddEntertainmentActivity.this, galleryPermissions)) {
                    Intent galleryIntent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), GALLERY_REQUEST_CODE);
                } else {
                    EasyPermissions.requestPermissions(AddEntertainmentActivity.this, "Access for storage",
                            101, galleryPermissions);
                }


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

        //starting the intent service
        startIntentService();

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
           // Log.d("MyAPP","started Upload");

            StorageReference filepath = mStorageReference.child(imageNameGenerator());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Log.d("MyAPP","Upload is successful");

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
                                        //handling layout of the successfully added area
                                        Intent mainIntent = new Intent(AddEntertainmentActivity.this, MainActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();

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
                    //Log.d("MyAPP","Upload failed");
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
        Bitmap bitmap;
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){

            imageUri = data.getData();
            mEntertainmentImageView.setImageURI(imageUri);
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(2,2)
                    .start(this);
            //mEntertainmentImageView.setImageBitmap(bitmap);
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                mEntertainmentImageView.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(AddEntertainmentActivity.this, "Failed to get profile picture, Try Again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    //This function downscales the image size
    private Bitmap getThumbnailBitmap(final String path, final int thumbnailSize) {
        Bitmap bitmap;
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            bitmap = null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / thumbnailSize;
        bitmap = BitmapFactory.decodeFile(path, opts);
        return bitmap;
    }

    public void startIntentService(){
        //intentService
        intentFilter = new IntentFilter(connect_receiver.PROCESS_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        connect_receiver = new connect_receiver();
        registerReceiver(connect_receiver,intentFilter);
        Intent service = new Intent(getApplicationContext(), connection_service.class);
        startService(service);
    }

    public class connect_receiver extends BroadcastReceiver {

        public final String PROCESS_RESPONSE = "com.socialteinc.socialate.intent.action.PROCESS_RESPONSE";
        boolean response = false;
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean response1 = intent.getBooleanExtra("response",true);
            if((!response1) && (response1 != response)){
                Snackbar sb = Snackbar.make(findViewById(R.id.addEntertainmentConstraintLayout), "Oops, No data connection?", Snackbar.LENGTH_LONG);
                View v = sb.getView();
                v.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
                sb.show();
                findViewById(R.id.addEntertainmentAreaButton).setClickable(false);
            }
            else if((response1) && response1 != response ){
                findViewById(R.id.addEntertainmentAreaButton).setClickable(true);
            }
            response = response1;
        }
    }
}
