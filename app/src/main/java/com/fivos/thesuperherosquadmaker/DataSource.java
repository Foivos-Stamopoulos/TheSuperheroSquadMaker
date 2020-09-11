package com.fivos.thesuperherosquadmaker;

import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.data.Comic;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Access point for managing user data.
 */
public interface DataSource {

    /**
     * Gets the superheroes from the data source.
     *
     * @return the superheroes from the data source.
     */
    Flowable<Character> getSuperheroes();

    /**
     * Gets the superhero from the data source.
     *
     * @return the superhero from the data source.
     */
    Flowable<Character> getSuperhero(long heroId);


    /**
     * Inserts the superhero into the data source
     *
     * @param superhero the user to be inserted
     */
    Completable insertSuperhero(Character superhero);

    /**
     * Deletes superhero from the data source.
     */
    void deleteSuperhero(long heroId);


    /**
     * Gets the comic from the data source.
     *
     * @return the comic from the data source.
     */
    Flowable<Comic> getComicsByHeroId(long heroId);

    /**
     * Inserts the comic into the data source
     *
     * @param comic the comic to be inserted
     */
    void insertComic(Comic comic);

    /**
     * Deletes comic/s from the data source.
     */
    void deleteComicsByHeroId(long heroId);

}
