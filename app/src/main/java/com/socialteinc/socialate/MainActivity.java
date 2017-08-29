package com.socialteinc.socialate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // References variables
    private RecyclerView mEntertainmentSpotRecyclerView;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;

    // Firebase instance variables
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mEventsDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Initialize references to views
        mToolbar = findViewById(R.id.mainPageToolBar);
        mProgressBar = findViewById(R.id.progressBar);
        mEntertainmentSpotRecyclerView = findViewById(R.id.entertainmentSpotRecyclerView);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Socialate");


        // Initialize  RecyclerView
        mEntertainmentSpotRecyclerView.setHasFixedSize(true);
        mEntertainmentSpotRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // Initialize Firebase components
        FirebaseApp.initializeApp(this);
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mEventsDatabaseReference = mFireBaseDatabase.getReference().child("Entertainments");
        mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users");

        mUsersDatabaseReference.keepSynced(true);
        mEventsDatabaseReference.keepSynced(true);

        // Initialize Firebase AuthStateListener to listen for changes in authentication
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user == null){

                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                    finish();
                }
            }
        };

        checkProfileExist();
    }

    /**
     * This method checks if a user has created a profile in the database
     * and if not the user is redirected to the pofile set up page
     * before they can look at the data on the application
     */
    private void checkProfileExist() {

        if(mFirebaseAuth.getCurrentUser() != null){

            final String user_id = mFirebaseAuth.getCurrentUser().getUid();
            mUsersDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(!dataSnapshot.hasChild(user_id)){

                        Intent createProfileIntent = new Intent(MainActivity.this,CreateProfileActivity.class);
                        createProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(createProfileIntent);
                        finish();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);

        FirebaseRecyclerAdapter<Entertainment, EntertainmentSpotAdapterViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Entertainment, EntertainmentSpotAdapterViewHolder>(
                Entertainment.class,
                R.layout.item_event,
                EntertainmentSpotAdapterViewHolder.class,
                mEventsDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(EntertainmentSpotAdapterViewHolder viewHolder, Entertainment model, int position) {

                final String entertainmentKey = getRef(position).getKey();

                viewHolder.setName(model.getName());
                viewHolder.setOwner(model.getAuthor());
                viewHolder.setPhotoUrl(model.getPhotoUrl());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Intent eventIntent = new Intent(MainActivity.this, EventActivity.class);
//                        eventIntent.putExtra("event_key", entertainmentKey);
//                        startActivity(eventIntent);
                    }
                });
            }
        };

        mEntertainmentSpotRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_add_entertainment){
            onAddEntertainment();
            return true;
        }
        if(id == R.id.action_logout){
            onLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This function launches add entertainment activity to create a new spot
     */
    private void onAddEntertainment(){
        Intent addEntertainmentIntent = new Intent(this, AddEntertainmentActivity.class);
        startActivity(addEntertainmentIntent);
    }

    /**
     * this function logs users out of firebase and the app.
     */
    private void onLogout() { mFirebaseAuth.signOut(); }

    public static class EntertainmentSpotAdapterViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public EntertainmentSpotAdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String title){
            TextView event_title = mView.findViewById(R.id.titleTextView);
            event_title.setText(title);
        }

        public void setOwner(String author){
            TextView event_author = mView.findViewById(R.id.ownerTextView);
            event_author.setText(author);
        }

        public void setPhotoUrl(String image){
            ImageView event_image = mView.findViewById(R.id.photoImageView);
            Glide.with(event_image.getContext())
                    .load(image)
                    .into(event_image);
        }
    }



}