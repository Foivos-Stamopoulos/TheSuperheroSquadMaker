package com.fivos.thesuperherosquadmaker.ui.superHeroDetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SuperHeroDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final int id;

    public SuperHeroDetailsViewModelFactory(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SuperHeroDetailsViewModel.class)) {
            return (T) new SuperHeroDetailsViewModel(id);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
