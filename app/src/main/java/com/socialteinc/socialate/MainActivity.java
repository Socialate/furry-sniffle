package com.socialteinc.socialate;


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // References variables
    private RecyclerView mEntertainmentSpotRecyclerView;
    private Toolbar mToolbar;

    // Firebase instance variables
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mEventsDatabaseReference;
    private DatabaseReference mLikesDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Boolean mProcessLike = false;

    SharedPreferences msharedPref;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Initialize references to views
        mToolbar = findViewById(R.id.mainPageToolBar);
        mEntertainmentSpotRecyclerView = findViewById(R.id.entertainmentSpotRecyclerView);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Socialate");
        }


        // Initialize  RecyclerView
        mEntertainmentSpotRecyclerView.setHasFixedSize(true);
        mEntertainmentSpotRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase components
        FirebaseApp.initializeApp(this);
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mEventsDatabaseReference = mFireBaseDatabase.getReference().child("Entertainments");
        mUsersDatabaseReference = mFireBaseDatabase.getReference().child("users");
        mLikesDatabaseReference = mFireBaseDatabase.getReference().child("Likes");

        mUsersDatabaseReference.keepSynced(true);
        mEventsDatabaseReference.keepSynced(true);
        mLikesDatabaseReference.keepSynced(true);

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
        /**Checks if initial settings value is present**/
        msharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int syncConnPref = msharedPref.getInt("bar_val", 50);
        (findViewById(R.id.entertainmentSpotRecyclerView)).setVisibility(View.VISIBLE);
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

                final String mEntertainmentKey = getRef(position).getKey();
                final String mEntertainmentName = model.getName();
                final String mEntertainmentUploader = model.getUID();

                viewHolder.setName(model.getName());
                viewHolder.setOwner(model.getAuthor());
                viewHolder.setPhotoUrl(model.getPhotoUrl());
                viewHolder.setLikeButton(mEntertainmentKey);
                viewHolder.setLikeNumber(mEntertainmentKey);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent eventIntent = new Intent(MainActivity.this, ViewEntertainmentActivity.class);
                        eventIntent.putExtra("entertainmentName", mEntertainmentName);
                        eventIntent.putExtra("entertainmentKey", mEntertainmentKey);
                        startActivity(eventIntent);
                    }
                });

                //Click textview to view profile of the user who uploaded the area
                viewHolder.mEntertainmentOwner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileViewIntent = new Intent(getApplicationContext(), ViewOtherUserProfile.class);
                        profileViewIntent.putExtra("entertainmentUploader", mEntertainmentUploader);
                        startActivity(profileViewIntent);
                    }
                });

                viewHolder.mLikeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

        if(id == R.id.action_view_edit_profile){
            onEditProfile();
        }

        if(id == R.id.action_logout){
            onLogout();
            return true;
        }
        if(id == R.id.action_settings){
            launchFrag();
            return true;
        }
        if(id == android.R.id.home){
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("settings pref")).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Socialate");
            (findViewById(R.id.entertainmentSpotRecyclerView)).setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchFrag() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        (findViewById(R.id.entertainmentSpotRecyclerView)).setVisibility(View.GONE);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new preferencesFrag(),"settings pref")
                .commit();
    }
    @Override
    public void onBackPressed() {
        Fragment p = getFragmentManager().findFragmentByTag("settings pref");
        if ((p).isVisible()) {
            getFragmentManager().beginTransaction().remove(p).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Socialate");
            (findViewById(R.id.entertainmentSpotRecyclerView)).setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * This function launches add entertainment activity to create a new spot
     */
    private void onAddEntertainment(){
        Intent addEntertainmentIntent = new Intent(this, AddEntertainmentActivity.class);
        startActivity(addEntertainmentIntent);
    }

    /**
     * This function launches view-edit profile activity to enable users to view and edit their profiles
     */
    private void onEditProfile(){
        Intent viewEditProfileIntent = new Intent(this,ViewEditProfileActivity.class);
        startActivity(viewEditProfileIntent);
    }

    /**
     * this function logs users out of firebase and the app.
     */
    private void onLogout() { mFirebaseAuth.signOut(); }

    public static class EntertainmentSpotAdapterViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageView mLikeButton;
        TextView mEntertainmentLikes;
        TextView mEntertainmentOwner; //
        FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mFirebaseAuth;
        DatabaseReference mLikesDatabaseReference;

        public EntertainmentSpotAdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mLikeButton = mView.findViewById(R.id.likeButton);
            mEntertainmentOwner = mView.findViewById(R.id.ownerTextView); //
            mEntertainmentLikes = mView.findViewById(R.id.likeCounterTextView);
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            mLikesDatabaseReference = mFirebaseDatabase.getReference().child("Likes");
            mLikesDatabaseReference.keepSynced(true);
        }

        void setLikeButton(final String mEntertainmentKey){

            mLikesDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    assert mFirebaseAuth.getCurrentUser() != null;
                    if(dataSnapshot.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).exists()){
                        mLikeButton.setImageResource(R.drawable.ic_fav);

                    }else {
                        mLikeButton.setImageResource(R.drawable.ic_fav_border);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        void setLikeNumber(final String mEntertainmentKey){

            mLikesDatabaseReference.child(mEntertainmentKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mEntertainmentLikes.setText(String.valueOf((int) dataSnapshot.getChildrenCount()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        public void setName(String title){
            TextView event_title = mView.findViewById(R.id.titleTextView);
            event_title.setText(title);
        }

        void setOwner(String author){
            mEntertainmentOwner.setText(author);
        }

        void setPhotoUrl(String image){
            ImageView event_image = mView.findViewById(R.id.photoImageView);
            final ProgressBar progressBar = mView.findViewById(R.id.imageProgressBar);
            Picasso.with(event_image.getContext())
                    .load(image)
                    .into(event_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
    }



}