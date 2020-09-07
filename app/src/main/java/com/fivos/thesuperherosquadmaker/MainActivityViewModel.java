package com.fivos.thesuperherosquadmaker;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fivos.thesuperherosquadmaker.api.ApiHelper;
import com.fivos.thesuperherosquadmaker.api.NetworkClient;
import com.fivos.thesuperherosquadmaker.api.SuperHeroesAPI;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.data.CharacterResponse;
import com.fivos.thesuperherosquadmaker.util.Config;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private CompositeDisposable mDisposable;
    private MutableLiveData<List<Character>> superHeroes = new MutableLiveData<>();

    public MainActivityViewModel() {
        mDisposable = new CompositeDisposable();
        fetchSuperHeroes();
    }

    void fetchSuperHeroes() {
        String timestamp = ApiHelper.getTimeStamp();
        String hash = ApiHelper.getHash(timestamp);
        if (hash != null) {
            mDisposable.add(NetworkClient.getRetrofit().create(SuperHeroesAPI.class)
                    .getCharacters(timestamp, Config.API_PUBLIC_KEY, hash)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<CharacterResponse>() {
                        @Override
                        public void onSuccess(CharacterResponse characterResponse) {
                            if (characterResponse != null && characterResponse.getData() != null) {
                                superHeroes.setValue(characterResponse.getData().getResults());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "error: " + e.getMessage());
                        }
                    }));
        }
    }

    public LiveData<List<Character>> getSuperHeroes() {
        return superHeroes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }
}
