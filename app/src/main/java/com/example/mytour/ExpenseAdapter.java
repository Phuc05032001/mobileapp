package com.example.mytour;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private Context context;
    private ArrayList ex_id,ex_type,ex_amount,ex_toe,ex_trip_id;

    public ExpenseAdapter(Context context, ArrayList ex_id, ArrayList ex_type,
                         ArrayList ex_amount, ArrayList ex_toe,
                         ArrayList ex_trip_id){
        this.context = context;
        this.ex_id = ex_id;
        this.ex_type = ex_type;
        this.ex_amount = ex_amount;
        this.ex_toe = ex_toe;
        this.ex_trip_id = ex_trip_id;
    }

    @NonNull
    @Override
    public ExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
