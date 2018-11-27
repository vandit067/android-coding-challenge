package com.stashinvest.stashchallenge.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.stashinvest.stashchallenge.App;
import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.injection.component.ActivityComponent;
import com.stashinvest.stashchallenge.injection.module.ActivityModule;
import com.stashinvest.stashchallenge.util.NetworkUtils;
import com.stashinvest.stashchallenge.util.UiUtils;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements MvpView{

    private ActivityComponent mActivityComponent;

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(App.getInstance().getAppComponent())
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return this.mActivityComponent;
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    @Override
    public void showError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void showError(@StringRes int resId) {
        showError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkAvailable(getApplicationContext());
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if(view == null){
            return;
        }
        UiUtils.hideKeyBoard(view);
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract void setUp();
}
