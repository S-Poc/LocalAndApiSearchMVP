package com.example.sawaiparihar.poctemp.network;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by sawai.parihar on 18/11/17.
 */

public interface ApiService {
    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=83a0a54bb953d2cb277c8a13bfbd18db")
    Observable<JsonObject> getSearchResult(@QueryMap Map<String, String> params);
}
