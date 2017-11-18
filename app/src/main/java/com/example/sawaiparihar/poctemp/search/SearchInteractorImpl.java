package com.example.sawaiparihar.poctemp.search;

import android.text.TextUtils;

import com.example.sawaiparihar.poctemp.network.NetworkClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import rx.functions.Func0;

/**
 * Created by sawai.parihar on 18/11/17.
 */

public class SearchInteractorImpl implements SearchInteractor {
    private JsonObject mDataObject;

    @Override
    public void fetchSearchData(String keyword, final OnSearchFinishedListener listener) {

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("tags", keyword);
        NetworkClient.getServices().getSearchResult(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject jsonObject) {
                        // store
                        mDataObject = jsonObject;

                        listener.onFetchSearchResultSuccess(jsonObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFetchSearchResultFailure("Some thing went wrong !");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void filterSearchDataLocally(String filteredText, final OnSearchFilterFinishedListener listener) {
        getFilteredDataLocally(filteredText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonArray>() {
                    @Override
                    public void accept(JsonArray jsonArray) throws Exception {
                        listener.onFilterSearchResultSuccess(jsonArray);
                    }
                });
    }

    private Observable<JsonArray> getFilteredDataLocally(final String filteredText) {
       return Observable.defer(new Func0<Observable<JsonArray>>() {
            @Override
            public Observable<JsonArray> call() {
                JsonArray returnValue = new JsonArray();

                JsonArray array = mDataObject.getAsJsonObject("photos").getAsJsonArray("photo");
                if (!TextUtils.isEmpty(filteredText) && mDataObject != null) {
                    for (int i = 0; i < array.size(); i++) {
                        String listText = array.get(i).getAsJsonObject().get("title").getAsString();
                        if (listText.toLowerCase().contains(filteredText.toLowerCase())) {
                            returnValue.add(array.get(i));
                        }
                    }
                }
                return Observable.just(returnValue);
            }
        });
    }
}
