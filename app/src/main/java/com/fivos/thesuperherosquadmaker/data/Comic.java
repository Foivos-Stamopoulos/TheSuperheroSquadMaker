package com.fivos.thesuperherosquadmaker.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Comic {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "thumbnailUrl")
    private String thumbnailUrl;

    @ColumnInfo(name = "superheroId")
    private int superheroId;

    @Ignore
    private Thumbnail thumbnail;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getSuperheroId() {
        return superheroId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setSuperheroId(int superheroId) {
        this.superheroId = superheroId;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}
