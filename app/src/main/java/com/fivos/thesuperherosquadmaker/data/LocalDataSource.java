package com.fivos.thesuperherosquadmaker.data;

import com.fivos.thesuperherosquadmaker.DataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

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
    public Flowable<List<Character>> getAllSuperheroes() {
        return mCharacterDao.getAll();
    }

    @Override
    public Maybe<Character> getSuperhero(long heroId) {
        return mCharacterDao.getHeroById(heroId);
    }

    @Override
    public Completable insertSuperhero(Character superhero) {
        superhero.setThumbnailUrl(getThumbnailUrl(superhero));
        return mCharacterDao.insert(superhero);
    }

    @Override
    public Completable deleteSuperhero(long heroId) {
        return mCharacterDao.deleteByHeroId(heroId);
    }

    @Override
    public Maybe<List<Comic>> getComicsByHeroId(long heroId) {
        return mComicDao.getComicsByHeroId(heroId);
    }

    @Override
    public Completable insertComics(List<Comic> comics) {
        setComicsThumbnailUrl(comics);
        return mComicDao.insert(comics);
    }

    @Override
    public Completable deleteComicsByHeroId(long heroId) {
        return mComicDao.deleteComicsByHeroId(heroId);
    }

    private void setComicsThumbnailUrl(List<Comic> comics) {
        if (comics != null) {
            for (Comic item : comics) {
                if (item.getThumbnail() != null) {
                    item.setThumbnailUrl(item.getThumbnail().getUrl());
                }
            }
        }
    }

    private String getThumbnailUrl(Character superhero) {
        if (superhero != null && superhero.getThumbnail() != null) {
            return superhero.getThumbnail().getUrl();
        }
        return null;
    }

}
