package com.stashinvest.stashchallenge.ui.contract;

import com.stashinvest.stashchallenge.api.model.ImageResult;

import java.util.List;

import androidx.annotation.NonNull;

public interface SimilarImagesContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showError(@NonNull String message);

        void showData(@NonNull List<ImageResult> similarImagesList);
    }

    interface Presenter {
        void getSimilarImagesData();
    }
}
