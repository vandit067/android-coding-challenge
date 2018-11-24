package com.stashinvest.stashchallenge.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.ui.contract.SimilarImagesContract;
import com.stashinvest.stashchallenge.ui.presenter.SimilarImagesPresenter;
import com.stashinvest.stashchallenge.util.UiUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PopUpDialogFragment extends DialogFragment implements SimilarImagesContract.View {

    @BindView(R.id.image_view)
    ImageView mIvSelectedImage;
    @BindView(R.id.similar_image_view1)
    ImageView mIvSimilarImage1;
    @BindView(R.id.similar_image_view2)
    ImageView mIvSimilarImage2;
    @BindView(R.id.similar_image_view3)
    ImageView mIvSimilarImage3;

    @BindView(R.id.title_view)
    TextView mTvTitle;

    @BindView(R.id.fragment_dialog_popup_progressbar)
    ProgressBar mProgressBar;
    @BindView((R.id.fragment_dialog_popup_ll_content_view))
    LinearLayout mLlContentView;
    @BindView(R.id.fragment_dialog_popup_rl_main_view)
    RelativeLayout mRlMainView;

    private SimilarImagesPresenter mSimilarImagesPresenter;

    private static String KEY_SELECTED_IMAGE_RESULT_OBJECT = "SelectedImageResultObject";


    public static PopUpDialogFragment newInstance(@NonNull ImageResult selectedImageresult) {
        Bundle bundle = new Bundle();
        PopUpDialogFragment popUpDialogFragment = new PopUpDialogFragment();
        bundle.putParcelable(KEY_SELECTED_IMAGE_RESULT_OBJECT, selectedImageresult);
        return new PopUpDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSimilarImagesPresenter = new SimilarImagesPresenter(this);
        if (getArguments() == null || getArguments().getParcelable(KEY_SELECTED_IMAGE_RESULT_OBJECT) == null) {
            dismiss();
            return;
        }
        this.mSimilarImagesPresenter.setSelectedImageResult(getArguments().getParcelable(KEY_SELECTED_IMAGE_RESULT_OBJECT));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_popup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Similar Images");
        // Initiate call to retrieve similar images
        this.mSimilarImagesPresenter.getSimilarImagesData();
    }

    @Override
    public void showProgress() {
        this.mProgressBar.setVisibility(View.VISIBLE);
        this.mLlContentView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        this.mProgressBar.setVisibility(View.GONE);
        this.mLlContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(@NonNull String message) {
        this.mProgressBar.setVisibility(View.GONE);
        this.mLlContentView.setVisibility(View.GONE);
        UiUtils.showSnackBar(this.mRlMainView, message, Snackbar.LENGTH_LONG);
    }

    @Override
    public void showData(@NonNull List<ImageResult> similarImagesList) {
        Picasso.with(getContext()).load(this.mSimilarImagesPresenter.getSelectedImageResult().getThumbUri()).into(this.mIvSelectedImage);
        this.mTvTitle.setText(this.mSimilarImagesPresenter.getSelectedImageResult().getTitle());
        if (similarImagesList.isEmpty()){
            return;
        }
        int i = 1;
        for (ImageResult imageResult: similarImagesList) {
            Picasso.with(getContext()).load(imageResult.getThumbUri()).into((i > 2) ? this.mIvSimilarImage3 : (i == 1) ? this.mIvSimilarImage1 : this.mIvSimilarImage2);
            i++;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mSimilarImagesPresenter.onDestroy();
        this.mSimilarImagesPresenter = null;
    }
}
