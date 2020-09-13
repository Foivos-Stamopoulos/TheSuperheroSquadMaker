package com.fivos.thesuperherosquadmaker.data;

import java.util.List;

public class CharacterResponse {

    private int code;
    private Data data;

    public int getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        private int offset;
        private int limit;
        private int count;
        private int total;
        private List<Character> results;

        public Data(int count, List<Character> results) {
            this.count = count;
            this.results = results;
        }

        public int getCount() {
            return count;
        }

        public List<Character> getResults() {
            return results;
        }

        public int getOffset() {
            return offset;
        }

        public int getLimit() {
            return limit;
        }

        public int getTotal() {
            return total;
        }
    }

}
