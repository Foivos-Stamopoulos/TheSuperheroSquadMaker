package com.fivos.thesuperherosquadmaker.ui.superHeroes;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.fivos.thesuperherosquadmaker.api.ApiHelper;
import com.fivos.thesuperherosquadmaker.api.NetworkClient;
import com.fivos.thesuperherosquadmaker.api.MarvelAPI;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.data.CharacterResponse;
import com.fivos.thesuperherosquadmaker.util.Config;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SuperheroDataSource extends PageKeyedDataSource<Integer, Character> {

    // The size of a page that we want
    public static final int PAGE_SIZE = 20;

    // We will start by skipping o superheroes
    private static final int SKIP = 0;

    // This will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Character> callback) {
        String timestamp = ApiHelper.getTimeStamp();
        String hash = ApiHelper.getHash(timestamp);
        NetworkClient.getRetrofit().create(MarvelAPI.class)
                .getCharactersPaged(timestamp, Config.API_PUBLIC_KEY, hash, PAGE_SIZE, SKIP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<CharacterResponse>() {
                    @Override
                    public void onSuccess(CharacterResponse characterResponse) {
                        if (characterResponse != null && characterResponse.getData() != null) {
                            callback.onResult(characterResponse.getData().getResults(), null, SKIP + PAGE_SIZE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    // This will load the previous page
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Character> callback) {
        String timestamp = ApiHelper.getTimeStamp();
        String hash = ApiHelper.getHash(timestamp);
        NetworkClient.getRetrofit().create(MarvelAPI.class)
                .getCharactersPaged(timestamp, Config.API_PUBLIC_KEY, hash, PAGE_SIZE, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<CharacterResponse>() {
                    @Override
                    public void onSuccess(CharacterResponse characterResponse) {
                        // If the current page is greater than one
                        // we are decrementing the page number
                        // else there is no previous page
                        Integer adjacentKey = (params.key > PAGE_SIZE) ? params.key - PAGE_SIZE : null;
                        if (characterResponse != null && characterResponse.getData() != null) {
                            // Passing the loaded data
                            // and the previous page key
                            callback.onResult(characterResponse.getData().getResults(), adjacentKey);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    // This will load the next page
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Character> callback) {
        String timestamp = ApiHelper.getTimeStamp();
        String hash = ApiHelper.getHash(timestamp);
        NetworkClient.getRetrofit().create(MarvelAPI.class)
                .getCharactersPaged(timestamp, Config.API_PUBLIC_KEY, hash, PAGE_SIZE, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<CharacterResponse>() {
                    @Override
                    public void onSuccess(CharacterResponse characterResponse) {
                        if (characterResponse != null && characterResponse.getData() != null) {
                            // If the response has next page
                            // incrementing the next page number
                            //Integer key = response.body().has_more ? params.key + 1 : null;
                            int total = characterResponse.getData().getTotal(); // (1.453)
                            int skip = characterResponse.getData().getOffset();
                            Integer key = (skip < total) ? params.key + PAGE_SIZE : null;

                            // Passing the loaded data and next page value
                            callback.onResult(characterResponse.getData().getResults(), key);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
