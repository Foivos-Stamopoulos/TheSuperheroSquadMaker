package com.fivos.thesuperherosquadmaker;

import android.content.Context;

import com.fivos.thesuperherosquadmaker.data.LocalDataSource;
import com.fivos.thesuperherosquadmaker.data.MarvelDatabase;

public class Injection {

    public static DataSource provideDataSource(Context context) {
        MarvelDatabase database = MarvelDatabase.getInstance(context);
        return new LocalDataSource(database.characterDao(), database.comicDao());
    }

}
