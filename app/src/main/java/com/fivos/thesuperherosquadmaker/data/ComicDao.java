package com.fivos.thesuperherosquadmaker.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface ComicDao {

    @Query("SELECT * FROM comic")
    List<Comic> getAll();

    @Query("SELECT * FROM comic WHERE superheroId = :heroId")
    Maybe<List<Comic>> getComicsByHeroId(long heroId);

    @Insert
    Completable insert(List<Comic> comics);

    @Query("DELETE FROM comic WHERE superheroId = :heroId")
    Completable deleteComicsByHeroId(long heroId);

}
