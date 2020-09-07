package com.fivos.thesuperherosquadmaker.ui.superHeroes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fivos.thesuperherosquadmaker.R;
import com.fivos.thesuperherosquadmaker.api.ApiHelper;
import com.fivos.thesuperherosquadmaker.api.NetworkClient;
import com.fivos.thesuperherosquadmaker.api.SuperHeroesAPI;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.data.CharacterResponse;
import com.fivos.thesuperherosquadmaker.databinding.SuperHeroesFragmentBinding;
import com.fivos.thesuperherosquadmaker.util.Config;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SuperHeroesFragment extends Fragment implements SuperHeroAdapter.OnItemClickListener{

    private SuperHeroesViewModel mViewModel;
    private SuperHeroesFragmentBinding mBinding;
    private SuperHeroAdapter mAdapter;
    private DisposableSingleObserver<CharacterResponse> mDisposable;
    private static final String TAG = SuperHeroesFragment.class.getSimpleName();

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
        mViewModel = ViewModelProviders.of(this).get(SuperHeroesViewModel.class);
        fetchSuperHeroes();

    }

    private void fetchSuperHeroes() {
        String timestamp = ApiHelper.getTimeStamp();
        String hash = ApiHelper.getHash(timestamp);
        if (hash != null) {
            mDisposable = NetworkClient.getRetrofit().create(SuperHeroesAPI.class)
                    .getCharacters(timestamp, Config.API_PUBLIC_KEY, hash)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<CharacterResponse>() {
                        @Override
                        public void onSuccess(CharacterResponse characterResponse) {
                            if (characterResponse != null && characterResponse.getData() != null) {
                                onSuperHeroesLoaded(characterResponse.getData().getResults());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "error: " + e.getMessage());
                        }
                    });
        }

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
        args.putString("hero_id", mAdapter.getCharacterId(position));
        Navigation.findNavController(itemView).navigate(R.id.action_superHeroesFragment_to_superHeroDetailsFragment, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

}