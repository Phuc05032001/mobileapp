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
    FloatingActionButton add_button_expense, calculator_button_expense;
    MyDatabase myDB;
    ArrayList<String> ex_id, ex_type, ex_amount, ex_toe;
    ExpenseAdapter customAdapterEx;
    TextView trip_Id, trip_Title;
    String tripId, tripTitle;
    int sumFood = 0, sumTravel = 0, sumCostsIncurred = 0, sumTransport = 0,total = 0 ;


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
        calculator_button_expense = findViewById(R.id.calculator_button_expense);
        add_button_expense = findViewById(R.id.add_button_expense);

        myDB = new MyDatabase(Expenses.this);
        ex_id = new ArrayList<>();
        ex_type = new ArrayList<>();
        ex_amount = new ArrayList<>();
        ex_toe = new ArrayList<>();

        storeDataInArrays();

        calculator_button_expense.setOnClickListener(v -> {
            Integer trip_id = Integer.parseInt(tripId);
            Cursor cursor = myDB.readAllDataEx(trip_id);
            Cursor cursorTravel = myDB.sumType(trip_id, "Travel");
            Cursor cursorFood = myDB.sumType(trip_id, "Food");
            Cursor cursorTransport = myDB.sumType(trip_id,"Transport");
            Cursor cursorCostsIncurred = myDB.sumType(trip_id,"Costs incurred");


            ArrayList <Integer> allTotal = new ArrayList();
            ArrayList <Integer> allTravel = new ArrayList();
            ArrayList <Integer> allTransport = new ArrayList();
            ArrayList <Integer> allFood = new ArrayList();
            ArrayList <Integer> allCostsIncurred = new ArrayList();

            while(cursor.moveToNext()){
                allTotal.add(cursor.getInt(2));
            }
            for (int i=0; i< allTotal.size(); i++) {
                total = total + allTotal.get(i);
            }

            //Travel
            while(cursorTravel.moveToNext()){
                allTravel.add(cursorTravel.getInt(2));
            }
            for (int i=0; i< allTravel.size(); i++) {
                sumTravel = sumTravel + allTravel.get(i);
            }

            //Food
            while(cursorFood.moveToNext()){
                allFood.add(cursorFood.getInt(2));
            }
            for (int i=0; i< allFood.size(); i++) {
                sumFood = sumFood + allFood.get(i);
            }

            //Transport
            while(cursorTransport.moveToNext()){
                allTransport.add(cursorTransport.getInt(2));
            }
            for (int i=0; i< allTransport.size(); i++) {
                sumTransport = sumTransport + allTransport.get(i);
            }

            //CostsIncurred
            while(cursorCostsIncurred.moveToNext()){
                allCostsIncurred.add(cursorCostsIncurred.getInt(2));
            }
            for (int i=0; i< allCostsIncurred.size(); i++) {
                sumCostsIncurred = sumCostsIncurred + allCostsIncurred.get(i);
            }

            Intent intent = new Intent(this,calculatorExpenses.class);
            intent.putExtra("sumFood",sumTravel);
            intent.putExtra("sumTravel",sumFood);
            intent.putExtra("sumCostsIncurred",sumTransport);
            intent.putExtra("sumTransport",sumCostsIncurred);
            intent.putExtra("total",total);
            intent.putExtra("ex_trip_Id",tripId);
            startActivity(intent);

            total = 0;
        });

        add_button_expense.setOnClickListener(view ->{
            Intent intent = new Intent(this,AddExpense.class);
            intent.putExtra("ex_trip_Id",tripId);
            intent.putExtra("trip_title",tripTitle);
            startActivity(intent);
        });

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