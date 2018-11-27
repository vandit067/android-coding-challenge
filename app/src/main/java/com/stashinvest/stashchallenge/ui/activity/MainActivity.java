package com.stashinvest.stashchallenge.ui.activity;

import android.os.Bundle;

import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.base.BaseActivity;
import com.stashinvest.stashchallenge.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        App.getInstance().getAppComponent().inject(this);
        getActivityComponent().inject(this);

        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, MainFragment.newInstance())
                .commit();
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
