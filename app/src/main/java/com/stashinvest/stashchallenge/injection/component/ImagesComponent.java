package com.stashinvest.stashchallenge.injection.component;

import com.stashinvest.stashchallenge.ui.fragment.MainFragment;

//@Component(modules = GetImagesModule.class, dependencies = AppComponent.class)
public interface ImagesComponent {
    void inject(MainFragment mainFragment);
}
