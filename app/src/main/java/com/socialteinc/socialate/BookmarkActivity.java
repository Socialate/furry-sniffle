package com.socialteinc.socialate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class BookmarkActivity extends AppCompatActivity {

    // References variables
    private RecyclerView mEntertainmentSpotRecyclerView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        // Initialize references to views
        mToolbar = findViewById(R.id.bookmarkPageToolBar);
        mEntertainmentSpotRecyclerView = findViewById(R.id.entertainmentSpotRecyclerView);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bookmarks");
        }
    }
}
