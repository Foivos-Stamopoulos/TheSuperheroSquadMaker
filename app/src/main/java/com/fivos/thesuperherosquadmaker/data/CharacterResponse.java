package com.fivos.thesuperherosquadmaker.data;

import java.util.List;

public class CharacterResponse {

    private int code;
    private Data data;

    public CharacterResponse(int code, Data data) {
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

        private int count;
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
    }

}
