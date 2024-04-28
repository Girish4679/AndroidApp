package com.example.githubandroidproject;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    public AlbumViewHolder(TextView view) {
        super(view);
        textView = view;
        // Define click listener for the ViewHolder's View
    }
}