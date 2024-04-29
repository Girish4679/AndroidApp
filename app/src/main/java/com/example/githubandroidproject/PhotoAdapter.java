package com.example.githubandroidproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> photos;
    private Context context;
    public interface OnTagClickListener {
        void onDeleteClicked(Photo photo);
    }
    public interface OnPhotoSelectedListener {
        void onPhotoSelected(List<Photo> selectedPhotos);
    }

    private List<Photo> selectedPhotos = new ArrayList<>();
    private OnTagClickListener tagClickListener;

    public PhotoAdapter(Context context, List<Photo> photos, OnTagClickListener listener) {
        this.context = context;
        this.photos = photos;
        this.tagClickListener = listener;
    private SlideshowInterface listener;
    public PhotoAdapter(Context context, List<Photo> photos,SlideshowInterface listener) {
        this.context = context;
        this.photos = photos;
        this.listener = listener;
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
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(photo.getPath()));
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.img.setImageBitmap(bitmap);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        StringBuilder tagBuilder = new StringBuilder();
        for (Tag tag : photo.getTags()) { // Assuming getTags() returns a Collection<String>
            tagBuilder.append(tag.getName()+": "+tag.getValue()).append("\n");
        }
        if (tagBuilder.length() > 0) {
            tagBuilder.setLength(tagBuilder.length() - 2);
        }
        holder.tags.setText(tagBuilder.toString());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onPhotoClick(photos,position);
                }
            }
        });
        // Bind photo data to views in ViewHolder
        // For example: holder.photoImageView.setImageResource(photo.getResId());
        // You'll need to replace 'photo.getResId()' with the appropriate method to retrieve the photo resource


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notify the activity or fragment about delete button click
                if (tagClickListener != null) {
                    tagClickListener.onDeleteClicked(photo);
                }
            }
        });
        /*
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle selection state of the photo
                boolean isSelected = !photo.isSelected();
                photo.setSelected(isSelected);
                notifyItemChanged(position);
                // Update the list of selected photos in the activity
                if (isSelected) {
                    selectedPhotos.add(photo);
                } else {
                    selectedPhotos.remove(photo);
                }
                Log.d("THE IMAGE IS SELECTED", String.valueOf(selectedPhotos.size()));
            }
        });

         */

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
    public void updatePhotos(List<Photo> newPhotos) {
        photos.clear();
        photos.addAll(newPhotos);
        notifyDataSetChanged(); // Notify adapter about data change
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView tags;

        public Button delete;
        // For example: public ImageView photoImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            // For example: photoImageView = itemView.findViewById(R.id.photoImageView);
            img = itemView.findViewById(R.id.imageID);
            tags = itemView.findViewById(R.id.tagsView);
            delete = itemView.findViewById(R.id.deletePhotoButton);
        }
    }
}