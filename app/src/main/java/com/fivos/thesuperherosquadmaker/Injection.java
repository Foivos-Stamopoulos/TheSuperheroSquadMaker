package com.fivos.thesuperherosquadmaker;

import android.content.Context;

import com.fivos.thesuperherosquadmaker.data.LocalDataSource;
import com.fivos.thesuperherosquadmaker.data.MarvelDatabase;
import com.fivos.thesuperherosquadmaker.data.Repository;

public class Injection {

    public static DataSource provideLocalDataSource(Context context) {
        MarvelDatabase database = MarvelDatabase.getInstance(context);
        return new LocalDataSource(database.characterDao(), database.comicDao());
    }

    public static DataSource provideRepository(Context context) {
        return Repository.getInstance(provideLocalDataSource(context));
    }

}
