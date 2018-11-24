package com.stashinvest.stashchallenge.ui.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageDetailModel;
import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;
import com.stashinvest.stashchallenge.ui.contract.SimilarImagesContract;
import com.stashinvest.stashchallenge.util.rx.AppRxSchedulers;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * Business logic related to {@link com.stashinvest.stashchallenge.ui.fragment.PopUpDialogFragment}
 */
public class SimilarImagesPresenter extends BasePresenter<SimilarImagesContract.View> implements SimilarImagesContract.Presenter {

    private AppRxSchedulers mAppRxSchedulers;

    private GettyImageService mGettyImageService;

    private  MetadataResponse mMetadataResponse;

    private String mSelectedImageUri;

    private String mSelectedImageId;

    public SimilarImagesPresenter(SimilarImagesContract.View view) {
        super(view);
        this.mAppRxSchedulers = new AppRxSchedulers();
        this.mGettyImageService = new GettyImageService();
    }

    public String getSelectedImageUri() {
        return this.mSelectedImageUri;
    }

    public void setSelectedImageUri(String selectedImageUri) {
        this.mSelectedImageUri = selectedImageUri;
    }

    public String getSelectedImageId() {
        return this.mSelectedImageId;
    }

    public void setmSelectedImageId(String selectedImageId) {
        this.mSelectedImageId = selectedImageId;
    }

    public MetadataResponse getMetadataResponse() {
        return this.mMetadataResponse;
    }

    public void setMetadataResponse(MetadataResponse metadataResponse) {
        this.mMetadataResponse = metadataResponse;
    }

    @Override
    public void getImageMetaDataWithSimilarImages(@NonNull Context context) {
        if(TextUtils.isEmpty(this.getSelectedImageId())){
            return;
        }
        addDisposable(this.mGettyImageService.getImageMetadata(this.getSelectedImageId())
                .doOnSubscribe(disposable -> view.showProgress())
                .subscribeOn(this.mAppRxSchedulers.io())
                .flatMap((Function<MetadataResponse, SingleSource<ImageResponse>>) metadataResponse -> {
                    setMetadataResponse(metadataResponse);
                    return mGettyImageService.getSimilarImages(getSelectedImageId());
                })
                .subscribeOn(this.mAppRxSchedulers.runOnBackground())
                .flatMap((Function<ImageResponse, SingleSource<ImageDetailModel>>)
                        imageResponse -> Single.just(new ImageDetailModel(getMetadataResponse(),
                                getSelectedImageUri(), imageResponse.getImages())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageDetailModel -> {
                    if(imageDetailModel == null){
                        view.showError(context.getString(R.string.message_no_data_available));
                        return;
                    }
                    view.showData(imageDetailModel);
                }, e -> view.showError(((HttpException) e).response().raw().message())));
//        addDisposable(this.mGettyImageService.getSimilarImages(this.getSelectedImageResult().getId())
//                .doOnSubscribe(disposable -> view.showProgress())
//                .subscribeOn(this.mAppRxSchedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(imageResponse -> view.showData(imageResponse.getImages()),
//                        e -> view.showError(((HttpException) e).response().raw().message())));
    }
}
