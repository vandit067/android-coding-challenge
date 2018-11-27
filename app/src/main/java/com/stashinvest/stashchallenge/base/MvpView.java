package com.stashinvest.stashchallenge.base;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface MvpView {

    void showProgress();

    void hideProgress();

    void showError(@NonNull String message);

    void showError(@StringRes int resId);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
