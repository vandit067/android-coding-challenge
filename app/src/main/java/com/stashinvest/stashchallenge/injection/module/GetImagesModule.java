package com.stashinvest.stashchallenge.injection.module;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.ui.contract.GetImagesContract;
import com.stashinvest.stashchallenge.ui.factory.GettyImageFactory;
import com.stashinvest.stashchallenge.ui.presenter.GetImagesPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Define MainFragment-specific dependencies here.
 */
@Module(subcomponents = GetImagesComponent.class)
public abstract class GetImagesModule {

    @Provides
    static GetImagesPresenter provideGetImagesPresenter(GetImagesContract.View view, GettyImageService gettyImageService, GettyImageFactory gettyImageFactory) {
        return new GetImagesPresenter(view, gettyImageService, gettyImageFactory);
    }
}
