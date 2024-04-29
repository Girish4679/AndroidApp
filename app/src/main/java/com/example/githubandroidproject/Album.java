package com.example.githubandroidproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Album implements Serializable {
    private String name;
    private List<Photo> photos;
    public Album(String name){
        this.name = name;
        this.photos = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }

    public List<Photo> getPhotos(){
        return photos;
    }
    public Photo findPhoto(Photo photo){
        for(Photo p : photos){
            if(p.equals(photo)) return p;
        }
        return null;
    }
    public void setPhotos(List<Photo> photos){
        this.photos = photos;
    }
    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    public void removePhoto(Photo photo){
        if(photos.contains(photo))
            photos.remove(photo);
    }

    public boolean containsPhoto(Photo photo)
    {
        return photos.contains(photo);
    }

    public boolean equals(Object o){
        if (this==o) return true;
        if(o == null || (!(o instanceof Album))) return false;
        Album other = (Album) o;
        return name.equals(other.getName());
    }
    public int hashCode(){
        return Objects.hash(name);
    }
    public String toString() {
        return "Album{" + "name='" + name + '\'' + ", # photos=" + photos.size() +'}';
    }
}
