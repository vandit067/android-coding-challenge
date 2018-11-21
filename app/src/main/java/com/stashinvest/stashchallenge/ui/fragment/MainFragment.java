package com.stashinvest.stashchallenge.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.stashinvest.stashchallenge.App;
import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.ui.contract.GetImagesContract;
import com.stashinvest.stashchallenge.ui.presenter.GetImagesPresenter;
import com.stashinvest.stashchallenge.util.SpaceItemDecoration;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class MainFragment extends Fragment implements GetImagesContract.View, TextView.OnEditorActionListener {

    @BindView(R.id.fragment_main_fl_main)
    FrameLayout mFlMainView;
    @BindView(R.id.search_phrase)
    EditText searchView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindDimen(R.dimen.image_space)
    int space;

    @Inject
    GetImagesPresenter mMainFragmentPresenter;

    Unbinder unbinder;

//    @Inject
//    GettyImageService mGettyImageService;
//
//    @Inject
//    GettyImageFactory mGettyImageFactory;
//
//    @Inject
//    ViewModelAdapter mAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AndroidInjection.inject(this);
        App.getInstance().getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        searchView.setOnEditorActionListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(mMainFragmentPresenter.getAdapter());
//        recyclerView.setAdapter(mAdapter);
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
    }

    @Override
    public void hideProgress() {
        this.mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showComplete() {

    }

    @Override
    public void showError(@NonNull String message) {
        this.hideProgress();
        Snackbar.make(this.mFlMainView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showData(@NonNull List<ImageResult> imagesList) {
        this.hideProgress();
        if(imagesList.isEmpty()){
            Snackbar.make(this.mFlMainView, getString(R.string.message_no_data_available), Snackbar.LENGTH_SHORT).show();
            return;
        }
        this.mMainFragmentPresenter.updateImages(imagesList);
//        updateImages(imagesList);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if(TextUtils.isEmpty(v.getText())){
                Snackbar.make(this.mFlMainView, getString(R.string.message_enter_search_text), Snackbar.LENGTH_SHORT).show();
                return false;
            }
//            loadData(v.getText().toString());
            mMainFragmentPresenter.loadData(v.getText().toString());
            return true;
        }
        return false;
    }

//    public void loadData(@NonNull String searchText) {
//       compositeDisposable.add(this.mGettyImageService.searchImages(searchText)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable -> showProgress())
//                .subscribe(imageResponse -> {
//                    hideProgress();
//                    showData(imageResponse.getImages());
//                }, e -> {
//                    hideProgress();
//                    showError(e.getMessage());
//                }));
//    }
//
//    public void updateImages(@NonNull List<ImageResult> images) {
//        List<BaseViewModel> viewModels = new ArrayList<>();
//        int i = 0;
//        for (ImageResult imageResult : images) {
//            viewModels.add(mGettyImageFactory.createGettyImageViewModel(i++, imageResult, this::onImageLongPress));
//        }
//        this.mAdapter.setViewModels(viewModels);
//    }
//
//    private void onImageLongPress(String id, String uri) {
//        //todo - implement new feature
//    }
}
