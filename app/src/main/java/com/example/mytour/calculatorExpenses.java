package com.example.mytour;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;


public class calculatorExpenses extends AppCompatActivity {

    Integer sumCostsIncurred, sumTransport,sumFood,sumTravel, total;
    String tripId;
    TextView textView;
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_expenses);

        anyChartView = findViewById(R.id.anyChartView);
        textView = findViewById(R.id.sumEx);


        if(getIntent().hasExtra("sumFood")
                && getIntent().hasExtra("sumTravel")
                && getIntent().hasExtra("sumCostsIncurred")
                && getIntent().hasExtra("sumTransport")
                && getIntent().hasExtra("total")
                && getIntent().hasExtra("ex_trip_Id")){

            // get data from Intent
            tripId = getIntent().getStringExtra("ex_trip_Id");
            total = getIntent().getIntExtra("total", 0);
            sumTravel = getIntent().getIntExtra("sumTravel", 0);
            sumFood = getIntent().getIntExtra("sumFood", 0);
            sumTransport = getIntent().getIntExtra("sumTransport", 0);
            sumCostsIncurred = getIntent().getIntExtra("sumCostsIncurred", 0);

            String convertTotalToString = "Total: " + String.valueOf(total) + " VND";

            textView.setText(convertTotalToString);

            Toast.makeText(calculatorExpenses.this,
                    "TripId = " + tripId
                    + "Total Trip = " + total
                    + " sumFood = " + sumFood
                    + " sumTravel = " + sumTravel
                    + " sumCostsIncurred = " + sumCostsIncurred
                    + " sumTransport = "+ sumTransport , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(calculatorExpenses.this, "No data", Toast.LENGTH_SHORT).show();
        }

        setupChartView();

    }

    private void setupChartView() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        dataEntries.add(new ValueDataEntry("Travel", sumTravel));
        dataEntries.add(new ValueDataEntry("Food", sumFood));
        dataEntries.add(new ValueDataEntry("Transport", sumTransport));
        dataEntries.add(new ValueDataEntry("Costs Incurred", sumCostsIncurred));

        pie.data(dataEntries);
//        pie.title("Spending Analysis");
        anyChartView.setChart(pie);

    }
}