package com.example.fa_parthvekariya_c0854741_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fa_parthvekariya_c0854741_android.databinding.ActivityPlaceListBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaceListActivity extends AppCompatActivity implements PlaceAdapter.PlaceClickListener {
    private ActivityPlaceListBinding binding;
    PlaceAdapter mAdapter;
    ArrayList<PlaceModel> placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setAddBtnListener();
        addCustomPlaceList();
        setPlaceList();
        setAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //to reload place list on re appearing the activity
        setPlaceList();
        setAdapter();
    }

    //set place adapter method
    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new PlaceAdapter(this, this);
        }
        if (binding.list.getAdapter() == null) {
            binding.list.setLayoutManager(new LinearLayoutManager(this));
            binding.list.setHasFixedSize(false);
            binding.list.setAdapter(mAdapter);
        }

        mAdapter.doRefresh(placeList);
    }

    //add custom 10 place list
    private void addCustomPlaceList() {
        DBHelper dbHandler = new DBHelper(this);
        ArrayList<HashMap<String, String>> places_list = dbHandler.GetPlaces();

        //check with place data base if empty then add static list
        if (places_list.size() == 0) {
            //insert new values to database
            dbHandler.insertPlaceDetails("Taj Mahal", "Agra, Delhi", "The Taj Mahal is an Islamic religious building, mosque and tomb built in the 17th century by king Shah Jahan in memory of his wife, Mumtaz Mahal. Its chief architect was Ustad Ahmad Lahauri.","false");
            dbHandler.insertPlaceDetails("Statue of Unity", "Gujarat, India", "The Statue of Unity is the world's tallest statue, with a height of 182 metres (597 feet), located in the state of Gujarat, India.", "true");
        }
    }

    //set place list from data base
    private void setPlaceList() {
        placeList = new ArrayList<>();

        //get place list from database and set to placeList
        DBHelper db = new DBHelper(this);
        ArrayList<HashMap<String, String>> places_list = db.GetPlaces();

        if (places_list.size() > 0) {
            for (int i = 0; i < places_list.size(); i++) {
                HashMap<String, String> currentPlace = places_list.get(i);
                placeList.add(new PlaceModel(currentPlace.get("place_name"),
                        currentPlace.get("place_description"),
                        currentPlace.get("place_address"),
                        currentPlace.get("place_visited")));
            }
        }

    }

    //on delete btn tap listener
    @Override
    public void onDeleteClickListener(int position) {
    //check current position for place position
        if (placeList.size() > 0) {
            if (placeList.get(position) != null) {
                PlaceModel currentPlace = placeList.get(position);
                DBHelper db = new DBHelper(this);

                //delete place with current place ID
                db.DeletePlace(currentPlace.getPlaceName());
                Toast.makeText(this,"Place Deleted Successfully.",Toast.LENGTH_SHORT).show();

                //reload activity
                recreate();
            }
        }
    }

    @Override
    public void onUpdateClickListener(int position) {
    //check current position for place position
        if (placeList.size() > 0) {
            if (placeList.get(position) != null) {
                PlaceModel currentPlace = placeList.get(position);
                //call update activity and pass current record data
                Intent intent = new Intent(getBaseContext(), UpdatePlaceActivity.class);
                intent.putExtra("PLACE_NAME", currentPlace.getPlaceName());
                intent.putExtra("PLACE_ADDRESS", currentPlace.getPlaceAddress());
                intent.putExtra("PLACE_DESCRIPTION", currentPlace.getPlaceDescription());
                intent.putExtra("PLACE_VISITED", currentPlace.getVisited());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onViewDetailsClickListener(int position) {
        //check current position for place position
        if (placeList.size() > 0) {
            if (placeList.get(position) != null) {
                PlaceModel currentPlace = placeList.get(position);
                //call update activity and pass current record data
                Intent intent = new Intent(getBaseContext(), PlaceDetailActivity.class);
                intent.putExtra("PLACE_NAME", currentPlace.getPlaceName());
                intent.putExtra("PLACE_ADDRESS", currentPlace.getPlaceAddress());
                intent.putExtra("PLACE_DESCRIPTION", currentPlace.getPlaceDescription());
                intent.putExtra("PLACE_VISITED", currentPlace.getVisited());
                startActivity(intent);
            }
        }
    }


    //call add place activity
    public void setAddBtnListener() {
        binding.btnAddPlace.setOnClickListener(v -> {
            startActivity(new Intent(this, AddPlaceActivity.class));
        });
    }
}