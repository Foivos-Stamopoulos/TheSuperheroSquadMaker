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
        private List<Comic> results;

        public Data(int count, List<Comic> results) {
            this.count = count;
            this.results = results;
        }

        public int getCount() {
            return count;
        }

        public List<Comic> getResults() {
            return results;
        }

        public int getTotal() {
            return total;
        }
    }

}
