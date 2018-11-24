package com.stashinvest.stashchallenge.api.model;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Image details class with image uri, {@link MetadataResponse} and {@link ImageResult}
 */
public class ImageDetailModel {

    private MetadataResponse mMetadataResponse;
    private String selectedImageUri;
    private List<ImageResult> mSimilarImages;

    public ImageDetailModel(@NonNull MetadataResponse metadataResponse, @NonNull String selectedImageUri, @NonNull List<ImageResult> similarImages) {
        this.mMetadataResponse = metadataResponse;
        this.selectedImageUri = selectedImageUri;
        this.mSimilarImages = similarImages;
    }

    public MetadataResponse getMetadataResponse() {
        return mMetadataResponse;
    }

    public String getSelectedImageUri() {
        return selectedImageUri;
    }

    public List<ImageResult> getSimilarImages() {
        return mSimilarImages;
    }
}
