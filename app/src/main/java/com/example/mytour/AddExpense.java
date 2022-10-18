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
    Integer ex_trip_Id;



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

        if(getIntent().hasExtra("ex_trip_Id")){
            // get data from Intent
            String ex_trip_Id_string = getIntent().getStringExtra("ex_trip_Id");
            ex_trip_Id = Integer.parseInt(ex_trip_Id_string);
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


            boolean check = CheckAllFields(Amount,Toe);


            // the boolean variable turns to be true then
            // only the user must be proceed to the activity

            if (check) {

                MyDatabase myDB = new MyDatabase(AddExpense.this);
                myDB.addExpense(Type, Amount,Toe, ex_trip_Id);

                displayNextAlert(Type,Amount,Toe, ex_trip_Id);
//                startActivity(new Intent(AddExpense.this, Expenses.class));
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
    // function which checks all the text fields
    private boolean CheckAllFields(String amount, String toe) {
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
//        if(!toe.matches("^\\d{4}[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?(([0-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?(([0-2][0-9])|(30)))|(02[\\-\\/\\s]?[0-2][0-9]))$")){
//            ToeInput.requestFocus();
//            ToeInput.setError("Not correct format data input year-month-day");
//            return false;
//        }
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