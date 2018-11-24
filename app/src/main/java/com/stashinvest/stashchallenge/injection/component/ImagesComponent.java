package com.stashinvest.stashchallenge.injection.component;

import com.stashinvest.stashchallenge.injection.AppComponent;
import com.stashinvest.stashchallenge.injection.module.GetImagesModule;
import com.stashinvest.stashchallenge.ui.fragment.MainFragment;

import dagger.Component;

//@Component(modules = GetImagesModule.class, dependencies = AppComponent.class)
public interface ImagesComponent {
    void inject(MainFragment mainFragment);
}
