package com.socialteinc.socialate;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

public class ViewEntertainmentActivity extends AppCompatActivity {

    private String TAG =ViewEntertainmentActivity.class.getSimpleName();

    // References variables
    private ImageView mEntertainmentImage;
    private TextView mEntertainmentOwner;
    private TextView mEntertainmentDescription;
    private TextView mEntertainmentAddress;
    private TextView mCommentorName;
    private TextView mLikeCommentTextView;
    private TextView mLikeCommentCounterTextView;
    private TextView mCommentDateTextView;
    private AutoCompleteTextView mCommentTextView;
    private ImageView mCommentorImage;
    private Toolbar mToolbar;
    private FloatingActionButton mLikeButton;
    private EditText mCommentEditText;
    private ImageButton mCommentButton;
    private String mAuthor;
    private String commentorProfileImage;
    private Boolean mProcessLike = false;
    private String mEntertainmentKey;
    private String mEntertainmentName;
    private FirebaseAuth mFirebaseAuth;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DataSnapshot> arr;
    private DataSnapshot[] dataset;



    // Firebase instance variables
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private DatabaseReference mUserDatabaseReference;
    private DatabaseReference mLikesDatabaseReference;
    private DatabaseReference mCommentsDatabaseReference;


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
        mRecyclerView = findViewById(R.id.comment_recyclerView);
        mCommentDateTextView = findViewById(R.id.dateTextView);
        mLikeCommentCounterTextView = findViewById(R.id.likeCommentCounterTextView);
        mLikeCommentTextView = findViewById(R.id.likeTextView);

        // Initialize firebase
        FirebaseApp.initializeApp(this);
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserDatabaseReference = mFireBaseDatabase.getReference().child("users");
        mEventsDatabaseReference = mFireBaseDatabase.getReference().child("Entertainments");
        mLikesDatabaseReference = mFireBaseDatabase.getReference().child("Likes");
        mCommentsDatabaseReference = mFireBaseDatabase.getReference().child("Comments");

        mToolbar = findViewById(R.id.ViewAddedAreaToolbar);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(mEntertainmentName);
        }


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

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

                postComments();

                // Display current user comments
               /* mCommentsDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            //int i = 0;
                            //String[] image = new String[0];
                            //String[] comment = new String[0];
                            //String[] author = new String[0];

                            for(DataSnapshot user_comment : dataSnapshot.getChildren()){

                                String eUID = (String) user_comment.child("entertainmentUID").getValue();

                                if(mEntertainmentKey.equals(eUID) == true){
                                    //System.out.println("*** "+eUID+" = "+mEntertainmentKey);
                                    String author =  (String) user_comment.child("author").getValue();
                                    String image = (String) user_comment.child("photoUrl").getValue();
                                    String comment = (String) user_comment.child("comment").getValue();
                                    final String user_id = (String) user_comment.child("uid").getValue();

                                    mCommentorName.setText(author);
                                    mCommentTextView.setText(comment);

                                    Picasso.with(getApplicationContext())
                                            .load(image)
                                            .into(mCommentorImage);

                                    mCommentorName.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent profileViewIntent = new Intent(getApplicationContext(), ViewOtherUserProfile.class);
                                            profileViewIntent.putExtra("entertainmentUploader", user_id);
                                            startActivity(profileViewIntent);
                                        }
                                    });

                                    //i++;
                                }
                            }

                            for(int n=0; n<author.length; n++){
                                mCommentorName.setText(author[i]);
                                mCommentTextView.setText(comment[i]);

                                Picasso.with(getApplicationContext())
                                        .load(image[i])
                                        .into(mCommentorImage);
                            }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });*/
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
        final String user_id = mFirebaseAuth.getCurrentUser().getUid();
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

        mUserDatabaseReference.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAuthor = (String) dataSnapshot.child("name").getValue();
                commentorProfileImage = (String) dataSnapshot.child("profileImage").getValue();

                Comments user_comment = new Comments(user_id, comment, commentorProfileImage, mAuthor, timeStamp, mEntertainmentKey);

                mCommentsDatabaseReference.push().setValue(user_comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            mCommentEditText.setText("");
                            Toast.makeText(ViewEntertainmentActivity.this,
                                    "Successful", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Network Error. Check your connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void postComments() {

        //DatabaseReference mFirebaseDatabaseReference = mFireBaseDatabase.getReference();
        mCommentsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arr = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String eUID = (String) postSnapshot.child("entertainmentUID").getValue();
                    if(mEntertainmentKey.equals(eUID) == true){
                        arr.add(postSnapshot);
                    }
                }

                if(arr.size() == 0){
                    Snackbar sb = Snackbar.make(findViewById(R.id.viewEntertainment_activity), "No comments to display", Snackbar.LENGTH_LONG);
                    View v = sb.getView();
                    v.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
                    sb.show();
                }else {
                    dataset = new DataSnapshot[arr.size()];
                    for (int i = 0; i < arr.size(); i++) {
                        dataset[i] = arr.get(i);
                    }
                    mAdapter = new MyAdapter(dataset);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private DataSnapshot[] mDataset;
        FirebaseDatabase mFirebaseDatabase;
        FirebaseAuth mFirebaseAuth;
        DatabaseReference mLikesDatabaseReference;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public CardView cardview;

            public ViewHolder(CardView v) {
                super(v);
                cardview = v;
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mFirebaseAuth = FirebaseAuth.getInstance();
                mLikesDatabaseReference = mFirebaseDatabase.getReference().child("Likes");
                mLikesDatabaseReference.keepSynced(true);
            }

        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(DataSnapshot[] dataSnapshot) {
            mDataset = dataSnapshot;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_comments, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            // holder.mTextView.setText(mDataset[position]);
            ((TextView) holder.cardview.findViewById(R.id.commentorNameTextView)).setText((String) mDataset[position].child("author").getValue());
            ((TextView) holder.cardview.findViewById(R.id.commentMultiAutoCompleteTextView)).setText((String) mDataset[position].child("comment").getValue());
            ((TextView) holder.cardview.findViewById(R.id.dateTextView)).setText((String) mDataset[position].child("timestamp").getValue());

            setPhotoUrl((String) mDataset[position].child("photoUrl").getValue(), holder);
            //setLikeNumber(mDataset[position].getKey(), (TextView) holder.cardview.findViewById(R.id.likeCounterTextView));
            //final int pos = position;
            //final ImageView[] imageView = new ImageView[1];
           /* holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent eventIntent = new Intent(SearchableActivity.this, ViewEntertainmentActivity.class);
                    eventIntent.putExtra("entertainmentName", (String) mDataset[pos].child("name").getValue());
                    eventIntent.putExtra("entertainmentKey", mDataset[pos].getKey());
                    startActivity(eventIntent);
                }
            }); */

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
        void setPhotoUrl(String image, ViewHolder vh){
            ImageView event_image = vh.cardview.findViewById(R.id.commentorProfileImageView);
            //final ProgressBar progressBar = vh.cardview.findViewById(R.id.imageProgressBar);
            Picasso.with(event_image.getContext())
                    .load(image)
                    .into(event_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            //progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
        void setLikeNumber(final String mEntertainmentKey, final TextView likesView){

            mLikesDatabaseReference.child(mEntertainmentKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    likesView.setText(String.valueOf((int) dataSnapshot.getChildrenCount()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }


}
