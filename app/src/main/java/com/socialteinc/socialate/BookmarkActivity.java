package com.socialteinc.socialate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BookmarkActivity extends AppCompatActivity {

    // References variables
    private RecyclerView mEntertainmentSpotRecyclerView;
    private Toolbar mToolbar;

    // Firebase instance variables
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mEventsDatabaseReference;
    private DatabaseReference mLikesDatabaseReference;
    private DatabaseReference mBookmarksDatabaseReference;

    private FirebaseAuth mFirebaseAuth;

    private Boolean mProcessLike = false;
    private boolean mProcessBookmark = false;

    SharedPreferences msharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        // Initialize references to views
        mToolbar = findViewById(R.id.bookmarkPageToolBar);
        mEntertainmentSpotRecyclerView = findViewById(R.id.entertainmentSpotRecyclerView);

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
        mBookmarksDatabaseReference = mFireBaseDatabase.getReference().child("Bookmarks");

        mUsersDatabaseReference.keepSynced(true);
        mEventsDatabaseReference.keepSynced(true);
        mLikesDatabaseReference.keepSynced(true);
        mBookmarksDatabaseReference.keepSynced(true);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bookmarks");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<String, EntertainmentSpotAdapterViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<String, EntertainmentSpotAdapterViewHolder>(
                String.class,
                R.layout.item_event,
                EntertainmentSpotAdapterViewHolder.class,
                mBookmarksDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid())
        ) {
            @Override
            protected void populateViewHolder(final EntertainmentSpotAdapterViewHolder viewHolder, String model, int position) {

                final String mEntertainmentKey = getRef(position).getKey();
                mEventsDatabaseReference.child(mEntertainmentKey).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String owner = (String) dataSnapshot.child("owner").getValue();
                        String name = (String) dataSnapshot.child("name").getValue();
//                        String description = (String) dataSnapshot.child("description").getValue();
//                        String address = (String) dataSnapshot.child("address").getValue();
                        String photoUrl = (String) dataSnapshot.child("photoUrl").getValue();
                        final String uid = (String) dataSnapshot.child("uid").getValue();
                        String author = (String) dataSnapshot.child("author").getValue();
                        updateViewholder(owner, name, uid, author, photoUrl);

                    }

                    private void updateViewholder(String owner, String name, String uid, String author, String photoUrl) {
                        viewHolder.setName(name);
                        viewHolder.setOwner(author);
                        viewHolder.setPhotoUrl(photoUrl);
                        final String mEntertainmentName =name;
                        final String mEntertainmentUploader = author;
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent eventIntent = new Intent(BookmarkActivity.this, ViewEntertainmentActivity.class);
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
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                viewHolder.setLikeButton(mEntertainmentKey);
                viewHolder.setbookmarkButton(mEntertainmentKey);
                viewHolder.setLikeNumber(mEntertainmentKey);

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
