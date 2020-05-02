package com.app.palpharmacy.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.palpharmacy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PharmacyDetailsActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);


        // hide the default actionbar
        getSupportActionBar().hide();

        // Recieve data
        String name = getIntent().getExtras().getString("name");
        String city = getIntent().getExtras().getString("city");
        String governorate = getIntent().getExtras().getString("governorate");
        String owner = getIntent().getExtras().getString("owner");
        String opens = getIntent().getExtras().getString("opens");
        String image_url = getIntent().getExtras().getString("anime_img");


        // ini views

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);
//
        TextView textOwner = findViewById(R.id.textOwner);
        TextView textOpen = findViewById(R.id.textOpen);
        TextView textGovernorate = findViewById(R.id.textGovernorate);
        TextView textCity = findViewById(R.id.textCity);
        ImageView img = findViewById(R.id.aa_thumbnail);

        // setting values to each view
        textOwner.setText(owner);
        textOpen.setText(opens);
        textCity.setText(city);
        textGovernorate.setText(governorate);
        collapsingToolbarLayout.setTitle(name);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);

    }
}
