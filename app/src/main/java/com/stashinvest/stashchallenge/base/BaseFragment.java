package com.stashinvest.stashchallenge.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.stashinvest.stashchallenge.injection.component.ActivityComponent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements MvpView {

    private BaseActivity mActivity;
    private Unbinder mUnbinder;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(@NonNull String message) {
        if (this.mActivity != null) {
            mActivity.showError(message);
        }
    }

    @Override
    public void showError(int resId) {
        if (this.mActivity != null) {
            mActivity.showError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (this.mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(int resId) {
        if (this.mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (this.mActivity != null) {
            mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void hideKeyboard() {
        if (this.mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    protected abstract void setUp(View view);

    public void setUnBinder(Unbinder unBinder) {
        this.mUnbinder = unBinder;
    }

    public BaseActivity getBaseActivity() {
        return this.mActivity;
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    @Override
    public void onDestroy() {
        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
