package com.fivos.thesuperherosquadmaker.ui.superHeroDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fivos.thesuperherosquadmaker.R;

public class SuperHeroDetailsFragment extends Fragment {

    private SuperHeroDetailsViewModel mViewModel;

    public static SuperHeroDetailsFragment newInstance() {
        return new SuperHeroDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.super_hero_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SuperHeroDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}