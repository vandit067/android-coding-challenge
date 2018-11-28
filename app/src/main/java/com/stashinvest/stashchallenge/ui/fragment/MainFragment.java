package com.stashinvest.stashchallenge.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.stashinvest.stashchallenge.App;
import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.ui.activity.MainActivity;
import com.stashinvest.stashchallenge.ui.adapter.ViewModelAdapter;
import com.stashinvest.stashchallenge.ui.contract.GetImagesContract;
import com.stashinvest.stashchallenge.ui.factory.GettyImageFactory;
import com.stashinvest.stashchallenge.ui.presenter.GetImagesPresenter;
import com.stashinvest.stashchallenge.ui.viewmodel.BaseViewModel;
import com.stashinvest.stashchallenge.ui.viewmodel.GettyImageViewModel;
import com.stashinvest.stashchallenge.util.SpaceItemDecoration;
import com.stashinvest.stashchallenge.util.UiUtils;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment implements GetImagesContract.View, TextView.OnEditorActionListener,TextWatcher, GettyImageViewModel.Listener {

    @BindView(R.id.fragment_main_fl_main)
    FrameLayout mFlMainView;
    @BindView(R.id.search_phrase)
    EditText searchView;
    @BindView(R.id.fragment_main_til_search)
    TextInputLayout mTilSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindDimen(R.dimen.image_space)
    int space;

    //    @Inject
    GetImagesPresenter mMainFragmentPresenter;

    Unbinder unbinder;

    @Inject
    GettyImageService mGettyImageService;

    @Inject
    GettyImageFactory mGettyImageFactory;

    @Inject
    ViewModelAdapter mAdapter;

    private Context mContext;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);
        this.mMainFragmentPresenter = new GetImagesPresenter(this, this.mGettyImageService, this.mGettyImageFactory);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        searchView.setOnEditorActionListener(this);
        this.searchView.addTextChangedListener(this);
        UiUtils.showKeyBoard(this.searchView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(space, space, space, space));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProgress() {
        this.mProgressBar.setVisibility(View.VISIBLE);
        this.recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        this.mProgressBar.setVisibility(View.GONE);
        this.recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(@NonNull String message) {
        this.mProgressBar.setVisibility(View.GONE);
        this.recyclerView.setVisibility(View.GONE);
        Snackbar.make(this.mFlMainView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showData(@NonNull List<ImageResult> imagesList) {
        this.hideProgress();
        if (imagesList.isEmpty()) {
            Snackbar.make(this.mFlMainView, getString(R.string.message_no_data_available), Snackbar.LENGTH_SHORT).show();
            return;
        }
        List<BaseViewModel> viewModels = this.mMainFragmentPresenter.updateImages(imagesList, this);
        this.mAdapter.setViewModels(viewModels);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (TextUtils.isEmpty(v.getText())) {
                this.mTilSearch.setError(getString(R.string.message_enter_search_text));
                return false;
            }
            UiUtils.hideKeyBoard(v);
            mMainFragmentPresenter.loadData(v.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void onImageLongPress(String id, String uri) {
        FragmentManager fragmentManager = ((MainActivity)this.mContext).getSupportFragmentManager();
        PopUpDialogFragment popUpDialogFragment = PopUpDialogFragment.newInstance(id, uri);
        popUpDialogFragment.show(fragmentManager, PopUpDialogFragment.class.getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mMainFragmentPresenter.onDestroy();
        this.mMainFragmentPresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count == 0) {
            this.recyclerView.setVisibility(View.GONE);
            this.mProgressBar.setVisibility(View.GONE);
            return;
        }
        this.mTilSearch.setError("");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
