package com.stashinvest.stashchallenge.ui.screen.images;

import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.base.MvpView;

import java.util.List;

import androidx.annotation.NonNull;

public interface ImagesMvpView extends MvpView {

    void showData(@NonNull List<ImageResult> imagesList);

    void openPopUpDialogFragment();
}
