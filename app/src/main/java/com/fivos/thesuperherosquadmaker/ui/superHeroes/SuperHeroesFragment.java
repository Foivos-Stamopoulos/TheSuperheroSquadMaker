package com.fivos.thesuperherosquadmaker.ui.superHeroes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fivos.thesuperherosquadmaker.databinding.SuperHeroesFragmentBinding;

public class SuperHeroesFragment extends Fragment {

    private SuperHeroesViewModel mViewModel;
    private SuperHeroesFragmentBinding binding;

    public static SuperHeroesFragment newInstance() {
        return new SuperHeroesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SuperHeroesFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SuperHeroesViewModel.class);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}