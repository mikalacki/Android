package com.example.mmreviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterNotifications extends RecyclerView.Adapter<MyAdapterNotifications.MyViewHolder> {

    Context context;

    ArrayList<PlaceReview> list;

    public MyAdapterNotifications(Context context, ArrayList<PlaceReview> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PlaceReview placeReview = list.get(position);
        holder.user.setText(placeReview.getUser());
        holder.comment.setText(placeReview.getComment());
        holder.place.setText(placeReview.getPlaceName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView user, comment, place;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.userNotification);
            comment = itemView.findViewById(R.id.commentNotification);
            place = itemView.findViewById(R.id.placeNotification);
        }
    }
}
