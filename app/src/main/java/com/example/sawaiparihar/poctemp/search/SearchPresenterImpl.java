package com.example.sawaiparihar.poctemp.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by sawai.parihar on 18/11/17.
 */

public class SearchPresenterImpl implements SearchPresenter,
        SearchInteractor.OnSearchFinishedListener, SearchInteractor.OnSearchFilterFinishedListener {
    private SearchView mSearchView;
    private SearchInteractor mSearchInteractor;
    private PublishSubject mUserEnteredValueObservableApi;
    private PublishSubject mUserEnteredValueObservableLocal;

    public SearchPresenterImpl(SearchView searchView) {
        this.mSearchView = searchView;
        this.mSearchInteractor = new SearchInteractorImpl();

        prepareUserEnteredValuesObservableApi();
        prepareUserEnteredValuesObservableLocal();
    }

    private void prepareUserEnteredValuesObservableApi() {
        if (mUserEnteredValueObservableApi == null) {
            mUserEnteredValueObservableApi = PublishSubject.create();
            mUserEnteredValueObservableApi
                    .debounce(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String o) throws Exception {
                            fetchDataRelatedToSearchKeyword(o);
                        }
                    });
        }
    }

    private void prepareUserEnteredValuesObservableLocal() {
        if (mUserEnteredValueObservableLocal == null) {
            mUserEnteredValueObservableLocal = PublishSubject.create();
            mUserEnteredValueObservableLocal
                    .debounce(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String o) throws Exception {
                            filterDataRelatedToSearchKeyword(o);
                        }
                    });
        }
    }

    private void fetchDataRelatedToSearchKeyword(String string) {
        if (mSearchView != null) {
            mSearchView.showLoading();
        }

        if (mSearchInteractor != null) {
            mSearchInteractor.fetchSearchData(string, this);
        }
    }

    private void filterDataRelatedToSearchKeyword(String string) {
        if (mSearchView != null) {
            mSearchView.showLoading();
        }

        if (mSearchInteractor != null) {
            mSearchInteractor.filterSearchDataLocally(string, this);
        }
    }

    @Override
    public void provideUserEnteredValueForApiSearch(String enteredValue) {
        if (mUserEnteredValueObservableApi != null) {
            mUserEnteredValueObservableApi.onNext(enteredValue);
        }
    }

    @Override
    public void provideUserEnteredValueForLocalSearch(String enteredValue) {
        if (mUserEnteredValueObservableLocal != null) {
            mUserEnteredValueObservableLocal.onNext(enteredValue);
        }
    }

    @Override
    public void onFetchSearchResultSuccess(JsonObject jsonObject) {
        if (mSearchView != null) {
            mSearchView.hideLoading();
        }

        if (mSearchView != null) {
            mSearchView.prepareAndShowList(jsonObject.getAsJsonObject("photos").getAsJsonArray("photo"));
        }
    }

    @Override
    public void onFetchSearchResultFailure(String errorMsg) {
        if (mSearchView != null) {
            mSearchView.hideLoading();
        }
    }

    @Override
    public void onFilterSearchResultSuccess(JsonArray jsonArray) {
        if (mSearchView != null) {
            mSearchView.hideLoading();
        }

        if (mSearchView != null) {
            mSearchView.prepareAndShowList(jsonArray);
        }
    }

    @Override
    public void onFilterSearchResultFailure(String errorMsg) {
        if (mSearchView != null) {
            mSearchView.hideLoading();
        }
    }
}
