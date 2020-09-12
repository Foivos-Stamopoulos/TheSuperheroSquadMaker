package com.fivos.thesuperherosquadmaker.ui.superHeroes;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fivos.thesuperherosquadmaker.DataSource;

public class SuperHeroesViewModelFactory implements ViewModelProvider.Factory {

    private final DataSource mDataSource;

    public SuperHeroesViewModelFactory(DataSource dataSource) {
        mDataSource = dataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SuperHeroesViewModel.class)) {
            return (T) new SuperHeroesViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
