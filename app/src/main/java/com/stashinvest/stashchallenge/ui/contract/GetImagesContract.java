package com.stashinvest.stashchallenge.ui.contract;

import com.stashinvest.stashchallenge.api.model.ImageResult;

import java.util.List;

import androidx.annotation.NonNull;

public interface GetImagesContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showComplete();

        void showError(@NonNull String message);

        void showData(@NonNull List<ImageResult> imagesList);
    }

    interface Presenter {
        void loadData(@NonNull String searchText);
    }
}
