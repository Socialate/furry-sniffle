package com.socialteinc.socialate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import im.delight.android.location.SimpleLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_nearby extends AppCompatActivity {

    private DatabaseReference ref;
    private DatabaseReference mEntertainmentref;
    private GeoFire geoFire;

    private GeoLocation mylocation;
    private ArrayList<String> geokeys;
    private ArrayList<GeoLocation> geoLocations;
    private DataSnapshot[] geoLocationSnap;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Location location;
    private LocationListener locationListener;
    private SharedPreferences msharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        geokeys = new ArrayList<>();
        geoLocations = new ArrayList<>();


        //initializing the Firebase storage
        ref = FirebaseDatabase.getInstance().getReference("GeoFire");
        mEntertainmentref = FirebaseDatabase.getInstance().getReference("Entertainments");
        geoFire = new GeoFire(ref);

        //for displaying the results
        mRecyclerView = findViewById(R.id.nearby_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Location Manager book keeping stuff
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria locationCriteria = new Criteria();
        String providerName = locationManager.getBestProvider(locationCriteria,
                true);
        if(providerName!=null) {
            location = locationManager.getLastKnownLocation(providerName);
        }
        locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, locationListener);


        msharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int radius = msharedPref.getInt("bar_val", 2);

        System.out.println("my LatLong: "+ location.getLatitude()+ " "+ location.getLongitude());
        System.out.println("settings radius is: "+ radius);
        mylocation = new GeoLocation(location.getLatitude(),location.getLongitude());
        GeoQuery geoQuery = geoFire.queryAtLocation(mylocation, radius);
        GeoQueryEventListener a = new GeoQueryEventListener() {

            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                geoLocations.add(location);
                geokeys.add(key);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                publish();
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        };
        geoQuery.addGeoQueryEventListener(a);



    }

    public int translateSeekbar(int bar_val){

        switch(bar_val){
            case 0:
                return 10;
            case 1:
                return 25;
            case 2:
                return 50;
            case 3:
                return 100;

        }
        return 0;
    }

    public void publish(){
        geoLocationSnap = new DataSnapshot[geoLocations.size()];
        System.out.println("*********************************** "+ geoLocations.size());
        mEntertainmentref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("*********************************** "+ geokeys.size());
                for(int i = 0; i < geokeys.size(); i++){
                    System.out.println("here");
                    System.out.println("----Names "+dataSnapshot.child(geokeys.get(i)).child("name"));
                    geoLocationSnap[i] = dataSnapshot.child(geokeys.get(i));
                }
                mAdapter = new MyAdapter(geoLocationSnap);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location loc) {

            if (loc != null) {
                location = loc;

            }
        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
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
                    .inflate(R.layout.item_event, parent, false);
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
            ((TextView) holder.cardview.findViewById(R.id.titleTextView)).setText((position + 1) + ". " + mDataset[position].child("name").getValue());
            ((TextView) holder.cardview.findViewById(R.id.ownerTextView)).setText((String) mDataset[position].child("author").getValue());
            setPhotoUrl((String) mDataset[position].child("photoUrl").getValue(), holder);
            setLikeNumber(mDataset[position].getKey(), (TextView) holder.cardview.findViewById(R.id.likeCounterTextView));
            final int pos = position;
            final ImageView[] imageView = new ImageView[1];
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent eventIntent = new Intent(Activity_nearby.this, ViewEntertainmentActivity.class);
                    eventIntent.putExtra("entertainmentName", (String) mDataset[pos].child("name").getValue());
                    eventIntent.putExtra("entertainmentKey", mDataset[pos].getKey());
                    startActivity(eventIntent);
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
        void setPhotoUrl(String image, ViewHolder vh){
            ImageView event_image = vh.cardview.findViewById(R.id.photoImageView);
            final ProgressBar progressBar = vh.cardview.findViewById(R.id.imageProgressBar);
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
