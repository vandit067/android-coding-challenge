package com.stashinvest.stashchallenge.ui.presenter;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.ui.adapter.ViewModelAdapter;
import com.stashinvest.stashchallenge.ui.contract.MainFragmentContract;
import com.stashinvest.stashchallenge.ui.factory.GettyImageFactory;
import com.stashinvest.stashchallenge.ui.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainFragmentPresenterImpl implements MainFragmentContract.Presenter {

    MainFragmentContract.View mView;

    @Inject
    GettyImageService mGettyImageService;

    @Inject
    GettyImageFactory mGettyImageFactory;

    @Inject
    ViewModelAdapter mAdapter;

    @Inject
    public MainFragmentPresenterImpl(MainFragmentContract.View mView) {
        this.mView = mView;
    }

    public ViewModelAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override
    public void loadData(@NonNull String searchText) {
        this.mView.showProgress();
        this.mGettyImageService.searchImages(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<ImageResult>>() {
                    @Override
                    public void onNext(List<ImageResult> imageResults) {
                        mView.showData(imageResults);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.showComplete();
                        mView.hideProgress();
                    }
                });
    }

    public void updateImages(@NonNull List<ImageResult> images) {
        List<BaseViewModel> viewModels = new ArrayList<>();
        int i = 0;
        for (ImageResult imageResult : images) {
            viewModels.add(mGettyImageFactory.createGettyImageViewModel(i++, imageResult, this::onImageLongPress));
        }
        this.mAdapter.setViewModels(viewModels);
    }

    private void onImageLongPress(String id, String uri) {
        //todo - implement new feature
    }
}
