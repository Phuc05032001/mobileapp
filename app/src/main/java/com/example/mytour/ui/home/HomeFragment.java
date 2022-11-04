package com.example.mytour.ui.home;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytour.AddActivity;
import com.example.mytour.CustomAdapter;
import com.example.mytour.MainActivity;
import com.example.mytour.MyDatabase;
import com.example.mytour.R;
import com.example.mytour.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    FloatingActionButton add_button,delete_all;
    MyDatabase myDB;
    ArrayList<String> trip_id, trip_title, trip_destination, trip_require, trip_dot, trip_description;
    CustomAdapter customAdapter;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerView;
        add_button = binding.addButton;
        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            startActivity(intent);
        });

        delete_all = binding.deleteAll;
        delete_all.setOnClickListener(v -> {
            MyDatabase myDB = new MyDatabase(getActivity().getApplication());
            myDB.deleteAllData();
            Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
            startActivity(intent);
        });

        myDB = new MyDatabase(getActivity().getApplication());
        trip_id = new ArrayList<>();
        trip_title = new ArrayList<>();
        trip_destination = new ArrayList<>();
        trip_dot = new ArrayList<>();
        trip_require = new ArrayList<>();
        trip_description = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(getActivity().getApplication(), trip_id, trip_title, trip_destination,trip_description, trip_dot, trip_require);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));

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