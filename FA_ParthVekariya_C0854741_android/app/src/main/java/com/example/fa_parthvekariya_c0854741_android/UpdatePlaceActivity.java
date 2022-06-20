package com.example.fa_parthvekariya_c0854741_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_place);

        //get previous details for place
        String previousPlaceName = getIntent().getStringExtra("PLACE_NAME");
        String previousPlaceAddress = getIntent().getStringExtra("PLACE_ADDRESS");
        String previousPlaceDescription = getIntent().getStringExtra("PLACE_DESCRIPTION");
        String placeVisited = getIntent().getStringExtra("PLACE_VISITED");

        //set previous place name
        EditText placeNameEditText = findViewById(R.id.update_place_name);
        placeNameEditText.setText(previousPlaceName);

        //set previous place name
        EditText placeAddressEditText = findViewById(R.id.update_place_address);
        placeAddressEditText.setText(previousPlaceAddress);

        //set previous place description
        EditText placeDescriptionEditText = findViewById(R.id.update_place_description);
        placeDescriptionEditText.setText(previousPlaceDescription);

        Button updatePlaceButton = findViewById(R.id.btn_update_place);

        //update button listener
        updatePlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //get current values of place
                String placeName = placeNameEditText.getText().toString();
                String placeAddress = placeAddressEditText.getText().toString();
                String placeDescription = placeDescriptionEditText.getText().toString();

                //check for empty values
                if (!placeName.isEmpty() && !placeAddress.isEmpty() && !placeDescription.isEmpty()) {
                    DBHelper dbHandler = new DBHelper(UpdatePlaceActivity.this);
                    //update database values with new values
                    dbHandler.UpdatePlaceDetails(placeName,placeAddress, placeDescription, placeVisited);
                    //success toast
                    Toast.makeText(getApplicationContext(), "Place Updated Successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all values properly!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}