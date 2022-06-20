package com.example.fa_parthvekariya_c0854741_android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PLACES_DB";
    private static final String TABLE_PLACES = "places";
    private static final String KEY_ID = "id";
    private static final String KEY_PLACE_NAME = "place_name";
    private static final String KEY_PLACE_ADDRESS = "place_address";
    private static final String KEY_PLACE_DESCRIPTION = "place_description";
    private static final String KEY_PLACE_VISITED = "place_visited";
    public DBHelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PLACE_NAME + " TEXT,"
                + KEY_PLACE_ADDRESS + " TEXT,"
                + KEY_PLACE_DESCRIPTION + " TEXT,"
                + KEY_PLACE_VISITED + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        // Create tables again
        onCreate(db);
    }

    // Adding new Place Details
    void insertPlaceDetails(String place_name, String place_address, String place_description, String place_visited){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues placeValues = new ContentValues();
        placeValues.put(KEY_PLACE_NAME, place_name);
        placeValues.put(KEY_PLACE_ADDRESS, place_address);
        placeValues.put(KEY_PLACE_DESCRIPTION, place_description);
        placeValues.put(KEY_PLACE_VISITED, place_visited);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_PLACES,null, placeValues);
        db.close();
    }

    // Get Place List
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetPlaces(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> placeList = new ArrayList<>();
        String query = "SELECT place_name, place_address, place_description, place_visited FROM " + TABLE_PLACES;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> placeObj = new HashMap<>();
            placeObj.put("place_name",cursor.getString(cursor.getColumnIndex(KEY_PLACE_NAME)));
            placeObj.put("place_address",cursor.getString(cursor.getColumnIndex(KEY_PLACE_ADDRESS)));
            placeObj.put("place_description",cursor.getString(cursor.getColumnIndex(KEY_PLACE_DESCRIPTION)));
            placeObj.put("place_visited",cursor.getString(cursor.getColumnIndex(KEY_PLACE_VISITED)));
            placeList.add(placeObj);
        }
        return  placeList;
    }

    // Get Place Details based on placeId
    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetPlaceByPlaceId(String placeId){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> placeList = new ArrayList<>();
        String query = "SELECT place_name, place_address, place_description, place_visited FROM "+ TABLE_PLACES;
        Cursor cursor = db.query(TABLE_PLACES,
                new String[]{KEY_PLACE_NAME, KEY_PLACE_ADDRESS, KEY_PLACE_DESCRIPTION, KEY_PLACE_VISITED},
                KEY_PLACE_NAME+ "=?",
                new String[]{String.valueOf(placeId)},
                null,
                null,
                null,
                null);
        if (cursor.moveToNext()){
            HashMap<String,String> placeObj = new HashMap<>();
            placeObj.put("place_name",cursor.getString(cursor.getColumnIndex(KEY_PLACE_NAME)));
            placeObj.put("place_address",cursor.getString(cursor.getColumnIndex(KEY_PLACE_ADDRESS)));
            placeObj.put("place_description",cursor.getString(cursor.getColumnIndex(KEY_PLACE_DESCRIPTION)));
            placeObj.put("place_visited",cursor.getString(cursor.getColumnIndex(KEY_PLACE_VISITED)));
            placeList.add(placeObj);
        }
        return  placeList;
    }

    // Delete Place Details
    public void DeletePlace(String placeId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACES,
                KEY_PLACE_NAME+" = ?",
                new String[]{String.valueOf(placeId)});
        db.close();
    }

    //Delete all place records
    public void DeleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACES, null,null);
        db.close();
    }

    // Update Place Details
    public int UpdatePlaceDetails(String place_name, String place_address, String place_description, String place_visited){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues placeValues = new ContentValues();
        placeValues.put(KEY_PLACE_NAME, place_name);
        placeValues.put(KEY_PLACE_ADDRESS, place_address);
        placeValues.put(KEY_PLACE_DESCRIPTION, place_description);
        placeValues.put(KEY_PLACE_VISITED, place_visited);
        int count = db.update(TABLE_PLACES,
                placeValues,
                KEY_PLACE_NAME+" = ?",
                new String[]{String.valueOf(place_name)});
        return count;
    }
}
