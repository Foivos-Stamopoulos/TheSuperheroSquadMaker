package com.fivos.thesuperherosquadmaker.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Character {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "thumbnailUrl")
    private String thumbnailUrl;

    @ColumnInfo(name = "available")
    private int available;

    @Ignore
    private Comics comics;

    @Ignore
    private Thumbnail thumbnail;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getAvailable() {
        return available;
    }

    public Comics getComics() {
        return comics;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setComics(Comics comics) {
        this.comics = comics;
    }

    public class Comics {

        private int available;

        public int getAvailable() {
            return available;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return id == character.id &&
                Objects.equals(name, character.name) &&
                Objects.equals(description, character.description) &&
                Objects.equals(thumbnailUrl, character.thumbnailUrl) &&
                Objects.equals(thumbnail, character.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, thumbnailUrl, thumbnail);
    }
}

