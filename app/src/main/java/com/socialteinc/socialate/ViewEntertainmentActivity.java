package com.socialteinc.socialate;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

public class ViewEntertainmentActivity extends AppCompatActivity {

    private String TAG =ViewEntertainmentActivity.class.getSimpleName();

    // References variables
    private ImageView mEntertainmentImage;
    private TextView mEntertainmentOwner;
    private TextView mEntertainmentDescription;
    private TextView mEntertainmentAddress;
    private TextView mCommentorName;
    private AutoCompleteTextView mCommentTextView;
    private ImageView mCommentorImage;
    private Toolbar mToolbar;
    private FloatingActionButton mLikeButton;
    private EditText mCommentEditText;
    private ImageButton mCommentButton;
    private Boolean mProcessLike = false;


    // Firebase instance variables
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private DatabaseReference mLikesDatabaseReference;
    private DatabaseReference mCommentsDatabaseReference;

    private String mEntertainmentKey;
    private String mEntertainmentName;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_entertainment_area);

        // entertainment key
        Intent intent = getIntent();
        mEntertainmentName = intent.getStringExtra("entertainmentName");
        mEntertainmentKey = intent.getStringExtra("entertainmentKey");
        Log.d(TAG, "onCreate: "+ mEntertainmentKey);

        // Initialize references to views
        mEntertainmentImage = findViewById(R.id.ViewAddedAreaImageView);
        mEntertainmentOwner = findViewById(R.id.ViewAddedAreaOwnerText);
        mEntertainmentDescription = findViewById(R.id.ViewAddedAreaDescText);
        mEntertainmentAddress = findViewById(R.id.ViewAddedAreaAddressText);
        mLikeButton = findViewById(R.id.likeFloatingActionButton);
        mCommentEditText = findViewById(R.id.commentEditText);
        mCommentButton = findViewById(R.id.commentImageButton);
        mCommentorName = findViewById(R.id.commentorNameTextView);
        mCommentTextView = findViewById(R.id.commentMultiAutoCompleteTextView);
        mCommentorImage = findViewById(R.id.commentorProfileImageView);


        // Initialize firebase
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mEventsDatabaseReference = mFireBaseDatabase.getReference().child("Entertainments");
        mLikesDatabaseReference = mFireBaseDatabase.getReference().child("Likes");
        mCommentsDatabaseReference = mFireBaseDatabase.getReference().child("Comments");

        mToolbar = findViewById(R.id.ViewAddedAreaToolbar);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(mEntertainmentName);
        }

        mLikesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assert mFirebaseAuth.getCurrentUser() != null;
                if(dataSnapshot.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).exists()){
                    mLikeButton.setImageResource(R.drawable.ic_fav);
                    mLikeButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));

                }else {
                    mLikeButton.setImageResource(R.drawable.ic_white_fav);
                    mLikeButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mEventsDatabaseReference.child(mEntertainmentKey).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String owner = (String) dataSnapshot.child("owner").getValue();
                String name = (String) dataSnapshot.child("name").getValue();
                String description = (String) dataSnapshot.child("description").getValue();
                String address = (String) dataSnapshot.child("address").getValue();
                String photoUrl = (String) dataSnapshot.child("photoUrl").getValue();
                final String uid = (String) dataSnapshot.child("uid").getValue();
                String author = (String) dataSnapshot.child("author").getValue();

                mEntertainmentOwner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileViewIntent = new Intent(getApplicationContext(), ViewOtherUserProfile.class);
                        profileViewIntent.putExtra("entertainmentUploader", uid);
                        startActivity(profileViewIntent);
                    }
                });

                mEntertainmentOwner.setText(author);
                mEntertainmentDescription.setText(description);
                mEntertainmentAddress.setText(address);

                Picasso.with(getApplicationContext())
                        .load(photoUrl)
                        .into(mEntertainmentImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Display current user comments
        mCommentsDatabaseReference.child(mEntertainmentKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               // String user_name = (String) dataSnapshot.child("name").getValue();
                //String user_image = (String) dataSnapshot.child("profileImage").getValue();
                String user_comment = (String) dataSnapshot.child("Comments").child(mFirebaseAuth.getCurrentUser().getUid()).getValue();

                System.out.println("aaa: "+user_comment);
                System.out.println("aaa: "+dataSnapshot.getValue());


               // mCommentorName.setText(user_name);
                mCommentTextView.setText(user_comment);

                /*Picasso.with(getApplicationContext())
                        .load(user_image)
                        .into(mCommentorImage);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLike();
            }
        });

        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processComment();
            }
        });

    }

    private void processLike() {
        mProcessLike = true;
        mLikesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mProcessLike && mFirebaseAuth.getCurrentUser() != null) {
                    if (!dataSnapshot.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).exists()) {
                        mLikesDatabaseReference.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).setValue("liked");
                        mProcessLike = false;
                    } else {
                        mLikesDatabaseReference.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).removeValue();
                        mProcessLike = false;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void processComment(){
        final String comment = mCommentEditText.getText().toString();
        mCommentsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!TextUtils.isEmpty(comment) && (mFirebaseAuth.getCurrentUser() != null)) {
                    if (!dataSnapshot.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).exists()) {

                        mCommentsDatabaseReference.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).setValue(comment);
                        mCommentEditText.setText("");
                        Toast.makeText(ViewEntertainmentActivity.this,
                                "Successful", Toast.LENGTH_LONG).show();
                        //mCommentEditText.clearComposingText();

                    } /*else {
                        Toast.makeText(ViewEntertainmentActivity.this,
                                "Write a comment in the text box then press the send button", Toast.LENGTH_LONG).show();
                    }*/
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
