package com.socialteinc.socialate;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class SearchableActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();

        FirebaseApp.initializeApp(this);
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();


        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);

            doMySearch(query);
        }

    }

    private void doMySearch(String search_string){

        DatabaseReference mFirebaseDatabaseReference = mFireBaseDatabase.getReference();
        Query query = mFirebaseDatabaseReference.child("Entertainments").orderByChild("name").equalTo(search_string);


        ValueEventListener valueEventListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    ((TextView)findViewById(R.id.name)).setText((String)postSnapshot.child("name").getValue());
                    System.out.println("------------- "+ (String)postSnapshot.child("name").getValue());
                    System.out.println("*********** "+ (String)postSnapshot.child("author").getValue());
                    //TODO get the data here

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };

        query.addValueEventListener(valueEventListener);



    }

}
