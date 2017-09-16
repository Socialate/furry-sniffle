package com.socialteinc.socialate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

public class ViewEntertainmentActivity extends AppCompatActivity {

    private String TAG =ViewEntertainmentActivity.class.getSimpleName();

    // References variables
    private ImageView mEntertainmentImage;
    private TextView mEntertainmentOwner;
    private TextView mEntertainmentDescription;
    private TextView mEntertainmentAddress;
    private Toolbar mToolbar;

    // Firebase instance variables
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mEventsDatabaseReference;

    private String mEntertainmentKey;
    private String mEntertainmentName;


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

        // Initialize firebase
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFireBaseDatabase.getReference().child("Entertainments");

        mToolbar = findViewById(R.id.ViewAddedAreaToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mEntertainmentName);

        mEventsDatabaseReference.child(mEntertainmentKey).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String owner = (String) dataSnapshot.child("owner").getValue();
                String name = (String) dataSnapshot.child("name").getValue();
                String description = (String) dataSnapshot.child("description").getValue();
                String address = (String) dataSnapshot.child("address").getValue();
                String photoUrl = (String) dataSnapshot.child("photoUrl").getValue();
                String uid = (String) dataSnapshot.child("uid").getValue();
                String author = (String) dataSnapshot.child("author").getValue();

                if(owner.equals("")){
                    ((findViewById(R.id.ownerLinearlayout))).setVisibility(View.GONE);
                }
                else{

                    mEntertainmentOwner.setText(owner);
                }

                mEntertainmentOwner.setText(owner);
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


    }
}