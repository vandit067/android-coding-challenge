package com.stashinvest.stashchallenge.ui.contract;

import com.stashinvest.stashchallenge.api.model.ImageDetailModel;
import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public interface ImageDetailContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showError(@NonNull String message);

        void showData(@NonNull ImageDetailModel imageDetailModel);
    }

    interface Presenter {

        Single<MetadataResponse> getImageMetaData(@NonNull String id);

        Single<ImageResponse> getSimilarImages(@NonNull String id);

        Single<ImageDetailModel> getImageDetailModel(@NonNull MetadataResponse metadataResponse,
                                                     @NonNull ImageResponse imageResponse,
                                                     @NonNull String imageUri);
    }
}
