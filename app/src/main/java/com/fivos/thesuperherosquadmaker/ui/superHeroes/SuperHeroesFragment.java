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
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fivos.thesuperherosquadmaker.Injection;
import com.fivos.thesuperherosquadmaker.R;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.databinding.SuperHeroesFragmentBinding;
import com.fivos.thesuperherosquadmaker.util.MyNetworkUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SuperHeroesFragment extends Fragment implements  SquadAdapter.OnHorizontalListItemClickListener,
        SuperheroPagedAdapter.OnVerticalListItemClickListener{

    private SuperHeroesViewModel mViewModel;
    private SuperHeroesFragmentBinding mBinding;
    private SuperheroPagedAdapter mPagedAdapter;
    private SquadAdapter mSquadAdapter;

    public SuperHeroesFragment() {
        // Empty Constructor
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

        mViewModel = new ViewModelProvider(this,
                new SuperHeroesViewModelFactory(
                        Injection.provideRepository(getActivity()))).get(SuperHeroesViewModel.class);
        subscribeToViewModel();
        if (MyNetworkUtils.isConnected(getActivity())) {
            setupVerticalRecyclerView();
        } else {
            mBinding.noConnectionLL.setVisibility(View.VISIBLE);
        }
        mBinding.retryButton.setOnClickListener(v -> onRetryPressed());
        mViewModel.start();
    }

    private void onRetryPressed() {
        if (MyNetworkUtils.isConnected(getActivity())) {
            mBinding.noConnectionLL.setVisibility(View.GONE);
            setupVerticalRecyclerView();
        } else {
            Snackbar.make(mBinding.getRoot(), getString(R.string.no_connection), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void subscribeToViewModel() {
        mViewModel.getSuperHeroes().observe(getViewLifecycleOwner(), superheroes -> {
            if (superheroes.size() > 0) {
                mBinding.mySquadTV.setVisibility(View.VISIBLE);
                onSquadLoaded(superheroes);
            } else {
                mBinding.mySquadTV.setVisibility(View.GONE);
            }
        });
    }

    private void setupVerticalRecyclerView() {
        mBinding.heroesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.heroesRV.setHasFixedSize(true);

        mPagedAdapter = new SuperheroPagedAdapter();
        mPagedAdapter.setOnVerticalListItemClickListener(this);
        // Observing the PagedList from view model
        mViewModel.itemPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Character>>() {
            @Override
            public void onChanged(@Nullable PagedList<Character> items) {
                // In case of any changes
                // submit the items to adapter
                mPagedAdapter.submitList(items);
            }
        });
        mBinding.heroesRV.setAdapter(mPagedAdapter);
    }

    /**
     * Displays the horizontal Superhero list
     * @param superHeroes the Superheroes that belong to the Squad
     */
    private void onSquadLoaded(List<Character> superHeroes) {
        mSquadAdapter = new SquadAdapter(superHeroes);
        mSquadAdapter.setOnHorizontalListItemClickListener(this);
        // Attach the adapter to the recyclerview to populate items
        mBinding.squadRV.setAdapter(mSquadAdapter);
        // Set layout manager to position the items
        mBinding.squadRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onHorizontalListItemClick(View itemView, int position) {
        onSuperheroPressed(itemView, mSquadAdapter.getCharacterId(position));
    }

    @Override
    public void onVerticalListItemClick(View itemView, int position) {
        onSuperheroPressed(itemView, mPagedAdapter.getCharacterId(position));
    }

    private void onSuperheroPressed(View itemView, int heroId) {
        if (MyNetworkUtils.isConnected(getActivity())) {
            Bundle args = new Bundle();
            args.putInt("hero_id", heroId);
            Navigation.findNavController(itemView).navigate(R.id.action_superHeroesFragment_to_superHeroDetailsFragment, args);
        } else {
            Snackbar.make(mBinding.getRoot(), getString(R.string.no_connection), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

}