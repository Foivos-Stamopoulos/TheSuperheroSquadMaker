package com.fivos.thesuperherosquadmaker.data;

import java.util.List;

public class ComicsResponse {

    private int code;
    private Data data;

    public ComicsResponse(int code, Data data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        private int total;
        private int count;
        private List<Comics> results;

        public Data(int count, List<Comics> results) {
            this.count = count;
            this.results = results;
        }

        public int getCount() {
            return count;
        }

        public List<Comics> getResults() {
            return results;
        }

        public int getTotal() {
            return total;
        }

        public class Comics {

            private int id;
            private String title;
            private Thumbnail thumbnail;

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

                public String getUrl() {
                    return path + "." + extension;
                }

            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public Thumbnail getThumbnail() {
                return thumbnail;
            }
        }

    }

}
