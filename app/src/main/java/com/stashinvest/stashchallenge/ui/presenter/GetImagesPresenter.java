package com.stashinvest.stashchallenge.ui.presenter;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.ui.contract.GetImagesContract;
import com.stashinvest.stashchallenge.ui.factory.GettyImageFactory;
import com.stashinvest.stashchallenge.ui.viewmodel.BaseViewModel;
import com.stashinvest.stashchallenge.ui.viewmodel.GettyImageViewModel;
import com.stashinvest.stashchallenge.util.rx.AppRxSchedulers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;

public class GetImagesPresenter extends BasePresenter<GetImagesContract.View> implements GetImagesContract.Presenter {

//    @Inject
    GettyImageService mGettyImageService;

//    @Inject
    GettyImageFactory mGettyImageFactory;

//    @Inject
//    ViewModelAdapter mAdapter;

    private final AppRxSchedulers appRxSchedulers;

    @Inject
    public GetImagesPresenter(GetImagesContract.View view) {
        super(view);
        appRxSchedulers = new AppRxSchedulers();
    }

   /* @Inject
    public GetImagesPresenter(GetImagesContract.View view, @NonNull GettyImageService gettyImageService, @NonNull GettyImageFactory mGettyImageFactory) {
        super(view);
        appRxSchedulers = new AppRxSchedulers();
        this.mGettyImageService = gettyImageService;
        this.mGettyImageFactory = mGettyImageFactory;
    }*/

//    public ViewModelAdapter getAdapter() {
//        return this.mAdapter;
//    }

    @Override
    public void loadData(@NonNull String searchText) {
        addDisposable(this.mGettyImageService.searchImages(searchText)
                .subscribeOn(appRxSchedulers.io())
                .observeOn(appRxSchedulers.androidThread())
                .doOnSubscribe(disposable -> view.showProgress())
                .subscribe(imageResponse -> {
                    view.hideProgress();
                    view.showData(imageResponse.getImages());
                }, e -> {
                    view.hideProgress();
                    view.showError(e.getMessage());
                }));
    }

    public List<BaseViewModel> updateImages(@NonNull List<ImageResult> images, GettyImageViewModel.Listener listener) {
        List<BaseViewModel> viewModels = new ArrayList<>();
        int i = 0;
        for (ImageResult imageResult : images) {
            viewModels.add(mGettyImageFactory.createGettyImageViewModel(i++, imageResult, listener));
        }
        return viewModels;
    }

    /*private void onImageLongPress(String id, String uri) {
        //todo - implement new feature
    }*/
}
