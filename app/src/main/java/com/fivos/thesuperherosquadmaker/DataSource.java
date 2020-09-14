package com.fivos.thesuperherosquadmaker;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.data.CharacterResponse;
import com.fivos.thesuperherosquadmaker.data.Comic;
import com.fivos.thesuperherosquadmaker.data.ComicsResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Access point for managing user data.
 */
public interface DataSource {

    /**
     * Gets the superheroes from the data source.
     *
     * @return the superheroes from the data source.
     */
    Flowable<List<Character>> getAllSuperheroes();

    /**
     * Gets the superhero from the data source.
     *
     * @return the superhero from the data source.
     */
    Maybe<Character> getSuperhero(long heroId);


    /**
     * Inserts the superhero into the data source
     *
     * @param superhero the user to be inserted
     */
    Completable insertSuperhero(Character superhero);

    /**
     * Deletes superhero from the data source.
     */
    Completable deleteSuperhero(long heroId);


    /**
     * Gets the comic from the data source.
     *
     * @return the comic from the data source.
     */
    Maybe<List<Comic>> getComicsByHeroId(long heroId);

    /**
     * Inserts the comic into the data source
     *
     * @param comics the comic to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertComics(List<Comic> comics);

    /**
     * Deletes comic/s from the data source.
     */
    Completable deleteComicsByHeroId(long heroId);


    Single<CharacterResponse> getCharactersFromBackend(String timestamp, String publicKey, String hash);

    Single<CharacterResponse> getCharacterFromBackend(int id, String timestamp, String publicKey,
                                                      String hash);

    Single<ComicsResponse> getComicsFromBackend(int characterId, String timestamp, String publicKey,
                                                String hash);

    Single<CharacterResponse> getCharactersPagedFromBackend(String timestamp, String publicKey,
                                                            String hash, int pageSize, int skip);
}
