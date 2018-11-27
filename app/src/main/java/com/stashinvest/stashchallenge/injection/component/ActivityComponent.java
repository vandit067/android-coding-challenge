package com.stashinvest.stashchallenge.injection.component;

import com.stashinvest.stashchallenge.injection.ForActivity;
import com.stashinvest.stashchallenge.injection.NetworkModule;
import com.stashinvest.stashchallenge.injection.module.ActivityModule;
import com.stashinvest.stashchallenge.ui.activity.MainActivity;
import com.stashinvest.stashchallenge.ui.fragment.MainFragment;

import dagger.Component;

@ForActivity
@Component(modules = {ActivityModule.class, NetworkModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(MainFragment mainFragment);
//    void inject(PopUpDialogFragment popUpDialogFragment);
}
