package com.socialteinc.socialate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

//import com.firebase.geofire.core.GeoHashQuery;

public class ViewEntertainmentActivity extends AppCompatActivity {

    private String TAG =ViewEntertainmentActivity.class.getSimpleName();

    // References variables
    private ImageView mEntertainmentImage, mNavigationImage;
    private TextView mEntertainmentOwner;
    private TextView mEntertainmentDescription;
    private TextView mEntertainmentAddress;
    private TextView mCommenterName;
    private TextView mLikeCommentTextView;
    private TextView mLikeCommentCounterTextView;
    private TextView mCommentDateTextView;
    private AutoCompleteTextView mCommentTextView;
    private ImageView mCommentorImage;
    private ImageButton mDeleteComment;
    private Toolbar mToolbar;
    private FloatingActionButton mLikeButton;
    private EditText mCommentEditText;
    private ImageButton mCommentButton;
    private Spinner mCostSpinner;
    private ImageView mCostImage;
    private String mAuthor;
    private String commentorProfileImage;
    private Boolean mProcessLike = false;
    private Boolean mProcessCommentLike = false;
    private String mEntertainmentKey;
    private String mCommentKey;
    private String mCommentName;
    private String mEntertainmentName;
    private FirebaseAuth mFirebaseAuth;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter<MyAdapter.ViewHolder> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DataSnapshot> arr;
    private DataSnapshot[] dataset;



    // Firebase instance variables
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private DatabaseReference mUserDatabaseReference;
    private DatabaseReference mLikesDatabaseReference;
    private DatabaseReference mCommentsDatabaseReference;
    private DatabaseReference mCostDatabaseReference;

    //intentService variables
    private connect_receiver connect_receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_entertainment_area);

        // entertainment key
        Intent intent = getIntent();
        mEntertainmentName = intent.getStringExtra("entertainmentName");
        mEntertainmentKey = intent.getStringExtra("entertainmentKey");
        //Log.d(TAG, "onCreate: "+ mEntertainmentKey);

        // Initialize references to views
        mEntertainmentImage = findViewById(R.id.ViewAddedAreaImageView);
        mNavigationImage = findViewById(R.id.navigationImageView);
        mEntertainmentOwner = findViewById(R.id.ViewAddedAreaOwnerText);
        mEntertainmentDescription = findViewById(R.id.ViewAddedAreaDescText);
        mEntertainmentAddress = findViewById(R.id.ViewAddedAreaAddressText);
        mLikeButton = findViewById(R.id.likeFloatingActionButton);
        mCommentEditText = findViewById(R.id.commentEditText);
        mCommentButton = findViewById(R.id.commentImageButton);
        mRecyclerView = findViewById(R.id.comment_recyclerView);
        mCostSpinner = findViewById(R.id.averageCostSpinner);
        mCostImage = findViewById(R.id.averageCostImageView);

        // Initialize firebase
        FirebaseApp.initializeApp(this);
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserDatabaseReference = mFireBaseDatabase.getReference().child("users");
        mEventsDatabaseReference = mFireBaseDatabase.getReference().child("Entertainments");
        mLikesDatabaseReference = mFireBaseDatabase.getReference().child("Likes");
        mCommentsDatabaseReference = mFireBaseDatabase.getReference().child("Comments");
        mCostDatabaseReference = mFireBaseDatabase.getReference().child("cost");

        mCostDatabaseReference.keepSynced(true);

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

        // Testing purposes
        if(mEntertainmentKey == null){
            mEntertainmentKey = "-KuWs0YVBB_nWlC03LrE";
        }
        mEventsDatabaseReference.child(mEntertainmentKey).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String owner = (String) dataSnapshot.child("owner").getValue();
                //String name = (String) dataSnapshot.child("name").getValue();
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

                mCostDatabaseReference.child(mEntertainmentKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //assert mFirebaseAuth.getCurrentUser() != null;
//                        if(dataSnapshot.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).exists()) {

                            String get_selected = (String) dataSnapshot.child(mFirebaseAuth.getCurrentUser().getUid()).getValue();

                            if(!TextUtils.isEmpty(get_selected)){
                                mCostSpinner.setSelection(((ArrayAdapter<String>)mCostSpinner.getAdapter()).getPosition(get_selected));

                                //int n = (int) dataSnapshot.getChildrenCount();
                                int low = 1;
                                int medium = 1;
                                int high = 1;

                                for(DataSnapshot child : dataSnapshot.getChildren()){
                                    String value = (String) child.getValue();

                                    if(value.equals("Low Cost")){
                                        low++;

                                    }else if(value.equals("Medium Cost")){
                                        medium++;

                                    }else if(value.equals("High Cost")){
                                        high++;

                                    }
                                }

                                //System.out.println("***** L:"+low + " M:"+medium + " H:"+high);

                                if(low > medium && low > high){
                                    mCostDatabaseReference.child(mEntertainmentKey).child("Average cost rating").setValue(" Low Cost");
                                }
                                else if(medium > low && medium > high){
                                    mCostDatabaseReference.child(mEntertainmentKey).child("Average cost rating").setValue(" Medium Cost");
                                }
                                else if(high > medium && high > low){
                                    mCostDatabaseReference.child(mEntertainmentKey).child("Average cost rating").setValue(" High Cost");
                                }
                                else if(low == medium && (low > high || medium > high)){
                                    mCostDatabaseReference.child(mEntertainmentKey).child("Average cost rating").setValue(" Medium Low Cost");
                                    //System.out.println("M L");
                                }
                                else if((high == medium && (high > low || medium > low))){
                                    mCostDatabaseReference.child(mEntertainmentKey).child("Average cost rating").setValue(" Medium High Cost");
                                    //System.out.println("M H");
                                }
                                else if(low == high && (low > medium || high > medium)){
                                    mCostDatabaseReference.child(mEntertainmentKey).child("Average cost rating").setValue(" High/Low Cost");
                                    //System.out.println("L to H");
                                }
                                else if(low == medium && medium == high){
                                    mCostDatabaseReference.child(mEntertainmentKey).child("Average cost rating").setValue(" High/Medium/Low Cost");
                                    //System.out.println("NO R0");
                                }
                                else {
                                    mCostDatabaseReference.child(mEntertainmentKey).child("Average cost rating").setValue(" No Rating");
                                    //System.out.println("NO");
                                }

                            }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                postComments();

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

        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processComment();
            }
        });

//        mCostImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // do something
//            }
//        });

        mCostSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CostSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void CostSpinner(){
        final String get_selected = mCostSpinner.getSelectedItem().toString();

        if(!TextUtils.isEmpty(get_selected)){
            assert mFirebaseAuth.getCurrentUser() != null;
            //final String uid = mFirebaseAuth.getCurrentUser().getUid();

            if(!TextUtils.isEmpty(mFirebaseAuth.getCurrentUser().getUid())) {
                if(TextUtils.equals(get_selected,"How costly is this place for you?")){
                    mCostDatabaseReference.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).removeValue();
                }else{
                    mCostDatabaseReference.child(mEntertainmentKey).child(mFirebaseAuth.getCurrentUser().getUid()).setValue(get_selected);
                }
            }

        }


        //starting the intent service
        startIntentService();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connect_receiver);
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

    private void processCommentLike(){
        mProcessCommentLike = true;

        mLikesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mProcessCommentLike && mFirebaseAuth.getCurrentUser() != null) {
                    if (!dataSnapshot.child(mCommentKey).child(mFirebaseAuth.getCurrentUser().getUid()).exists()) {
                        mLikesDatabaseReference.child(mCommentKey).child(mFirebaseAuth.getCurrentUser().getUid()).setValue("liked");
                        mProcessCommentLike = false;
                    } else {
                        mLikesDatabaseReference.child(mCommentKey).child(mFirebaseAuth.getCurrentUser().getUid()).removeValue();
                        mProcessCommentLike = false;
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

                if(!TextUtils.isEmpty(comment)){
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
                }else{
                    Toast.makeText(getApplicationContext(), "Please type a comment first", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void postComments() {
        mCommentsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arr = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String eUID = (String) postSnapshot.child("entertainmentUID").getValue();
                    //final String uid = (String) postSnapshot.child("uid").getValue();

                    if(mEntertainmentKey.equals(eUID) == true){
                        arr.add(postSnapshot);
                    }
                }

                if(arr.size() == 0){
                    Snackbar sb = Snackbar.make(findViewById(R.id.viewEntertainment_activity), "Be the first to comment on this spot", Snackbar.LENGTH_LONG);
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

                mLikeCommentCounterTextView = cardview.findViewById(R.id.likeCommentCounterTextView);

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
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_comments, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            // holder.mTextView.setText(mDataset[position]);

            // view comment author profile
            final String uid = (String) mDataset[position].child("uid").getValue();
            if(!TextUtils.isEmpty(uid)){
                mCommenterName = (holder.cardview.findViewById(R.id.commentorNameTextView));
                mCommenterName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileViewIntent = new Intent(getApplicationContext(), ViewOtherUserProfile.class);
                        profileViewIntent.putExtra("commentUploader", uid);
                        boolean mCheck = true;
                        profileViewIntent.putExtra("check", mCheck);
                        startActivity(profileViewIntent);
                    }
                });

            }

            // like comments
            final String val  =  mDataset[position].getKey();
            if(!TextUtils.isEmpty(val)){
                (holder.cardview.findViewById(R.id.likeTextView)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCommentKey = val;
                        processCommentLike();
                    }
                });

                // allow users to delete their own comments
                mDeleteComment = (holder.cardview.findViewById(R.id.deleteCommentImageButton));
                mDeleteComment.setVisibility(View.INVISIBLE); // not visible to no-author of comment

                if(mFirebaseAuth.getCurrentUser().getUid().equals(mDataset[position].child("uid").getValue())){

                    mDeleteComment.setVisibility(View.VISIBLE); // allow author the option to delete their comment

                    mDeleteComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mCommentsDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    mCommentsDatabaseReference.child(val).removeValue();  // delete comment
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                            mLikesDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // delete associated likes
                                    mLikesDatabaseReference.child(val).child(mFirebaseAuth.getCurrentUser().getUid()).removeValue();
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }
                    });
                }

            }

            ((TextView) holder.cardview.findViewById(R.id.commentorNameTextView)).setText((String) mDataset[position].child("author").getValue());
            ((TextView) holder.cardview.findViewById(R.id.commentMultiAutoCompleteTextView)).setText((String) mDataset[position].child("comment").getValue());
            ((TextView) holder.cardview.findViewById(R.id.dateTextView)).setText((String) mDataset[position].child("timestamp").getValue());

            setPhotoUrl((String) mDataset[position].child("photoUrl").getValue(), holder);
            setLikeNumber(mDataset[position].getKey(), (TextView) holder.cardview.findViewById(R.id.likeCommentCounterTextView));

            final int pos = position;
            //final ImageView[] imageView = new ImageView[1];

            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCommentName = (String) mDataset[pos].child("name").getValue();

                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }

        void setPhotoUrl(String image, ViewHolder vh){
            ImageView event_image = vh.cardview.findViewById(R.id.commentorProfileImageView);
            final ProgressBar progressBar = vh.cardview.findViewById(R.id.progressBar2);
            Picasso.with(event_image.getContext())
                    .load(image)
                    .into(event_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(getApplicationContext(), "Network error: failed to load image", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        void setLikeNumber(final String mCommentKey, final TextView likesView){

            mLikesDatabaseReference.child(mCommentKey).addValueEventListener(new ValueEventListener() {
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

    public void startIntentService(){
        //intentService
        connect_receiver = new connect_receiver();
        intentFilter = new IntentFilter(connect_receiver.PROCESS_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(connect_receiver,intentFilter);
        Intent service = new Intent(getApplicationContext(), connection_service.class);
        startService(service);
    }

    public class connect_receiver extends BroadcastReceiver {

        public final String PROCESS_RESPONSE = "com.socialteinc.socialate.intent.action.PROCESS_RESPONSE";
        boolean response = true;
        //View like = findViewById(R.id.likeButton);
       // View ownerText = findViewById(R.id.ownerTextView);
        View nav = findViewById(R.id.navigationImageView);


        @Override
        public void onReceive(Context context, Intent intent) {
            boolean response1 = intent.getBooleanExtra("response",true);
            if((!response1) && (response1 != response)){
                Snackbar sb = Snackbar.make(findViewById(R.id.viewEntertainment_activity), "Oops, No data connection?", Snackbar.LENGTH_LONG);
                View v = sb.getView();
                v.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
                sb.show();
               // like.setClickable(false);
               // ownerText.setClickable(false);
                nav.setClickable(false);
            }
            else if(response1){
               // like.setClickable(true);
               // ownerText.setClickable(true);
                nav.setClickable(true);
            }
            response = response1;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
