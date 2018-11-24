package com.stashinvest.stashchallenge.injection.module;

import dagger.Subcomponent;

@Subcomponent(modules = {GetImagesViewModule.class})
public interface GetImagesComponent {

    @Subcomponent.Builder
    interface Builder {
        Builder imagesModule(GetImagesViewModule getImagesViewModule);

        GetImagesComponent build();
    }
}
