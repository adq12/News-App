package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView textTitle, textSource;
    ImageView img_headline;
    CardView cardView;
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        textTitle=itemView.findViewById(R.id.txt_title);
        img_headline=itemView.findViewById(R.id.img_headline);
        textSource=itemView.findViewById(R.id.txt_source);
        cardView=itemView.findViewById(R.id.main_container);

    }
}
