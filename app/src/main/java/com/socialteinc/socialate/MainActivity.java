package com.socialteinc.socialate;


import android.app.Fragment;
import android.app.SearchManager;
import android.content.*;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.login.LoginManager;
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
    private DatabaseReference mCostDatabaseReference;
    private DatabaseReference mBookmarksDatabaseReference;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //intentService variables
    private connect_receiver connect_receiver;
    private IntentFilter intentFilter;


    private Boolean mProcessLike = false;
    private boolean mProcessBookmark = false;

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
        mCostDatabaseReference = mFireBaseDatabase.getReference().child("cost");
        mBookmarksDatabaseReference = mFireBaseDatabase.getReference().child("Bookmarks");

        mUsersDatabaseReference.keepSynced(true);
        mEventsDatabaseReference.keepSynced(true);
        mLikesDatabaseReference.keepSynced(true);
        mCostDatabaseReference.keepSynced(true);
        mBookmarksDatabaseReference.keepSynced(true);

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
        //int syncConnPref = msharedPref.getInt("bar_val", 50);
        (findViewById(R.id.entertainmentSpotRecyclerView)).setVisibility(View.VISIBLE);

        //starting intent service
        startIntentService();
    }

    /**
     * This method checks if a user has created a profile in the database
     * and if not the user is redirected to the pofile set up page
     * before they can look at the data on the application
     */
    public void checkProfileExist() {

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

    public void startIntentService(){
        //intentService
        connect_receiver = new connect_receiver();
        intentFilter = new IntentFilter(connect_receiver.PROCESS_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(connect_receiver,intentFilter);
        Intent service = new Intent(getApplicationContext(), connection_service.class);
        startService(service);
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
            protected void populateViewHolder(final EntertainmentSpotAdapterViewHolder viewHolder, Entertainment model, int position) {

                final String mEntertainmentKey = getRef(position).getKey();
                final String mEntertainmentName = model.getName();
                final String mEntertainmentUploader = model.getUID();

                viewHolder.setName(model.getName());
                viewHolder.setOwner(model.getAuthor());
                viewHolder.setPhotoUrl(model.getPhotoUrl());
                viewHolder.setLikeButton(mEntertainmentKey);
                viewHolder.setbookmarkButton(mEntertainmentKey);
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

                mCostDatabaseReference.child(mEntertainmentKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String get_rating = (String) dataSnapshot.child("Average cost rating").getValue();
                        viewHolder.setAverageCost(get_rating);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
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

                viewHolder.mBookmarkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mProcessBookmark = true;
                        mBookmarksDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (mProcessBookmark && mFirebaseAuth.getCurrentUser() != null) {
                                    if (!dataSnapshot.child(mFirebaseAuth.getCurrentUser().getUid()).child(mEntertainmentKey).exists()) {
                                        mBookmarksDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child(mEntertainmentKey).setValue("true");
                                        mProcessBookmark = false;
                                    } else {
                                        mBookmarksDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child(mEntertainmentKey).removeValue();
                                        mProcessBookmark = false;
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
        unregisterReceiver(connect_receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_btn).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.requestFocus(1);
        //searchView.setSubmitButtonEnabled(true);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_view_edit_profile){
            onEditProfile();
        }

        if(id == R.id.action_logout){
            onLogout();
            return true;
        }
        if(id == R.id.action_settings){
            launchFrag();
            setVisibility(0);

            return true;
        }
        if(id == android.R.id.home){
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("settings pref")).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Socialate");
            setVisibility(1);
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
        if(p == null){
            super.onBackPressed();
        }
        else if ((p).isVisible()) {
            getFragmentManager().beginTransaction().remove(p).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Socialate");
            setVisibility(1);

        }
    }

    /**
     * This function launches add entertainment activity to create a new spot
     */
    public void onAddEntertainment(View v){
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
    private boolean onLogout() {
        mFirebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        return true;
        }

    public static class EntertainmentSpotAdapterViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageView mLikeButton;
        ImageView mBookmarkButton;
        TextView mEntertainmentLikes;
        TextView mEntertainmentOwner; //
        FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mFirebaseAuth;
        DatabaseReference mLikesDatabaseReference;
        DatabaseReference mBookmarksDatabaseReference;

        public EntertainmentSpotAdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mBookmarkButton = mView.findViewById(R.id.bookmarkButton);
            mLikeButton = mView.findViewById(R.id.likeButton);
            mEntertainmentOwner = mView.findViewById(R.id.ownerTextView); //
            mEntertainmentLikes = mView.findViewById(R.id.likeCounterTextView);
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            mLikesDatabaseReference = mFirebaseDatabase.getReference().child("Likes");
            mBookmarksDatabaseReference = mFirebaseDatabase.getReference().child("Bookmarks");
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

        void setbookmarkButton(final String mEntertainmentKey){

            mBookmarksDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    assert mFirebaseAuth.getCurrentUser() != null;
                    if(dataSnapshot.child(mFirebaseAuth.getCurrentUser().getUid()).child(mEntertainmentKey).exists()){
                        mBookmarkButton.setImageResource(R.drawable.ic_action_bookmark_red);

                    }else {
                        mBookmarkButton.setImageResource(R.drawable.ic_action_bookmark);

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

        private void setAverageCost(String cost){
            TextView average_cost = mView.findViewById(R.id.averageCostTextView);
            if(cost != null && !cost.equals(" No Rating")){
                //average_cost.setVisibility(View.VISIBLE);
                average_cost.setText("Average cost:"+cost);
            }else {
                average_cost.setVisibility(View.GONE);
            }

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

    public boolean setVisibility(int v){
        if(v == 0){
            v = View.INVISIBLE;
        }
        else{
            v = View.VISIBLE;
        }
        (findViewById(R.id.floatingActionButton)).setVisibility(v);
        (findViewById(R.id.search_btn)).setVisibility(v);
        (findViewById(R.id.entertainmentSpotRecyclerView)).setVisibility(v);

        return true;
    }

    public class connect_receiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.socialteinc.socialate.intent.action.PROCESS_RESPONSE";
        boolean response = true;
        View fab = findViewById(R.id.floatingActionButton);
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean response1 = intent.getBooleanExtra("response",true);
           if((!response1) && (response1 != response)){
               Snackbar sb = Snackbar.make(findViewById(R.id.layout_main_activity), "Oops, No data connection?", Snackbar.LENGTH_LONG);
               View v = sb.getView();
               v.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
               sb.show();
               fab.setClickable(false);
           }
           else if(response1){
               fab.setClickable(true);
           }
            response = response1;
        }
    }

}