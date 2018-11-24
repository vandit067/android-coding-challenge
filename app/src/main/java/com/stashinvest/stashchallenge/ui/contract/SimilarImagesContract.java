package com.stashinvest.stashchallenge.ui.contract;

import android.content.Context;

import com.stashinvest.stashchallenge.api.model.ImageDetailModel;

import androidx.annotation.NonNull;

public interface SimilarImagesContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showError(@NonNull String message);

        void showData(@NonNull ImageDetailModel imageDetailModel);
    }

    interface Presenter {
        void getImageMetaDataWithSimilarImages(@NonNull Context context);
    }
}
