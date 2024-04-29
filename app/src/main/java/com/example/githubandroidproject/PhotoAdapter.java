package com.example.githubandroidproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class PhotoAdapter //extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>
{
    private List<Photo> photos;
    private Context context;
    public PhotoAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    //@Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    //@Override
    /*
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        Photo photo = photos.get(position);
        Picasso.get()
                .load(Uri.parse(photo.getPath()))
                .placeholder(R.drawable.placeholder) // Optional: Displayed while the image is loading
                .error(R.drawable.error) // Optional: Displayed if there's an error loading the image
                .into(holder.img);
        StringBuilder tagBuilder = new StringBuilder();
        for (Tag tag : photo.getTags()) { // Assuming getTags() returns a Collection<String>
            tagBuilder.append(tag.getName()+": "+tag.getValue()).append("\n");
        }
        if (tagBuilder.length() > 0) {
            tagBuilder.setLength(tagBuilder.length() - 2);
        }
        holder.tags.setText(tagBuilder.toString());
        // Bind photo data to views in ViewHolder
        // For example: holder.photoImageView.setImageResource(photo.getResId());
        // You'll need to replace 'photo.getResId()' with the appropriate method to retrieve the photo resource
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView tags;
        // For example: public ImageView photoImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            // For example: photoImageView = itemView.findViewById(R.id.photoImageView);
            img = itemView.findViewById(R.id.imageID);
            tags = itemView.findViewById(R.id.tagsView);
        }
    }
}

