package com.example.mytour;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DetailItem extends AppCompatActivity {

    EditText amount_input, toe_input ;
    TextView type_tv;

    Button delete_button;

    String id, type, amount,toe;

    private Spinner spinnerTypeExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        getAndSetIntentData();

        amount_input = findViewById(R.id.inputAmount);
        toe_input = findViewById(R.id.inputDot);
        type_tv = findViewById(R.id.typeExpense);


        delete_button = findViewById(R.id.deleteButton);
        delete_button.setOnClickListener(v -> {
            MyDatabase myDB = new MyDatabase(DetailItem.this);
            myDB.deleteOneRowEx(id);
        });


    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")
                && getIntent().hasExtra("ex_type")
                && getIntent().hasExtra("ex_amount")
                && getIntent().hasExtra("ex_toe")){

            // get data from Intent
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("ex_type");
            amount = getIntent().getStringExtra("ex_amount");
            toe = getIntent().getStringExtra("ex_toe");

            //setting intent data
            amount_input.setText(amount);
            toe_input.setText(toe);
        }

    }
}