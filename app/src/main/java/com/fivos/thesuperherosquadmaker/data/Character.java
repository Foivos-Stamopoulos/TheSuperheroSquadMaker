package com.fivos.thesuperherosquadmaker.data;

import java.util.List;

public class Character {

    private String id;
    private String name;
    private String description;
    private String modified;
    private Thumbnail thumbnail;
    private String resourceURI;
    private Comics comics;

    public Character(String id, String name, String description, String modified,
                     Thumbnail thumbnail, String resourceURI, Comics comics) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modified = modified;
        this.thumbnail = thumbnail;
        this.resourceURI = resourceURI;
        this.comics = comics;
    }

    public String getId() {
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
