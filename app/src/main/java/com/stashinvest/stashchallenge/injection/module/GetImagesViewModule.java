package com.stashinvest.stashchallenge.injection.module;

import com.stashinvest.stashchallenge.ui.contract.GetImagesContract;
import com.stashinvest.stashchallenge.ui.fragment.MainFragment;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class GetImagesViewModule {

    @Binds
    abstract GetImagesContract.View bindGetImagesContractView(MainFragment mainFragment);
}
