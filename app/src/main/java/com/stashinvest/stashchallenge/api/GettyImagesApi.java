package com.stashinvest.stashchallenge.api;

import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GettyImagesApi {

    @NonNull
    @GET("search/images")
    Single<ImageResponse> searchImages(@Query("phrase") String phrase,
                                           @Query("fields") String fields,
                                           @Query("sort_order") String sortOrder);

    @GET("images/{id}")
    Call<MetadataResponse> getImageMetadata(@Path("id") String id);

    @GET("images/{id}/similar")
    Call<ImageResponse> getSimilarImages(@Path("id") String id);
}
