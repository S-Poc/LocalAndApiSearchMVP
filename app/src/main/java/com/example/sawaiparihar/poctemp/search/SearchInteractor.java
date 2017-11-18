package com.example.sawaiparihar.poctemp.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by sawai.parihar on 18/11/17.
 */

public interface SearchInteractor {
    interface OnSearchFinishedListener {
        void onFetchSearchResultSuccess(JsonObject jsonObject);
        void onFetchSearchResultFailure(String errorMsg);
    }
    void fetchSearchData(String keyword, OnSearchFinishedListener listener);

    interface OnSearchFilterFinishedListener {
        void onFilterSearchResultSuccess(JsonArray jsonArray);
        void onFilterSearchResultFailure(String errorMsg);
    }
    void filterSearchDataLocally(String filteredText, OnSearchFilterFinishedListener listener);

}
