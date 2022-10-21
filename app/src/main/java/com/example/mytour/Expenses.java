package com.example.mytour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Expenses extends AppCompatActivity {
    RecyclerView recyclerViewExpenses;
    FloatingActionButton add_button_expense;
    MyDatabase myDB;
    ArrayList<String> ex_id, ex_type, ex_amount, ex_toe;
    ExpenseAdapter customAdapterEx;
    TextView trip_Id, trip_Title;
    String tripId, tripTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        if(getIntent().hasExtra("ex_trip_Id")
                && getIntent().hasExtra("trip_title")){
            trip_Id = findViewById(R.id.trip_Id_txt);
            trip_Title = findViewById(R.id.trip_Title_txt);

            // get data from Intent
            tripId = getIntent().getStringExtra("ex_trip_Id");
            tripTitle = getIntent().getStringExtra("trip_title");

            //setting intent data
            trip_Id.setText(tripId);
            trip_Title.setText(tripTitle);
        }

        recyclerViewExpenses = findViewById(R.id.recyclerViewExpenses);
        add_button_expense = findViewById(R.id.add_button_expense);
        add_button_expense.setOnClickListener(view ->{
            Intent intent = new Intent(this,AddExpense.class);
            intent.putExtra("ex_trip_Id",tripId);
            intent.putExtra("trip_title",tripTitle);
            startActivity(intent);
        });

        myDB = new MyDatabase(Expenses.this);
        ex_id = new ArrayList<>();
        ex_type = new ArrayList<>();
        ex_amount = new ArrayList<>();
        ex_toe = new ArrayList<>();

        storeDataInArrays();

        customAdapterEx = new ExpenseAdapter(Expenses.this, ex_id, ex_type, ex_amount,ex_toe);
        recyclerViewExpenses.setAdapter(customAdapterEx);
        recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(Expenses.this));
    }

    void storeDataInArrays() {
        Integer trip_id = Integer.parseInt(tripId);
        Cursor cursor = myDB.readAllDataEx(trip_id);
        if(cursor.getCount() == 0){
            Toast.makeText(Expenses.this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                ex_id.add(cursor.getString(0));
                ex_type.add(cursor.getString(1));
                ex_amount.add(cursor.getString(3));
                ex_toe.add(cursor.getString(2));
            }
        }
    }
}