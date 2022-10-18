package com.example.mytour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, destination_input, description_input, dot_input ;

    Button update_button, delete_button, repenses_button;

    String id, title, destination,description, dot, require;

    RadioGroup require_radioGroup;
    RadioButton require_radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.inputName2);
        destination_input = findViewById(R.id.inputDestination2);
        description_input = findViewById(R.id.inputDesc2);
        dot_input = findViewById(R.id.inputDot2);
        require_radioGroup = findViewById(R.id.radioGroup2);
        update_button = findViewById(R.id.updateButtom);
        delete_button = findViewById(R.id.deleteButton);
        repenses_button = findViewById(R.id.expensesButton);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        getAndSetIntentData();

        //Set actionbar
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(title);
        }


        update_button.setOnClickListener((view)->{

            // get selected radio button from radioGroup
            int selectedId = require_radioGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            require_radioButton = (RadioButton) findViewById(selectedId);

            MyDatabase myDB = new MyDatabase(UpdateActivity.this);
            title = title_input.getText().toString().trim();
            destination = destination_input.getText().toString().trim();
            description = description_input.getText().toString().trim();
            dot = dot_input.getText().toString().trim();
            require = require_radioButton.getText().toString().trim();

            // store the returned value of the dedicated function which checks
            // whether the entered data is valid or if any fields are left blank.

            boolean check = CheckAllFields(title,destination,description,dot,require);
            // the boolean variable turns to be true then
            // only the user must be proceed to the activity

            if (check) {
                myDB.updateData(id, title, destination, description, dot, require);

                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            }
            else {
                Toast.makeText(getApplicationContext(), "check information again", Toast.LENGTH_SHORT).show();
            }
        });

        delete_button.setOnClickListener((View)->{
            MyDatabase myDB = new MyDatabase(UpdateActivity.this);
            myDB.deleteOneRow(id);

            startActivity(new Intent(UpdateActivity.this, MainActivity.class));
        });

        repenses_button.setOnClickListener((view)->{
            Intent intent = new Intent(this,Expenses.class);
            intent.putExtra("ex_trip_Id",id);
            intent.putExtra("trip_title",title);
            startActivity(intent);
        });

    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id")
                && getIntent().hasExtra("title")
                && getIntent().hasExtra("destination")
                && getIntent().hasExtra("dot")
                && getIntent().hasExtra("require")
                && getIntent().hasExtra("description")){

            // get data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            destination = getIntent().getStringExtra("destination");
            dot = getIntent().getStringExtra("dot");
            require = getIntent().getStringExtra("require");
            description = getIntent().getStringExtra("description");

            //setting intent data
            title_input.setText(title);
            destination_input.setText(destination);
            dot_input.setText(dot);
            description_input.setText(description);
            if(require.equals("Yes")){
                require_radioButton = findViewById(R.id.radioButtonYes2);
            }else{
                require_radioButton = findViewById(R.id.radioButtonNo2);
            }
            require_radioButton.setChecked(true);
        }
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerUpdateFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void updateDOT(@NonNull LocalDate dot){
        TextView dotText = (TextView) findViewById(R.id.inputDot2);
        dotText.setText(dot.toString());
    }
    // function which checks all the text fields
    private boolean CheckAllFields(String title, String destination, String description, String dot,String require) {

        if (title.length() == 0) {
            title_input.requestFocus();
            title_input.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        if (destination.length() == 0) {
            destination_input.requestFocus();
            destination_input.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        if (description.length() == 0) {
            description_input.requestFocus();
            description_input.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        if (dot.length() == 0) {
            dot_input.requestFocus();
            dot_input.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        if(!dot.matches("^\\d{4}[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?(([0-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?(([0-2][0-9])|(30)))|(02[\\-\\/\\s]?[0-2][0-9]))$")){
            dot_input.requestFocus();
            dot_input.setError("Not correct format data input year-month-day");
            return false;
        }
        if (require.length() == 0) {
            require_radioButton.requestFocus();
            require_radioButton.setError("FILL CANNOT BE EMPTY");
            return false;
        }
        else {
            return true;}
    }

}