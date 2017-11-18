package com.example.sawaiparihar.poctemp.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.sawaiparihar.poctemp.R;

/**
 * Created by sawai.parihar on 18/11/17.
 */

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new SearchFragment())
                .commit();
    }
}
