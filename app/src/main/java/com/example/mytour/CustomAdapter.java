package com.example.mytour;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList trip_id, trip_title, trip_destination,trip_description, trip_dot, trip_require;


    public CustomAdapter(Context context, ArrayList trip_id, ArrayList trip_title,
                         ArrayList trip_destination,ArrayList trip_description,
                         ArrayList trip_dot, ArrayList trip_require){
        this.context = context;
        this.trip_id = trip_id;
        this.trip_title = trip_title;
        this.trip_destination = trip_destination;
        this.trip_dot = trip_dot;
        this.trip_require = trip_require;
        this.trip_description = trip_description;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.trip_id_txt.setText(String.valueOf(trip_id.get(position)));
        holder.trip_title_txt.setText(String.valueOf(trip_title.get(position)));
        holder.trip_destination_txt.setText(String.valueOf(trip_destination.get(position)));
        holder.trip_dot_txt.setText(String.valueOf(trip_dot.get(position)));
        holder.trip_require_txt.setText(String.valueOf(trip_require.get(position)));
        holder.trip_description_txt.setText(String.valueOf(trip_description.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UpdateActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", String.valueOf(trip_id.get(position)));
                intent.putExtra("title", String.valueOf(trip_title.get(position)));
                intent.putExtra("destination", String.valueOf(trip_destination.get(position)));
                intent.putExtra("description", String.valueOf(trip_description.get(position)));
                intent.putExtra("dot", String.valueOf(trip_dot.get(position)));
                intent.putExtra("require", String.valueOf(trip_require.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trip_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trip_id_txt, trip_title_txt, trip_destination_txt, trip_require_txt, trip_dot_txt, trip_description_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_title_txt = itemView.findViewById(R.id.trip_title_txt);
            trip_destination_txt = itemView.findViewById(R.id.trip_destination_txt);
            trip_description_txt = itemView.findViewById(R.id.trip_description_txt);
            trip_dot_txt = itemView.findViewById(R.id.trip_dot_txt);
            trip_require_txt = itemView.findViewById(R.id.trip_require_txt);

            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
