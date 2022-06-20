package com.example.fa_parthvekariya_c0854741_android;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddPlaceActivity extends AppCompatActivity {
    int SELECT_PICTURE = 200;
    ImageButton selectImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        addPlaceButtonListener();


        // register the UI widgets with their appropriate IDs
        selectImageBtn = findViewById(R.id.add_image_btn);

        // handle the Choose Image button to trigger
        // the image chooser function
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

    }

    // this function is triggered when
    // the Select Image Button is clicked
    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try { selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        selectImageBtn.setImageURI(selectedImageUri);
                    }
                }
            });

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void addPlaceButtonListener() {
        //bind views with ids
        EditText placeNameEditText = findViewById(R.id.et_place_name);
        EditText placeAddressEditText = findViewById(R.id.et_place_address);
        EditText placeDescriptionEditText = findViewById(R.id.et_place_description);
        Button addPlaceButton = findViewById(R.id.btn_add_place);
        Button selectLocationButton = findViewById(R.id.btn_select_location);

        //add button click listener
        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current values of place
                String placeName = placeNameEditText.getText().toString();
                String placeAddress = placeAddressEditText.getText().toString();
                String placeDescription = placeDescriptionEditText.getText().toString();

                //check for empty values
                if (!placeName.isEmpty() && !placeAddress.isEmpty() && !placeDescription.isEmpty()) {
                    DBHelper dbHandler = new DBHelper(AddPlaceActivity.this);
                    //insert new values to database
                    dbHandler.insertPlaceDetails(placeName, placeAddress, placeDescription, "false");
                    //success toast
                    Toast.makeText(getApplicationContext(), "Place Added Successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all values properly!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPlaceActivity.this, MapsActivity.class));
            }
        });
    }


}