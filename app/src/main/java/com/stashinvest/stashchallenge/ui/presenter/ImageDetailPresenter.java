package com.stashinvest.stashchallenge.ui.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageDetailModel;
import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;
import com.stashinvest.stashchallenge.ui.contract.ImageDetailContract;
import com.stashinvest.stashchallenge.util.rx.AppRxSchedulers;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * Business logic related to {@link com.stashinvest.stashchallenge.ui.fragment.PopUpDialogFragment}
 */
public class ImageDetailPresenter extends BasePresenter<ImageDetailContract.View> implements ImageDetailContract.Presenter {

    private AppRxSchedulers mAppRxSchedulers;

    private GettyImageService mGettyImageService;

    private  MetadataResponse mMetadataResponse;

    private String mSelectedImageUri;

    private String mSelectedImageId;

    public ImageDetailPresenter(ImageDetailContract.View view, GettyImageService gettyImageService) {
        super(view);
        this.mAppRxSchedulers = new AppRxSchedulers();
        this.mGettyImageService = gettyImageService;
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

    public void fetchAndDisplayImageMetaDataWithSimilarImages(@NonNull Context context) {
        if(TextUtils.isEmpty(this.getSelectedImageId())){
            return;
        }
        addDisposable(this.getImageMetaData(this.getSelectedImageId())
                .doOnSubscribe(disposable -> view.showProgress())
                .subscribeOn(this.mAppRxSchedulers.io())
                .flatMap((Function<MetadataResponse, SingleSource<ImageResponse>>) metadataResponse -> {
                    setMetadataResponse(metadataResponse);
                    return this.getSimilarImages(this.getSelectedImageId());
                })
                .subscribeOn(this.mAppRxSchedulers.runOnBackground())
                .flatMap((Function<ImageResponse, SingleSource<ImageDetailModel>>)
                        imageResponse -> this.getImageDetailModel(getMetadataResponse(), imageResponse,
                                getSelectedImageUri()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageDetailModel -> {
                    if(imageDetailModel == null){
                        view.showError(context.getString(R.string.message_no_data_available));
                        return;
                    }
                    view.showData(imageDetailModel);
                }, e -> view.showError(((HttpException) e).response().raw().message())));
    }

    @Override
    public Single<MetadataResponse> getImageMetaData(@NonNull String id){
        return this.mGettyImageService.getImageMetadata(id);
    }

    @Override
    public Single<ImageResponse> getSimilarImages(@NonNull String id){
        return mGettyImageService.getSimilarImages(id);
    }

    @Override
    public Single<ImageDetailModel> getImageDetailModel(@NonNull MetadataResponse metadataResponse,
                                                        @NonNull ImageResponse imageResponse,
                                                        @NonNull String imageUri){
        return Single.just(new ImageDetailModel(metadataResponse,
                imageUri, imageResponse.getImages()));
    }
}
