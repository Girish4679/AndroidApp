package com.example.githubandroidproject;

import java.time.LocalDate;

/**
 * Album display helper class which helps the controller dispaly the albums
 * @author Girish Ranganathan, Rushil Thummaluru
 */
public class AlbumDisplay {
    private String albumName;
    private int numPhotos;
    private Album album;
    /**
     * Constructor for albumdisplay, used in the AlbumDisplayController.
     * @param album Takes in the album that is going to be displayed
     * @param albumName Takes in the album name
     * @param numPhotos Takes in the number of photos that are going to be displayed
     * @param dateRange Takes in the date range of the photos in the album
     */
    public AlbumDisplay(Album album, String albumName, int numPhotos){
        this.album = album;
        this.albumName = albumName;
        this.numPhotos = numPhotos;
    }
    /**
     * Get method for album
     * @return Returns the album
     */
    public Album getAlbum(){
        return album;
    }
    /**
     * get method for getNumphotos
     * @return Returns the number of photos
     */
    public int getNumPhotos(){
        return numPhotos;
    }
    /**
     * getter method for album name
     * @return Returns the album name
     */
    public String getAlbumName(){
        return albumName;
    }
    /**
     * getter method for dataRange
     * @return Returns the dateRange
     */
    /**
     * toString method to print the attributes
     */
    public String toString(){
        return "Name: "+albumName+", Number of Pictures: "+numPhotos;
    }
}
