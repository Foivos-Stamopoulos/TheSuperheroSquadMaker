package com.fivos.thesuperherosquadmaker.ui.superHeroes;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fivos.thesuperherosquadmaker.DataSource;
import com.fivos.thesuperherosquadmaker.data.Character;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SuperHeroesViewModel extends ViewModel {

    private final DataSource mDataSource;
    private CompositeDisposable mDisposable;
    private static final String TAG = SuperHeroesViewModel.class.getSimpleName();
    private MutableLiveData<List<Character>> mSuperHeroes = new MutableLiveData<>();

    public SuperHeroesViewModel(DataSource dataSource) {
        mDataSource = dataSource;
        mDisposable = new CompositeDisposable();
    }

    void start() {
        fetchSuperHeroesFromDB();
    }

    private void fetchSuperHeroesFromDB() {
        mDisposable.add(mDataSource.getAllSuperheroes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Character>>() {
                    @Override
                    public void accept(List<Character> superheroesList) throws Exception {
                        Log.d(TAG, "Superheroes list size: " + superheroesList.size());
                        mSuperHeroes.setValue(superheroesList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Log.d(TAG, "Fetch superheroes error: " + e.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "Superheroes action");
                    }
                }));
    }

    MutableLiveData<List<Character>> getSuperHeroes() {
        return mSuperHeroes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }
}