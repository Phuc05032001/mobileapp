package com.example.mytour;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytour.ui.home.HomeFragment;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    private  static  final  String DATABASE_NAME = "TripAssistant.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_trips";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "trip_title";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_DATE_OF_THE_TRIP = "date_of_trip";
    private static final String COLUMN_REQUIRE_ASSESSMENT = "require_assessment";
    private static final String COLUMN_DESCRIPTION = "description";

    private static final String TABLE_NAME_EXPENSES = "my_expenses";
    private static final String COLUMN_ID2 = "ex_id";
    private static final String COLUMN_FOREIGN_KEY_TRIP_ID = "trip_id";
    private static final String COLUMN_TYPE = "ex_type";
    private static final String COLUMN_AMOUNT = "ex_amount";
    private static final String COLUMN_TIME_OF_EXPENSE = "ex_time_of_expense";



    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_DESTINATION + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_REQUIRE_ASSESSMENT + " TEXT, " +
                        COLUMN_DATE_OF_THE_TRIP + " TEXT);";
        db.execSQL(query);



        String query2 = "CREATE TABLE " + TABLE_NAME_EXPENSES +
                        " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TYPE + " TEXT, " +
                        COLUMN_AMOUNT + " TEXT, " +
                        COLUMN_TIME_OF_EXPENSE + " TEXT, " +
                        COLUMN_FOREIGN_KEY_TRIP_ID + " INTEGER, " +
                        " FOREIGN KEY(" + COLUMN_FOREIGN_KEY_TRIP_ID + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_ID + ")" +
                        ");";
//        Log.i("quert", query2);

        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EXPENSES );
        onCreate(db);
    }

    public void addTrip(@NonNull String name,@NonNull String destination,@NonNull String description,@NonNull String dot,@NonNull String require){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DATE_OF_THE_TRIP, dot);
        cv.put(COLUMN_REQUIRE_ASSESSMENT, require);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Add Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addExpense(@NonNull String type,@NonNull String amount,@NonNull String toe,@NonNull Integer ex_trip_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_TIME_OF_EXPENSE, toe);
        cv.put(COLUMN_FOREIGN_KEY_TRIP_ID, ex_trip_id);
        long result = db.insert(TABLE_NAME_EXPENSES, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Add Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if( db != null){
            cursor = db.rawQuery(query, null);
        }
        return  cursor;
    }

    public Cursor readAllDataEx(Integer trip_id_ex){

        String query = "Select * from "  + TABLE_NAME_EXPENSES +" WHERE "+COLUMN_FOREIGN_KEY_TRIP_ID+" LIKE '%"+trip_id_ex+"%'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if( db != null){
            cursor = db.rawQuery(query, null);
        }
        return  cursor;
    }

    public Cursor sumType(Integer trip_id_ex, String type){

        String query = "Select * from "  + TABLE_NAME_EXPENSES +" WHERE "+COLUMN_FOREIGN_KEY_TRIP_ID+" LIKE '%"+trip_id_ex+"%'"
                        + "AND " +COLUMN_TYPE+ " LIKE '%"+type+"%'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorType = null;
        if( db != null){
            cursorType = db.rawQuery(query, null);
        }

        return  cursorType;
    }

    public Cursor readSearchData( String searchData){
         String query = "Select * from "  + TABLE_NAME +" WHERE "+COLUMN_TITLE+" LIKE '%"+searchData+"%'"
                        + " OR " +  COLUMN_DESTINATION+" LIKE '%"+searchData+"%'"+
                        " OR " +  COLUMN_DATE_OF_THE_TRIP+" LIKE '%"+searchData+"%'"+
                        " OR " +  COLUMN_REQUIRE_ASSESSMENT+" LIKE '%"+searchData+"%'"+
                        " OR " +  COLUMN_DESCRIPTION+" LIKE '%"+searchData+"%'"+" " +
                        "OR " + COLUMN_ID+" LIKE '%"+searchData+"%'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if( db != null){
            cursor = db.rawQuery(query, null);
        }
        return  cursor;
    }


    public void updateData(String row_id, String trip_title, String destination, String description, String dot, String require) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, trip_title);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DATE_OF_THE_TRIP, dot);
        cv.put(COLUMN_REQUIRE_ASSESSMENT, require);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }


    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRowEx(String ex_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_EXPENSES, "ex_id=?", new String[]{ex_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Toast.makeText(context, "Delete Successfully.", Toast.LENGTH_SHORT).show();
        db.execSQL("DELETE FROM " + TABLE_NAME);

    }

}

