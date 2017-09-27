package com.socialteinc.socialate;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewEntertainmentActivity extends AppCompatActivity {

    private String TAG =ViewEntertainmentActivity.class.getSimpleName();

    // References variables
    private ImageView mEntertainmentImage, mNavigationImage;
    private TextView mEntertainmentOwner;
    private TextView mEntertainmentDescription;
    private TextView mEntertainmentAddress;
    private Toolbar mToolbar;
    private FloatingActionButton mLikeButton;
    private Boolean mProcessLike = false;


    // Firebase instance variables
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private DatabaseReference mLikesDatabaseReference;


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
        mNavigationImage = findViewById(R.id.navigationImageView);
        mEntertainmentOwner = findViewById(R.id.ViewAddedAreaOwnerText);
        mEntertainmentDescription = findViewById(R.id.ViewAddedAreaDescText);
        mEntertainmentAddress = findViewById(R.id.ViewAddedAreaAddressText);
        mLikeButton = findViewById(R.id.likeFloatingActionButton);


        // Initialize firebase
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mEventsDatabaseReference = mFireBaseDatabase.getReference().child("Entertainments");
        mLikesDatabaseReference = mFireBaseDatabase.getReference().child("Likes");

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

        mNavigationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMap();
            }
        });

        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLike();
            }
        });

    }

    private void launchMap() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+ mEntertainmentAddress.getText().toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
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
}
