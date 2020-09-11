package com.fivos.thesuperherosquadmaker.ui.superHeroDetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fivos.thesuperherosquadmaker.DataSource;

public class SuperHeroDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final DataSource mDataSource;
    private final int mHeroId;

    public SuperHeroDetailsViewModelFactory(DataSource dataSource, int heroId) {
        mDataSource = dataSource;
        mHeroId = heroId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SuperHeroDetailsViewModel.class)) {
            return (T) new SuperHeroDetailsViewModel(mDataSource, mHeroId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
