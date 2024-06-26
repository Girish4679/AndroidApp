package com.example.githubandroidproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class RecyclerAdapter extends RecyclerView.Adapter<AlbumViewHolder>{
    List<Album> albums;
    private Context context;
    private AlbumClickInterface listener;

    public RecyclerAdapter(Context context, AlbumClickInterface listener){
        this.context = context;
        albums = new ArrayList<>();
        this.listener = listener;
    }
    public void setAlbums(List<Album> albums){
        this.albums = albums;
        notifyDataSetChanged();
    }
    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(16, 16, 16, 16);
        return new AlbumViewHolder(textView);
    }
    @Override
    public void onBindViewHolder(AlbumViewHolder viewHolder, int index){
        Album album = albums.get(index);
        viewHolder.textView.setText(album.toString());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onAlbumClick(album);
                }
            }
        });
    }
    @Override
    public int getItemCount(){
        return albums.size();
    }
}
