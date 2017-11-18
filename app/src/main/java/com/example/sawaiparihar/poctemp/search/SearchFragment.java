package com.example.sawaiparihar.poctemp.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.sawaiparihar.poctemp.R;
import com.google.gson.JsonArray;

/**
 * Created by sawai.parihar on 18/11/17.
 */

public class SearchFragment extends Fragment implements SearchView, TextWatcher {
    private SearchPresenter mSearchPresenter;
    private SearchAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchPresenter = new SearchPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((EditText) view.findViewById(R.id.api_search_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mSearchPresenter != null && charSequence != null) {
                    mSearchPresenter.provideUserEnteredValueForApiSearch(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((EditText) view.findViewById(R.id.local_search_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mSearchPresenter != null && charSequence != null) {
                    mSearchPresenter.provideUserEnteredValueForLocalSearch(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.search_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new SearchAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {
        if (getView() != null) {
            getView().findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (getView() != null) {
            getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }

    @Override
    public void prepareAndShowList(JsonArray jsonArray) {
        if (mAdapter != null) {
            mAdapter.setJsonArray(jsonArray);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
