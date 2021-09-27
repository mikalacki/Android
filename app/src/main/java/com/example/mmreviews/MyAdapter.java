package com.example.mmreviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<PlaceReview> list;

    public MyAdapter(Context context, ArrayList<PlaceReview> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PlaceReview placeReview = list.get(position);
        holder.user.setText(placeReview.getUser());
        holder.comment.setText(placeReview.getComment());
        holder.ratingAvg.setText((String.format("" + placeReview.getAvarage())));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView user, comment, ratingAvg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.userCard);
            comment = itemView.findViewById(R.id.commentCard);
            ratingAvg = itemView.findViewById(R.id.ratingAvg);
        }
    }
}
