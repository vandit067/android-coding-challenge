package com.stashinvest.stashchallenge.base;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.util.rx.AppRxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V>{

    private V mMvpView;

    private final CompositeDisposable mCompositeDisposable;

    private final AppRxSchedulers mAppRxSchedulers;

    private final GettyImageService mGettyImageService;

    @Inject
    public BasePresenter(CompositeDisposable compositeDisposable, AppRxSchedulers appRxSchedulers, GettyImageService gettyImageService) {
        this.mCompositeDisposable = compositeDisposable;
        this.mAppRxSchedulers = appRxSchedulers;
        this.mGettyImageService = gettyImageService;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        this.mCompositeDisposable.clear();
        this.mMvpView = null;
    }

    public boolean isViewAttached(){
        return this.mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public AppRxSchedulers getAppRxSchedulers() {
        return mAppRxSchedulers;
    }

    public GettyImageService getGettyImageService() {
        return mGettyImageService;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
