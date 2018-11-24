package com.stashinvest.stashchallenge.injection.module;

import com.stashinvest.stashchallenge.util.rx.AppRxSchedulers;
import com.stashinvest.stashchallenge.util.rx.RxSchedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @Provides
    @Singleton
    RxSchedulers provideRxSchedulers() {
        return new AppRxSchedulers();
    }
}
