package com.example.githubandroidproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

public class Photo implements Parcelable {
    private String filePath;
    private Set<Tag> tags;
    public Photo(String filePath){
        this.filePath = filePath;
        this.tags = new HashSet<>();
    }

    protected Photo(Parcel in) {
        filePath = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getPath(){
        return filePath;
    }
    public void setPath(String newPath){
        filePath = newPath;
    }

    public Set<Tag> getTags(){
        return tags;
    }
    public void addTag(Tag tag){
        tags.add(tag);
    }

    public void removeTag(Tag tag){
        if(tags.contains(tag))
            tags.remove(tag);
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || (!(o instanceof Tag))) return false;
        Photo other = (Photo) o;
        return filePath.equals(other.getPath());
    }
    public String toString() {
        return "Photo{" + "filePath='" + filePath + '\'' + ", tags=" + tags + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(filePath);
    }
}
