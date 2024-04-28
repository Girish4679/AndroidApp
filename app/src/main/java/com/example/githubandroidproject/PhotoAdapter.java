package com.example.githubandroidproject;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> photos;

    public PhotoAdapter(List<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Photo photo = photos.get(position);
        Drawable draw = Drawable.createFromPath(photo.getPath());
        holder.img.setImageDrawable(draw);

        // Bind photo data to views in ViewHolder
        // For example: holder.photoImageView.setImageResource(photo.getResId());
        // You'll need to replace 'photo.getResId()' with the appropriate method to retrieve the photo resource
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        // For example: public ImageView photoImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            // For example: photoImageView = itemView.findViewById(R.id.photoImageView);
            img = itemView.findViewById(R.id.imageID);
        }
    }
}

