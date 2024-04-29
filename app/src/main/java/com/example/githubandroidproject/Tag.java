package com.example.githubandroidproject;

import java.io.Serializable;
import java.util.Objects;

public class Tag implements Serializable {
    private String tagName;
    private String tagValue;
    public Tag(String tagName, String tagValue){
        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    public String getName(){
        return tagName;
    }

    public String getValue(){
        return tagValue;
    }

    public void setName(String name){
        tagName = name;
    }

    public void setValue(String value){
        tagValue = value;
    }

    public String toString(){
        return "Name: " + tagName +", Value: " + tagValue;
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if(o == null || (!(o instanceof Tag))) return false;
        Tag other = (Tag) o;
        return  tagName.equals(other.getName()) && tagValue.equals(other.getValue());
    }

    public int hashCode(){
        return Objects.hash(tagName, tagValue);
    }
}

