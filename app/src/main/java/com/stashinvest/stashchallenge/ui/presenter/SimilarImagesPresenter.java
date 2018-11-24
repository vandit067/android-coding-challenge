package com.stashinvest.stashchallenge.ui.presenter;

import android.text.TextUtils;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.ui.contract.SimilarImagesContract;
import com.stashinvest.stashchallenge.util.rx.AppRxSchedulers;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.HttpException;

/**
 * Business logic related to {@link com.stashinvest.stashchallenge.ui.fragment.PopUpDialogFragment}
 */
public class SimilarImagesPresenter extends BasePresenter<SimilarImagesContract.View> implements SimilarImagesContract.Presenter {

    private AppRxSchedulers mAppRxSchedulers;

    private GettyImageService mGettyImageService;

    // Selected image object
    private ImageResult mSelectedImageResult = new ImageResult();

    public SimilarImagesPresenter(SimilarImagesContract.View view) {
        super(view);
        this.mAppRxSchedulers = new AppRxSchedulers();
        this.mGettyImageService = new GettyImageService();
    }

    public ImageResult getSelectedImageResult() {
        return mSelectedImageResult;
    }

    public void setSelectedImageResult(@NonNull ImageResult selectedImageResult) {
        this.mSelectedImageResult = selectedImageResult;
    }

    @Override
    public void getSimilarImagesData() {
        if(TextUtils.isEmpty(this.getSelectedImageResult().getId())){
            return;
        }
        addDisposable(this.mGettyImageService.getSimilarImages(this.getSelectedImageResult().getId())
                .doOnSubscribe(disposable -> view.showProgress())
                .subscribeOn(this.mAppRxSchedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageResponse -> view.showData(imageResponse.getImages()),
                        e -> view.showError(((HttpException) e).response().raw().message())));
    }
}
