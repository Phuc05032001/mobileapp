package com.example.mytour;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class AddActivity extends AppCompatActivity {
    int _id;

    EditText nameInput;
    EditText destinationInput;
    EditText descInput;
    TextView dotInput;

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button  saveButton;
    final String regexCheckData = "^\\d{4}[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?(([0-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?(([0-2][0-9])|(30)))|(02[\\-\\/\\s]?[0-2][0-9]))$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameInput = findViewById(R.id.inputName);
        destinationInput = findViewById(R.id.inputDestination);
        descInput = findViewById(R.id.inputDesc);
        dotInput = findViewById(R.id.inputDot);

        radioGroup = findViewById(R.id.radioGroup);
        saveButton = findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(view -> {

            // get selected radio button from radioGroup
            int selectedId = radioGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            radioButton = (RadioButton) findViewById(selectedId);

            String Require = radioButton.getText().toString().trim();
            String Name = nameInput.getText().toString().trim();
            String Destination = destinationInput.getText().toString().trim();
            String Description = descInput.getText().toString().trim();
            String Dot = dotInput.getText().toString().trim();

            // store the returned value of the dedicated function which checks
            // whether the entered data is valid or if any fields are left blank.

            boolean check = CheckAllFields(Name,Destination,Description,Dot,Require);
            MyDatabase myDB = new MyDatabase(AddActivity.this);

            // the boolean variable turns to be true then
            // only the user must be proceed to the activity

            if (check) {
                myDB.addTrip(Name, Destination,Description, Dot, Require);
                startActivity(new Intent(AddActivity.this, MainActivity.class));
            }
            else {
                Toast.makeText(getApplicationContext(), "check information again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void updateDOT(@NonNull LocalDate dot){
        TextView dotText = (TextView) findViewById(R.id.inputDot);
        dotText.setText(dot.toString());
    }
    // function which checks all the text fields
    private boolean CheckAllFields(String name, String destination, String description, String dot,String require) {

        if (name.length() == 0) {
            nameInput.requestFocus();
            nameInput.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        if (destination.length() == 0) {
            destinationInput.requestFocus();
            destinationInput.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        if (description.length() == 0) {
            descInput.requestFocus();
            descInput.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        if (dot.length() == 0) {
            dotInput.requestFocus();
            dotInput.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        if(!dot.matches(regexCheckData)){
            dotInput.requestFocus();
            dotInput.setError("Not correct format data input year-month-day");
            return false;
        }
        if (require.length() == 0) {
            radioButton.requestFocus();
            radioButton.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        else {
        return true;}
    }
}

