package com.fivos.thesuperherosquadmaker.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ComicDao {

    @Query("SELECT * FROM comic")
    List<Comic> getAll();

    @Query("SELECT * FROM comic WHERE superheroId = :heroId")
    Flowable<Comic> getComicsByHeroId(long heroId);

    @Insert
    void insert(Comic comic);

    @Query("DELETE FROM comic WHERE superheroId = :heroId")
    void deleteByHeroId(long heroId);

}
