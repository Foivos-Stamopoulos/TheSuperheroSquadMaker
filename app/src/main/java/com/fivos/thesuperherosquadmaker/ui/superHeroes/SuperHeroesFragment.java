package com.fivos.thesuperherosquadmaker.ui.superHeroes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fivos.thesuperherosquadmaker.MainActivityViewModel;
import com.fivos.thesuperherosquadmaker.R;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.data.CharacterResponse;
import com.fivos.thesuperherosquadmaker.databinding.SuperHeroesFragmentBinding;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;

public class SuperHeroesFragment extends Fragment implements SuperHeroAdapter.OnItemClickListener{

    private SuperHeroesViewModel mViewModel;
    private SuperHeroesFragmentBinding mBinding;
    private SuperHeroAdapter mAdapter;
    private DisposableSingleObserver<CharacterResponse> mDisposable;
    private static final String TAG = SuperHeroesFragment.class.getSimpleName();
    private MainActivityViewModel mSharedViewModel;

    public static SuperHeroesFragment newInstance() {
        return new SuperHeroesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = SuperHeroesFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(SuperHeroesViewModel.class);
        mSharedViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        subscribeToSharedViewModel();
    }

    private void subscribeToSharedViewModel() {
        mSharedViewModel.getSuperHeroes().observe(getViewLifecycleOwner(), new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> superHeroes) {
                if (superHeroes != null) {
                    onSuperHeroesLoaded(superHeroes);
                }
            }
        });
    }

    private void onSuperHeroesLoaded(List<Character> superHeroes) {
        mAdapter = new SuperHeroAdapter(superHeroes);
        mAdapter.setOnItemClickListener(this);
        // Attach the adapter to the recyclerview to populate items
        mBinding.heroesRV.setAdapter(mAdapter);
        // Set layout manager to position the items
        mBinding.heroesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Bundle args = new Bundle();
        args.putInt("hero_id", mAdapter.getCharacterId(position));
        Navigation.findNavController(itemView).navigate(R.id.action_superHeroesFragment_to_superHeroDetailsFragment, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

}