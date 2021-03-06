package com.stashinvest.stashchallenge.api;

import android.content.Context;

import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;
import com.stashinvest.stashchallenge.util.NetworkUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Single;

public class GettyImageService {
    private static final String FIELDS = "id,title,thumb";
    private static final String SORT_ORDER = "best";

    @Inject
    GettyImagesApi api;

    @Inject
    public GettyImageService() {
    }

    @NonNull
    public Single<ImageResponse> searchImages(String phrase) {
        return api.searchImages(phrase, FIELDS, SORT_ORDER);
    }


    public Single<MetadataResponse> getImageMetadata(String id) {
        return api.getImageMetadata(id);
    }

    @NonNull
    public Single<ImageResponse> getSimilarImages(String id) {
        return api.getSimilarImages(id);
    }

    Observable<Boolean> isNetworkAvailable(Context context) {
        return NetworkUtils.isNetworkAvailableObservable(context);
    }
}
