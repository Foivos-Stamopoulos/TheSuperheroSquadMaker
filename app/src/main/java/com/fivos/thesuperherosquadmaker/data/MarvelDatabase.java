package com.fivos.thesuperherosquadmaker.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * The Room database that contains the Character and Comic tables
 */
@androidx.room.Database(entities = {Character.class, Comic.class}, version = 1)
public abstract class MarvelDatabase extends RoomDatabase {

    private static volatile MarvelDatabase INSTANCE;

    public abstract CharacterDao characterDao();
    public abstract ComicDao comicDao();

    public static MarvelDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MarvelDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MarvelDatabase.class, "Marvel.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
