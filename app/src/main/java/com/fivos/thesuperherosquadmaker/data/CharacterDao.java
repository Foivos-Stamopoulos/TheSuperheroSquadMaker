package com.fivos.thesuperherosquadmaker.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface CharacterDao {

    @Query("SELECT * FROM character")
    Flowable<Character> getAll();

    @Query("SELECT * FROM character WHERE id = :heroId")
    Flowable<Character> getHeroById(long heroId);

    @Insert
    Completable insert(Character superHero);

    @Delete
    void delete(Character superHero);

    @Query("DELETE FROM character WHERE id = :heroId")
    void deleteByHeroId(long heroId);

}
