package com.example.mytour;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    Context context;
    ArrayList ex_id,ex_type,ex_amount,ex_toe;

    public ExpenseAdapter(Context context, ArrayList ex_id, ArrayList ex_type,
                         ArrayList ex_amount, ArrayList ex_toe){
        this.context = context;
        this.ex_id = ex_id;
        this.ex_type = ex_type;
        this.ex_amount = ex_amount;
        this.ex_toe = ex_toe;
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
        holder.ex_id_TXT.setText(String.valueOf(ex_id.get(position)));
        holder.ex_type_TXT.setText(String.valueOf(ex_type.get(position)));
        holder.ex_toe_TXT.setText(String.valueOf(ex_amount.get(position)));
        holder.ex_amount_TXT.setText(String.valueOf(ex_toe.get(position)));

        holder.expensesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailItem.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", String.valueOf(ex_id.get(position)));
                intent.putExtra("type", String.valueOf(ex_type.get(position)));
                intent.putExtra("amount", String.valueOf(ex_amount.get(position)));
                intent.putExtra("time_of_the_expense", String.valueOf(ex_toe.get(position)));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ex_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout expensesLayout;
        TextView ex_id_TXT, ex_type_TXT, ex_toe_TXT, ex_amount_TXT;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ex_id_TXT = itemView.findViewById(R.id.ex_id_txt);
            ex_type_TXT = itemView.findViewById(R.id.ex_type_txt);
            ex_toe_TXT = itemView.findViewById(R.id.ex_toe_txt);
            ex_amount_TXT = itemView.findViewById(R.id.ex_amount_txt);
            expensesLayout = itemView.findViewById(R.id.expensesLayout);

        }
    }
}
