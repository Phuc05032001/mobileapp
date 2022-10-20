package com.example.mytour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private Context context;
    private ArrayList ex_id,ex_type,ex_amount,ex_toe,ex_trip_id;

    public ExpenseAdapter(Context context, ArrayList ex_id, ArrayList ex_type,
                         ArrayList ex_amount, ArrayList ex_toe, ArrayList ex_trip_id){
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_expenses, parent, false);
        return new ExpenseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ex_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout expensesLayout;
        TextView ex_type_txt, ex_toe_txt, ex_amount_txt, ex_id_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ex_type_txt = itemView.findViewById(R.id.ex_type_txt);
            ex_toe_txt = itemView.findViewById(R.id.ex_toe_txt);
            ex_id_txt = itemView.findViewById(R.id.ex_id_txt);
            ex_amount_txt = itemView.findViewById(R.id.ex_amount_txt);



            expensesLayout = itemView.findViewById(R.id.expensesLayout);

        }
    }
}
