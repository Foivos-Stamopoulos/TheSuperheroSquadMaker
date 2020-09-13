package com.fivos.thesuperherosquadmaker.ui.superHeroes;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.fivos.thesuperherosquadmaker.data.Character;

public class SuperheroDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Character>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Character> create() {
        SuperheroDataSource itemDataSource = new SuperheroDataSource();
        // Posting the dataSource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        // Returning the dataSource
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Character>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }

}
