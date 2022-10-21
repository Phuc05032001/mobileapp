package com.example.mytour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class AddExpense extends AppCompatActivity {

    EditText AmountInput, ToeInput;
    Button AddExpense;
    String tripId, tripTitle;
    MyDatabase myDB;


    private final String[] typeExpenseArray = {
            "Travel",
            "Food",
            "Transport",
            "Costs incurred",
    };

    private Spinner spinnerTypeExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        if(getIntent().hasExtra("ex_trip_Id")
                && getIntent().hasExtra("trip_title")){
            // get data from Intent
            tripId = getIntent().getStringExtra("ex_trip_Id");
            tripTitle = getIntent().getStringExtra("trip_title");
        }

        AmountInput = findViewById(R.id.inputName);

        ToeInput = findViewById(R.id.inputDot);

        spinnerTypeExpense = (Spinner) findViewById(R.id.spinnerTypeExpense);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeExpenseArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeExpense.setAdapter((dataAdapter));

        AddExpense = findViewById(R.id.addExpenseButton);
        AddExpense.setOnClickListener(view -> {

            String Type = spinnerTypeExpense.getSelectedItem().toString();
            String Amount = AmountInput.getText().toString().trim();
            String Toe = ToeInput.getText().toString().trim();
            Integer ex_trip_id = Integer.parseInt(tripId);

            boolean check = CheckAllFields(Amount,Toe);

            if (check) {
                myDB = new MyDatabase(AddExpense.this);
                myDB.addExpense(Type, Amount,Toe, ex_trip_id);
//                displayNextAlert(Type,Amount,Toe, ex_trip_id);

                Intent intent = new Intent(this,Expenses.class);
                intent.putExtra("ex_trip_Id",tripId);
                intent.putExtra("trip_title",tripTitle);
                startActivity(intent);

            }
            else {
                Toast.makeText(getApplicationContext(), "check information again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerExpenseFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void updateDOT(@NonNull LocalDate dot){
        TextView dotText = (TextView) findViewById(R.id.inputDot);
        dotText.setText(dot.toString());
    }
    private boolean CheckAllFields(@NonNull String amount, String toe) {
        if (amount.length() == 0) {
            AmountInput.requestFocus();
            AmountInput.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        if (toe.length() == 0) {
            ToeInput.requestFocus();
            ToeInput.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        else {
            return true;}
    }
    private void displayNextAlert(String Type, String Amount, String Toe, Integer Trip_id){
        new AlertDialog.Builder(this).setTitle("Details entered").setMessage("Details entered: " +
                "\n" +
                "\n" + Type +
                "\n" + Amount +
                "\n" + Toe +
                "\n" + Trip_id
        ).setNeutralButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
  }
}