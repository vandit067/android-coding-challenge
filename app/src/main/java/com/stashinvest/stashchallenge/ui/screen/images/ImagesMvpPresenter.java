package com.stashinvest.stashchallenge.ui.screen.images;

import com.stashinvest.stashchallenge.base.MvpPresenter;
import com.stashinvest.stashchallenge.injection.ForActivity;

import androidx.annotation.NonNull;

@ForActivity
public interface ImagesMvpPresenter<V extends ImagesMvpView> extends MvpPresenter<V> {
    void loadData(@NonNull String searchText);
}
