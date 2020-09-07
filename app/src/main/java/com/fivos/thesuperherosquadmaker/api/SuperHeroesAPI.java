package com.fivos.thesuperherosquadmaker.api;

import com.fivos.thesuperherosquadmaker.data.CharacterResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SuperHeroesAPI {

    @GET("/v1/public/characters")
    Single<CharacterResponse> getCharacters(
            @Query("ts") String timestamp,
            @Query("apikey") String publicKey,
            @Query("hash") String hash
    );

    @GET("/v1/public/characters/{id}")
    Single<CharacterResponse> getCharacter(
            @Path("id") int id,
            @Query("ts") String timestamp,
            @Query("apikey") String publicKey,
            @Query("hash") String hash
    );

}