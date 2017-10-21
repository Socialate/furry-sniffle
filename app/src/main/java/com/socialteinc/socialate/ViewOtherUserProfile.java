package com.socialteinc.socialate;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ViewOtherUserProfile extends AppCompatActivity{

    private String TAG = ViewEntertainmentActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private ImageView getProfilePicture;
    private TextView getDisplayName;
    private TextView getFullName;
    private EditText getDescrip;
    private String ownerUID;
    private String commentorUID;
    private boolean check;
    private String mUsersKey;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mProfileDatabaseReference;
    private DatabaseReference mProfileDatabaseReference1;
    private boolean checker;

    //intentService variables
    private connect_receiver connect_receiver;
    private IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_profile_activity);

        // get identifier key
        Intent intent = getIntent();
        Intent intent1 = getIntent();
        ownerUID = intent.getStringExtra("entertainmentUploader");
        commentorUID = intent1.getStringExtra("commentUploader");
        checker = intent1.getBooleanExtra("check", false);
        setChecker(checker);

        // Initialize references to views
        mToolbar = findViewById(R.id.ProfileToolbar2);
        getProfilePicture = findViewById(R.id.imageView2);
        getDisplayName = findViewById(R.id.displayNameTextView);
        getFullName = findViewById(R.id.fullNameTextView);
        getDescrip = findViewById(R.id.describeEditText);

        // Initialize Firebase components
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mProfileDatabaseReference = mFireBaseDatabase.getReference().child("users");
        mProfileDatabaseReference1 = mFireBaseDatabase.getReference().child("users");
        //mUsersKey = mFirebaseAuth.

        //Initialise toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Profile");

        // progress bar
        mProgressDialog = new ProgressDialog(this);

        if(getChecker()){
            // for testing purposes
            if(commentorUID == null){
                commentorUID = "rv32DonlxHVQz7IHcCSUyx4xRx42";
                (findViewById(R.id.ViewProfileProgressBar3)).setVisibility(View.GONE);
            }
            // Display comment uploader profile details
            mProfileDatabaseReference1.child(commentorUID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String user_display = (String) dataSnapshot.child("displayName").getValue();
                    String user_name = (String) dataSnapshot.child("name").getValue();
                    String user_image = (String) dataSnapshot.child("profileImage").getValue();
                    String user_descrip = (String) dataSnapshot.child("description").getValue();

                    getDisplayName.setText(user_display);
                    getFullName.setText(user_name);
                    getDescrip.setText(user_descrip);

                    Picasso.with(getApplicationContext())
                            .load(user_image)
                            .into(getProfilePicture, new Callback() {
                                @Override
                                public void onSuccess() {
                                    (findViewById(R.id.ViewProfileProgressBar3)).setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {

                                }
                            });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }else {
            //for testing purposes
            if(ownerUID == null){
                ownerUID = "B7TbLOcLXggRL1TyQxrgrGlwMiO2";
                (findViewById(R.id.ViewProfileProgressBar3)).setVisibility(View.GONE);

            }

            // Display entertainment uploader profile details
            mProfileDatabaseReference.child(ownerUID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String user_display = (String) dataSnapshot.child("displayName").getValue();
                    String user_name = (String) dataSnapshot.child("name").getValue();
                    String user_image = (String) dataSnapshot.child("profileImage").getValue();
                    String user_descrip = (String) dataSnapshot.child("description").getValue();

                    getDisplayName.setText(user_display);
                    getFullName.setText(user_name);
                    getDescrip.setText(user_descrip);

                    Picasso.with(getApplicationContext())
                            .load(user_image)
                            .into(getProfilePicture,
                                    new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            (findViewById(R.id.ViewProfileProgressBar3)).setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        //starting the intent service
        startIntentService();

    }

    public void setChecker(boolean check){
        this.check = check;
    }

    public boolean getChecker(){
        return check;
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
                Snackbar sb = Snackbar.make(findViewById(R.id.other_profile_view), "Oops, No data connection?", Snackbar.LENGTH_LONG);
                View v = sb.getView();
                v.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
                sb.show();
            }
            response = response1;
        }
    }
}
