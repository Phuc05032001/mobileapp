package com.example.mytour.ui.search;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytour.AddActivity;
import com.example.mytour.CustomAdapter;
import com.example.mytour.MyDatabase;
import com.example.mytour.databinding.FragmentHomeBinding;
import com.example.mytour.databinding.FragmentSearchBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private static final String TABLE_NAME = "my_trips";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "trip_title";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_DATE_OF_THE_TRIP = "date_of_trip";
    private static final String COLUMN_REQUIRE_ASSESSMENT = "require_assessment";
    private static final String COLUMN_DESCRIPTION = "description";

    private FragmentSearchBinding binding;

    RecyclerView recyclerView2;
    MyDatabase myDB;
    ArrayList<String> trip_id, trip_title, trip_destination, trip_require, trip_dot, trip_description;
    String searchData;
    CustomAdapter customAdapter;
    Button SearchBtn;
    EditText SearchInput;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SearchInput = binding.inputSearch;

        recyclerView2 = binding.recyclerView2;

        SearchBtn = binding.searchButton;

        myDB = new MyDatabase(getActivity().getApplication());
        trip_id = new ArrayList<>();
        trip_title = new ArrayList<>();
        trip_destination = new ArrayList<>();
        trip_dot = new ArrayList<>();
        trip_require = new ArrayList<>();
        trip_description = new ArrayList<>();

        storeDataInArrays();

        SearchBtn.setOnClickListener(view -> {
            SQLiteDatabase db = getContext().openOrCreateDatabase(TABLE_NAME, Context.MODE_PRIVATE, null);
            searchData = SearchInput.getText().toString().trim();

            Cursor cursor = myDB.readSearchData(searchData);
           // Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 0){
                Toast.makeText(getActivity().getApplication(), "Search not found???", Toast.LENGTH_SHORT).show();
                trip_id.removeAll(trip_id);
                trip_title.removeAll(trip_title);
                trip_destination.removeAll(trip_destination);
                trip_require.removeAll(trip_require);
                trip_dot.removeAll(trip_dot);
                trip_description.removeAll(trip_description);

            }
            else {
                trip_id.removeAll(trip_id);
                trip_title.removeAll(trip_title);
                trip_destination.removeAll(trip_destination);
                trip_require.removeAll(trip_require);
                trip_dot.removeAll(trip_dot);
                trip_description.removeAll(trip_description);

                while (cursor.moveToNext()){
                    trip_id.add(cursor.getString(0));
                    trip_title.add(cursor.getString(1));
                    trip_destination.add(cursor.getString(2));
                    trip_description.add(cursor.getString(3));
                    trip_require.add(cursor.getString(4));
                    trip_dot.add(cursor.getString(5));

                    Toast.makeText(getActivity().getApplication(), "Search ok", Toast.LENGTH_SHORT).show();
                }
            }
            customAdapter = new CustomAdapter(getActivity().getApplication(), trip_id, trip_title, trip_destination,trip_description, trip_dot, trip_require);
            recyclerView2.setAdapter(customAdapter);

        });


        customAdapter = new CustomAdapter(getActivity().getApplication(), trip_id, trip_title, trip_destination,trip_description, trip_dot, trip_require);
        recyclerView2.setAdapter(customAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));

        return root;
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
//            Toast.makeText(getActivity().getApplication(), "No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                trip_id.add(cursor.getString(0));
                trip_title.add(cursor.getString(1));
                trip_destination.add(cursor.getString(2));
                trip_description.add(cursor.getString(3));
                trip_require.add(cursor.getString(4));
                trip_dot.add(cursor.getString(5));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}