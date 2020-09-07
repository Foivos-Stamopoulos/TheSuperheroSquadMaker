package com.fivos.thesuperherosquadmaker.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CharacterDao {

    @Query("SELECT * FROM character")
    List<Character> getAll();


    @Insert
    void insert(Character... superHeroes);

    @Delete
    void delete(Character superHero);

}
