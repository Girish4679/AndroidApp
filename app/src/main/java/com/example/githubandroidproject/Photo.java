package com.example.githubandroidproject;

import java.util.HashSet;
import java.util.Set;

public class Photo {
    private String filePath;
    private Set<Tag> tags;
    public Photo(String filePath){
        this.filePath = filePath;
        this.tags = new HashSet<>();
    }
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
}
