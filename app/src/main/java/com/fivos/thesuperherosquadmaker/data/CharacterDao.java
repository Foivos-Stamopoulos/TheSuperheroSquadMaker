package com.fivos.thesuperherosquadmaker.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface CharacterDao {

    @Query("SELECT * FROM character ORDER BY name ASC")
    Flowable<List<Character>> getAll();

    @Query("SELECT * FROM character WHERE id = :heroId")
    Maybe<Character> getHeroById(long heroId);

    @Insert
    Completable insert(Character superHero);

    @Delete
    void delete(Character superHero);

    @Query("DELETE FROM character WHERE id = :heroId")
    Completable deleteByHeroId(long heroId);

}
