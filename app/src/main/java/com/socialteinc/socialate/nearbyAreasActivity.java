package com.socialteinc.socialate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class nearbyAreasActivity extends AppCompatActivity implements LocationListener{

    private DatabaseReference ref;
    private DatabaseReference mEntertainmentref;
    private GeoFire geoFire;

    private GeoLocation mylocation;
    private ArrayList<String> geokeys;
    private ArrayList<GeoLocation> geoLocations;
    private ArrayList<Float> geoDistances;
    private DataSnapshot[] geoLocationSnap;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Location mlocation;
    private SharedPreferences msharedPref;
    String[] sortedkeys;
    private LocationManager locationManager;

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        geokeys = new ArrayList<>();
        geoLocations = new ArrayList<>();
        geoDistances = new ArrayList<>();
        mToolbar = findViewById(R.id.nearby_app_bar);
        msharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nearby Areas");
        }

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
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria locationCriteria = new Criteria();
        String providerName = locationManager.getBestProvider(locationCriteria,
                true);
        if(providerName != null) {
            mlocation = locationManager.getLastKnownLocation(providerName);
            if(mlocation == null){
                locationManager.requestLocationUpdates(providerName, 1000, 0, this);
            }else{
                processLocation();
            }
        }


    }

    public void processLocation(){

        int radius = msharedPref.getInt("bar_val", 2);

        mylocation = new GeoLocation(mlocation.getLatitude(), mlocation.getLongitude());
        System.out.println(translateSeekbar(radius));
        GeoQuery geoQuery = geoFire.queryAtLocation(mylocation, translateSeekbar(radius));
        GeoQueryEventListener a = new GeoQueryEventListener() {

            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                geoLocations.add(location);
                geokeys.add(key);
                Location tmp = new Location(LocationManager.GPS_PROVIDER);
                tmp.setLatitude(location.latitude);
                tmp.setLongitude(location.longitude);
                geoDistances.add(mlocation.distanceTo(tmp));
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

              sortedkeys = insertionSort(geokeys,geoDistances);
                publish();
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        };
        geoQuery.addGeoQueryEventListener(a);
    }

    public static String[] insertionSort(ArrayList<String> keys, ArrayList<Float> locations){

        Float x;
        String y;
        int j;
        for (int i = 1; i < keys.size(); i++){
            x = locations.get(i);
            y = keys.get(i);
            j = i - 1;
            while (j >= 0 && locations.get(j) > x){
                locations.set(j+1, locations.get(j));
                keys.set(j+1, keys.get(j));
                j--;
            }
            locations.set(j+1, x);
            keys.set(j+1, y);
        }
        for(Float i:locations){
            System.out.println(i);
        }

        return keys.toArray(new String[keys.size()]);
    }

    public static int translateSeekbar(int bar_val){

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
        mEntertainmentref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("*********************************** "+ geokeys.size());
                for(int i = 0; i < geokeys.size(); i++){
                    System.out.println("here");
                    System.out.println("----Names "+dataSnapshot.child(sortedkeys[i]).child("name"));
                    geoLocationSnap[i] = dataSnapshot.child(sortedkeys[i]);
                }
                mAdapter = new MyAdapter(geoLocationSnap);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
                finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
        mlocation = location;
        processLocation();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

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
            Sum sum = new Sum();
            setDescription((TextView)holder.cardview.findViewById(R.id.descriptionTextView), sum.summarize((String)mDataset[position].child("description").getValue()) );

            setLikeNumber(mDataset[position].getKey(), (TextView) holder.cardview.findViewById(R.id.likeCounterTextView));
            final int pos = position;
            final ImageView[] imageView = new ImageView[1];
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent eventIntent = new Intent(nearbyAreasActivity.this, ViewEntertainmentActivity.class);
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

        public void setDescription(TextView event_description, String description){
            int MAX_CHAR = 100;

            int maxLength = (description.length() < MAX_CHAR)?description.length():MAX_CHAR;
            String inputString = description.substring(0, maxLength);
            if(description.length() > MAX_CHAR){
                inputString = inputString + "...";
                event_description.setText(inputString);
            }else {
                event_description.setText(inputString);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
