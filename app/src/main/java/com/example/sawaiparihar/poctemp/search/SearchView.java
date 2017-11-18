package com.example.sawaiparihar.poctemp.search;

import com.google.gson.JsonArray;


/**
 * Created by sawai.parihar on 18/11/17.
 */

public interface SearchView {
    void showLoading();

    void hideLoading();

    void prepareAndShowList(JsonArray jsonArray);
}
