package com.stashinvest.stashchallenge.api;

import android.content.Context;

import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;
import com.stashinvest.stashchallenge.util.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

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


    public Call<MetadataResponse> getImageMetadata(String id) {
        return api.getImageMetadata(id);
    }

    public Call<ImageResponse> getSimilarImages(String id) {
        return api.getSimilarImages(id);
    }

    Observable<Boolean> isNetworkAvailable(Context context) {
        return NetworkUtils.isNetworkAvailableObservable(context);
    }
}
