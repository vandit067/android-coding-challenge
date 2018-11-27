package com.stashinvest.stashchallenge.ui.screen.images;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.base.BasePresenter;
import com.stashinvest.stashchallenge.util.rx.AppRxSchedulers;

import androidx.annotation.NonNull;
import io.reactivex.disposables.CompositeDisposable;

public class ImagePresenter<V extends ImagesMvpView> extends BasePresenter<V>
        implements ImagesMvpPresenter<V> {


    public ImagePresenter(CompositeDisposable compositeDisposable, AppRxSchedulers appRxSchedulers,
                          GettyImageService gettyImageService) {
        super(compositeDisposable, appRxSchedulers, gettyImageService);
    }

    @Override
    public void loadData(@NonNull String searchText) {
        getCompositeDisposable().add(getGettyImageService().searchImages(searchText)
                .subscribeOn(getAppRxSchedulers().io())
                .observeOn(getAppRxSchedulers().androidThread())
                .doOnSubscribe(disposable -> getMvpView().showProgress())
                .subscribe(imageResponse -> {
                    getMvpView().hideProgress();
                    getMvpView().showData(imageResponse.getImages());
                }, e -> {
                    getMvpView().hideProgress();
                    getMvpView().showError(e.getMessage());
                }));
    }

}
