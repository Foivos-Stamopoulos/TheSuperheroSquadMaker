package com.fivos.thesuperherosquadmaker.data;

import androidx.annotation.NonNull;

import com.fivos.thesuperherosquadmaker.DataSource;
import com.fivos.thesuperherosquadmaker.api.MarvelAPI;
import com.fivos.thesuperherosquadmaker.api.NetworkClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class Repository implements DataSource {

    private volatile static Repository INSTANCE = null;

    private final DataSource mLocalDataSource;

    private Repository(@NonNull DataSource localDataSource) {
        this.mLocalDataSource = localDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param localDataSource  the device storage data source
     * @return the {@link Repository} instance
     */
    public static Repository getInstance(DataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository(localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(DataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    // Methods implemented by the local data source

    @Override
    public Flowable<List<Character>> getAllSuperheroes() {
        return mLocalDataSource.getAllSuperheroes();
    }

    @Override
    public Maybe<Character> getSuperhero(long heroId) {
        return mLocalDataSource.getSuperhero(heroId);
    }

    @Override
    public Completable insertSuperhero(Character superhero) {
        return mLocalDataSource.insertSuperhero(superhero);
    }

    @Override
    public Completable deleteSuperhero(long heroId) {
        return mLocalDataSource.deleteSuperhero(heroId);
    }

    @Override
    public Maybe<List<Comic>> getComicsByHeroId(long heroId) {
        return mLocalDataSource.getComicsByHeroId(heroId);
    }

    @Override
    public Completable insertComics(List<Comic> comics) {
        return mLocalDataSource.insertComics(comics);
    }

    @Override
    public Completable deleteComicsByHeroId(long heroId) {
        return mLocalDataSource.deleteComicsByHeroId(heroId);
    }

    // Methods implemented by the remote data source

    @Override
    public Single<CharacterResponse> getCharactersFromBackend(String timestamp, String publicKey, String hash) {
        return NetworkClient.getRetrofit().create(MarvelAPI.class).getCharacters(timestamp, publicKey, hash);
    }

    @Override
    public Single<CharacterResponse> getCharacterFromBackend(int id, String timestamp, String publicKey, String hash) {
        return NetworkClient.getRetrofit().create(MarvelAPI.class).getCharacter(id, timestamp, publicKey, hash);
    }

    @Override
    public Single<ComicsResponse> getComicsFromBackend(int characterId, String timestamp, String publicKey, String hash) {
        return NetworkClient.getRetrofit().create(MarvelAPI.class).getComics(characterId, timestamp, publicKey, hash);
    }

    @Override
    public Single<CharacterResponse> getCharactersPagedFromBackend(String timestamp, String publicKey, String hash, int pageSize, int skip) {
        return NetworkClient.getRetrofit().create(MarvelAPI.class).getCharactersPaged(timestamp, publicKey, hash, pageSize, skip);
    }

}
