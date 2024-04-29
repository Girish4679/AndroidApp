package com.example.githubandroidproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> photos;
    private Context context;
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
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

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

