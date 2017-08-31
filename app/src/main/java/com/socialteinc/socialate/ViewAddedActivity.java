package com.socialteinc.socialate;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

public class ViewAddedActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;
    private String title_val, description_val,owner_val,address_val;
    private Uri ImageURi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_added_area);
        mToolbar = findViewById(R.id.ViewAddedAreaToolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Entertainment Area");

        Bundle bundle = getIntent().getExtras();
        title_val = bundle.getString("title_val");
        description_val = bundle.getString("description_val");
        owner_val = bundle.getString("owner_val");
        address_val = bundle.getString("address_val");
        ImageURi = Uri.parse(bundle.getString("imageUri"));

    }

    @Override
    protected void onStart() {
        super.onStart();

        ((TextView)(findViewById(R.id.ViewAddedAreaTitle))).setText(title_val);
        ((TextView)(findViewById(R.id.ViewAddedAreaAddressText))).setText(address_val.trim());
        ((TextView)(findViewById(R.id.ViewAddedAreaDescText))).setText(description_val);
        ((ImageView)findViewById(R.id.ViewAddedAreaImageView)).setImageURI(ImageURi);

        if(owner_val.equals("")){
            ((findViewById(R.id.ViewAddedAreaOwner))).setVisibility(View.GONE);
            ((findViewById(R.id.ViewAddedAreaOwnerText))).setVisibility(View.GONE);
        }
        else{

            ((TextView)(findViewById(R.id.ViewAddedAreaOwnerText))).setText(owner_val);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
           finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
