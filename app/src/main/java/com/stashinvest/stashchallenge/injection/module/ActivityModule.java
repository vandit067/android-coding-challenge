package com.stashinvest.stashchallenge.injection.module;

import android.content.Context;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.GettyImagesApi;
import com.stashinvest.stashchallenge.injection.ActivityContext;
import com.stashinvest.stashchallenge.injection.ForActivity;
import com.stashinvest.stashchallenge.ui.contract.GetImagesContract;
import com.stashinvest.stashchallenge.ui.factory.GettyImageFactory;
import com.stashinvest.stashchallenge.ui.presenter.GetImagesPresenter;
import com.stashinvest.stashchallenge.ui.screen.images.ImagePresenter;
import com.stashinvest.stashchallenge.ui.screen.images.ImagesMvpView;
import com.stashinvest.stashchallenge.util.rx.AppRxSchedulers;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    AppRxSchedulers provideAppRxSchedulers(){
        return new AppRxSchedulers();
    }

    @Provides
    @ForActivity
    ImagePresenter<ImagesMvpView> provideImagePresenter(ImagePresenter<ImagesMvpView> imagePresenter){
        return imagePresenter;
    }

    /*@Provides
    @ForActivity
    GetImagesPresenter provideGetImagesPresenter(GetImagesPresenter getImagesPresenter){
        return getImagesPresenter;
    }

    @Provides
    GettyImageService provideGettyImageService(GettyImageService gettyImageService){
        return gettyImageService;
    }*/

    @Provides
    GettyImageFactory provideGettyImageFactory(){
        return new GettyImageFactory();
    }
}
