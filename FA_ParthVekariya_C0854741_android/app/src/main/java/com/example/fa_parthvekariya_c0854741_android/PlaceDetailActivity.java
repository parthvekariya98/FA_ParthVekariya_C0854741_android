package com.example.fa_parthvekariya_c0854741_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlaceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        //get previous details for place
        String placeName = getIntent().getStringExtra("PLACE_NAME");
        String placeAddress = getIntent().getStringExtra("PLACE_ADDRESS");
        String placeDescription = getIntent().getStringExtra("PLACE_DESCRIPTION");
        String placeVisited = getIntent().getStringExtra("PLACE_VISITED");

        //set current place name
        TextView placeNameText = findViewById(R.id.txt_place_name);
        placeNameText.setText(placeName);

        //set current place address
        TextView placeAddressText = findViewById(R.id.txt_place_address);
        placeAddressText.setText(placeAddress);

        //set current place description
        TextView placeDescriptionText = findViewById(R.id.txt_place_desc);
        placeDescriptionText.setText(placeDescription);

        Button markAsVisitedButton = findViewById(R.id.btn_mark_visited);
        Button viewOnMapButton = findViewById(R.id.btn_view_on_map);

        // button listener
        markAsVisitedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHandler = new DBHelper(PlaceDetailActivity.this);
                //handle mark as visited section
                //update database values with new values
                dbHandler.UpdatePlaceDetails(placeName, placeAddress, placeDescription, "true");
                //success toast
                Toast.makeText(getApplicationContext(), "Added to visited place section.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlaceDetailActivity.this, MapsActivity.class));
            }
        });
    }
}