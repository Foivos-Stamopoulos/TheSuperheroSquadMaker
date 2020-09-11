package com.fivos.thesuperherosquadmaker.ui.superHeroDetails;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fivos.thesuperherosquadmaker.DataSource;
import com.fivos.thesuperherosquadmaker.api.ApiHelper;
import com.fivos.thesuperherosquadmaker.api.NetworkClient;
import com.fivos.thesuperherosquadmaker.api.SuperHeroesAPI;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.data.CharacterResponse;
import com.fivos.thesuperherosquadmaker.data.Comic;
import com.fivos.thesuperherosquadmaker.data.ComicsResponse;
import com.fivos.thesuperherosquadmaker.util.Config;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SuperHeroDetailsViewModel extends ViewModel {

    private static final String TAG = SuperHeroDetailsViewModel.class.getSimpleName();
    private CompositeDisposable mDisposable;
    private int mId;
    private MutableLiveData<Character> superHero = new MutableLiveData<>();
    private MutableLiveData<List<Comic>> comics = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Integer> totalComicsAppeared = new MutableLiveData<>();
    private final DataSource mDataSource;

    public SuperHeroDetailsViewModel(DataSource dataSource, int heroId) {
        mDataSource = dataSource;
        mId = heroId;
        mDisposable = new CompositeDisposable();
    }

    void start() {
        isLoading.setValue(true);
        // TODO: 8/9/2020 check first if hero exists in DB else make request !!
        fetchSuperHeroFromDB(mId);
        //fetchSuperHero();
    }

    void fetchSuperHeroFromDB(long heroId) {
        mDisposable.add(mDataSource.getSuperhero(heroId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(character -> {
                            if (character == null) {
                                Log.d(TAG, "hero is null");
                            } else {
                                Log.d(TAG, "hero name is: " + character.getName());
                            }
                        }
                ));
    }

    public void onButtonPressed() {
        insertSuperHeroInDB(superHero.getValue());
    }

    void insertSuperHeroInDB(Character superhero) {
        mDisposable.add(mDataSource.insertSuperhero(superhero)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "hero inserted in DB");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error inserting hero in DB");
                    }
                }));
    }

    void fetchSuperHero() {
        String timestamp = ApiHelper.getTimeStamp();
        String hash = ApiHelper.getHash(timestamp);
        if (hash != null) {
            mDisposable.add(NetworkClient.getRetrofit().create(SuperHeroesAPI.class)
                    .getCharacter(mId, timestamp, Config.API_PUBLIC_KEY, hash)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<CharacterResponse>() {
                        @Override
                        public void onSuccess(CharacterResponse characterResponse) {
                            if (characterResponse != null && characterResponse.getData() != null) {
                                List<Character> list = characterResponse.getData().getResults();
                                if (list != null && list.size() > 0) {
                                    superHero.setValue(list.get(0));
                                    fetchComics();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "error: " + e.getMessage());
                            isLoading.setValue(false);
                        }
                    }));
        }
    }

    void fetchComics() {
        String timestamp = ApiHelper.getTimeStamp();
        String hash = ApiHelper.getHash(timestamp);
        if (hash != null) {
            mDisposable.add(NetworkClient.getRetrofit().create(SuperHeroesAPI.class)
                    .getComics(mId, timestamp, Config.API_PUBLIC_KEY, hash)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<ComicsResponse>() {
                        @Override
                        public void onSuccess(ComicsResponse characterResponse) {
                            if (characterResponse != null && characterResponse.getData() != null) {
                                List<Comic> list = characterResponse.getData().getResults();
                                comics.setValue(list);
                                totalComicsAppeared.setValue(characterResponse.getData().getTotal());
                            }
                            isLoading.setValue(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "error: " + e.getMessage());
                            isLoading.setValue(false);
                        }
                    }));
        }
    }

    public LiveData<Character> getSuperHero() {
        return superHero;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<Comic>> getComics() {
        return comics;
    }

    public MutableLiveData<Integer> getTotalComicsAppeared() {
        return totalComicsAppeared;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }

}