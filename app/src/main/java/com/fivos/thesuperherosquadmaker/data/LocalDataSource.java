package com.fivos.thesuperherosquadmaker.data;

import com.fivos.thesuperherosquadmaker.DataSource;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Using the Room database as a data source.
 */
public class LocalDataSource implements DataSource {

    private final CharacterDao mCharacterDao;
    private final ComicDao mComicDao;

    public LocalDataSource(CharacterDao characterDao, ComicDao comicDao) {
        mCharacterDao = characterDao;
        mComicDao = comicDao;
    }

    @Override
    public Flowable<Character> getSuperheroes() {
        return mCharacterDao.getAll();
    }

    @Override
    public Flowable<Character> getSuperhero(long heroId) {
        return mCharacterDao.getHeroById(heroId);
    }

    @Override
    public Completable insertSuperhero(Character superhero) {
        return mCharacterDao.insert(superhero);
    }

    @Override
    public void deleteSuperhero(long heroId) {
        mCharacterDao.deleteByHeroId(heroId);
    }

    @Override
    public Flowable<Comic> getComicsByHeroId(long heroId) {
        return mComicDao.getComicsByHeroId(heroId);
    }

    @Override
    public void insertComic(Comic comic) {
        mComicDao.insert(comic);
    }

    @Override
    public void deleteComicsByHeroId(long heroId) {
        mComicDao.deleteByHeroId(heroId);
    }

}
