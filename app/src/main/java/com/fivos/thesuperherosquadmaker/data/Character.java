package com.fivos.thesuperherosquadmaker.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Character {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "modified")
    private String modified;

    //@ColumnInfo(name = "thumbnail")
    private Thumbnail thumbnail;

    @ColumnInfo(name = "resourceURI")
    private String resourceURI;

    private Comics comics;

    public Character(int id, String name, String description, String modified,
                     Thumbnail thumbnail, String resourceURI, Comics comics) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modified = modified;
        //this.thumbnail = thumbnail;
        this.resourceURI = resourceURI;
        this.comics = comics;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getModified() {
        return modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    /*

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = new Gson().toJson(thumbnail);
    }*/

    public String getResourceURI() {
        return resourceURI;
    }

    public Comics getComics() {
        return comics;
    }

    public class Thumbnail {

        private String path;
        private String extension;

        public Thumbnail(String path, String extension) {
            this.path = path;
            this.extension = extension;
        }

        public String getPath() {
            return path;
        }

        public String getExtension() {
            return extension;
        }
    }

    class Comics {

        private int available;
        private String collectionURI;
        private List<Item> items;
        private int returned;

        public Comics(int available, String collectionURI, List<Item> items, int returned) {
            this.available = available;
            this.collectionURI = collectionURI;
            this.items = items;
            this.returned = returned;
        }

        public int getAvailable() {
            return available;
        }

        public String getCollectionURI() {
            return collectionURI;
        }

        public List<Item> getItems() {
            return items;
        }

        public int getReturned() {
            return returned;
        }

        class Item {

            private String resourceURI;
            private String name;

            public Item(String resourceURI, String name) {
                this.resourceURI = resourceURI;
                this.name = name;
            }
        }

    }
}
