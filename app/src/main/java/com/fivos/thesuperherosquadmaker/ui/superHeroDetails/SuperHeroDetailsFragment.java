package com.fivos.thesuperherosquadmaker.ui.superHeroDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fivos.thesuperherosquadmaker.R;
import com.fivos.thesuperherosquadmaker.data.Character;

public class SuperHeroDetailsFragment extends Fragment {

    private SuperHeroDetailsViewModel mViewModel;
    private int mId;

    public SuperHeroDetailsFragment() {
        // Empty Constructor
    }

    public static SuperHeroDetailsFragment newInstance() {
        return new SuperHeroDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("hero_id")) {
            mId = getArguments().getInt("hero_id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.super_hero_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new SuperHeroDetailsViewModelFactory(mId))
                .get(SuperHeroDetailsViewModel.class);
        subscribeToViewModel();
        mViewModel.fetchSuperHero();
    }

    private void subscribeToViewModel() {
        mViewModel.getSuperHero().observe(getViewLifecycleOwner(), new Observer<Character>() {
            @Override
            public void onChanged(Character character) {
                if (character != null) {
                    Toast.makeText(getActivity(), character.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}