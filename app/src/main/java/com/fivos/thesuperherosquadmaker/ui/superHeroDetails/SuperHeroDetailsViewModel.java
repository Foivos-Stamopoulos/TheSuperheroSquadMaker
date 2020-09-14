package com.fivos.thesuperherosquadmaker.ui.superHeroDetails;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fivos.thesuperherosquadmaker.DataSource;
import com.fivos.thesuperherosquadmaker.R;
import com.fivos.thesuperherosquadmaker.api.ApiHelper;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.data.CharacterResponse;
import com.fivos.thesuperherosquadmaker.data.Comic;
import com.fivos.thesuperherosquadmaker.data.ComicsResponse;
import com.fivos.thesuperherosquadmaker.util.Config;
import com.fivos.thesuperherosquadmaker.util.Event;
import com.fivos.thesuperherosquadmaker.util.ParseResponse;

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
    private MutableLiveData<Boolean> shouldRecruit = new MutableLiveData<>();
    private MutableLiveData<Boolean> shouldShowConfirmationDialog = new MutableLiveData<>();
    private final MutableLiveData<Event<Integer>> mSnackbarText = new MutableLiveData<>();
    private final DataSource mRepository;

    public SuperHeroDetailsViewModel(DataSource repository, int heroId) {
        mRepository = repository;
        mId = heroId;
        mDisposable = new CompositeDisposable();
    }

    void start() {
        isLoading.setValue(true);
        fetchSuperHeroFromDB(mId);
    }

    private void fetchSuperHeroFromDB(long heroId) {
        mDisposable.add(mRepository.getSuperhero(heroId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(character -> {
                            superHero.setValue(character);
                            shouldRecruit.setValue(false);
                            fetchComicsFromDB(character.getId());
                        },
                        ex -> {
                            Log.d(TAG, "Error: " + ex.getMessage());
                            isLoading.setValue(false);
                        },
                        () -> {
                            Log.d(TAG, "Completed. Superhero not found in DB.");
                            shouldRecruit.setValue(true);
                            requestSuperHero();
                        })
        );
    }

    private void fetchComicsFromDB(long heroId) {
        mDisposable.add(mRepository.getComicsByHeroId(heroId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comicsList -> {
                            comics.setValue(comicsList);
                            totalComicsAppeared.setValue(comicsList.size());
                            isLoading.setValue(false);
                        },
                        ex -> {
                            Log.d(TAG, "Error: " + ex.getMessage());
                            isLoading.setValue(false);
                        },
                        () -> {
                            Log.d(TAG, "Completed. No comics for this Superhero in DB.");
                            isLoading.setValue(false);
                        })
        );
    }

    void onButtonPressed() {
        Boolean shouldRecruitHero = shouldRecruit.getValue();
        if (shouldRecruitHero != null) {
            if (shouldRecruitHero) {
                insertSuperHeroInDB(superHero.getValue());
            } else {
                shouldShowConfirmationDialog.setValue(true);
            }
        }
    }

    public void onFirePressed() {
        deleteSuperHeroById(mId);
        deleteComicsByHeroId(mId);
    }

    private void insertSuperHeroInDB(Character superhero) {
        mDisposable.add(mRepository.insertSuperhero(superhero)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "hero inserted in DB");
                        List<Comic> comicsList = getSuperheroComics();
                        if (comicsList != null && comicsList.size() > 0) {
                            insertComicsInDB(comicsList);
                        }
                        shouldRecruit.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error inserting hero in DB");
                    }
                }));
    }

    private void deleteSuperHeroById(long heroId) {
        mDisposable.add(mRepository.deleteSuperhero(heroId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Hero deleted from DB");
                        shouldRecruit.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error deleting hero from DB");
                    }
                }));
    }

    private void insertComicsInDB(List<Comic> comics) {
        mDisposable.add(mRepository.insertComics(comics)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Comics inserted in DB");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error inserting comics in DB");
                    }
                }));
    }

    private void deleteComicsByHeroId(long heroId) {
        mDisposable.add(mRepository.deleteComicsByHeroId(heroId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Comics of Superhero deleted from DB");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error deleting comics of Superhero from DB");
                    }
                }));
    }

    private List<Comic> getSuperheroComics() {
        if (comics != null && comics.getValue() != null) {
            List<Comic> comicsList = comics.getValue();
            for (Comic item : comicsList) {
                item.setSuperheroId(mId);
            }
            return comicsList;
        }
        return null;
    }

    private void requestSuperHero() {
        String timestamp = ApiHelper.getTimeStamp();
        String hash = ApiHelper.getHash(timestamp);
        if (hash != null) {
            mDisposable.add(mRepository.getCharacterFromBackend(mId, timestamp, Config.API_PUBLIC_KEY, hash)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<CharacterResponse>() {
                        @Override
                        public void onSuccess(CharacterResponse characterResponse) {
                            if (characterResponse != null && characterResponse.getData() != null) {
                                List<Character> list = characterResponse.getData().getResults();
                                if (list != null && list.size() > 0) {
                                    superHero.setValue(list.get(0));
                                    requestComics();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "error: " + e.getMessage());
                            isLoading.setValue(false);
                            if (ParseResponse.getStatusCode(e) == 404) {
                                mSnackbarText.setValue(new Event<>(R.string.character_not_found));
                            } else {
                                mSnackbarText.setValue(new Event<>(R.string.something_went_wrong));
                            }
                        }
                    }));
        }
    }

    private void requestComics() {
        String timestamp = ApiHelper.getTimeStamp();
        String hash = ApiHelper.getHash(timestamp);
        if (hash != null) {
            mDisposable.add(mRepository.getComicsFromBackend(mId, timestamp, Config.API_PUBLIC_KEY, hash)
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
                            mSnackbarText.setValue(new Event<>(R.string.something_went_wrong));
                        }
                    }));
        }
    }

    LiveData<Character> getSuperHero() {
        return superHero;
    }

    MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    MutableLiveData<List<Comic>> getComics() {
        return comics;
    }

    MutableLiveData<Integer> getTotalComicsAppeared() {
        return totalComicsAppeared;
    }

    MutableLiveData<Boolean> getShouldRecruit() {
        return shouldRecruit;
    }

    LiveData<Boolean> getShouldShowConfirmationDialog() {
        return shouldShowConfirmationDialog;
    }

    LiveData<Event<Integer>> getSnackbarText() {
        return mSnackbarText;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }

}