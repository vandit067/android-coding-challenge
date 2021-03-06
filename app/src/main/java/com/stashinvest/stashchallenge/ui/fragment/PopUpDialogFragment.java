package com.stashinvest.stashchallenge.ui.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.stashinvest.stashchallenge.App;
import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageDetailModel;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.ui.contract.ImageDetailContract;
import com.stashinvest.stashchallenge.ui.presenter.ImageDetailPresenter;
import com.stashinvest.stashchallenge.util.UiUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PopUpDialogFragment extends DialogFragment implements ImageDetailContract.View {

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
    @BindView(R.id.fragment_dialog_tv_artist)
    TextView mTvArtist;

    @BindView(R.id.fragment_dialog_popup_progressbar)
    ProgressBar mProgressBar;
    @BindView((R.id.fragment_dialog_popup_ll_content_view))
    LinearLayout mLlContentView;
    @BindView(R.id.fragment_dialog_popup_rl_main_view)
    RelativeLayout mRlMainView;

    private ImageDetailPresenter mImageDetailPresenter;

    private static String KEY_SELECTED_IMAGE_ID = "SelectedImageId";
    private static String KEY_SELECTED_IMAGE_URI = "SelectedImageUri";

    private Context mContext;

    @Inject
    GettyImageService mGettyImageService;


    public static PopUpDialogFragment newInstance(@NonNull String selectedImageId, @NonNull String selectedImageUri) {
        Bundle bundle = new Bundle();
        PopUpDialogFragment popUpDialogFragment = new PopUpDialogFragment();
        bundle.putString(KEY_SELECTED_IMAGE_ID, selectedImageId);
        bundle.putString(KEY_SELECTED_IMAGE_URI, selectedImageUri);
        popUpDialogFragment.setArguments(bundle);
        return popUpDialogFragment;
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
        this.mImageDetailPresenter = new ImageDetailPresenter(this, this.mGettyImageService);
        if (getArguments() == null) {
            dismiss();
            return;
        }
        this.mImageDetailPresenter.setSelectedImageUri(getArguments().getString(KEY_SELECTED_IMAGE_URI));
        this.mImageDetailPresenter.setmSelectedImageId(getArguments().getString(KEY_SELECTED_IMAGE_ID));
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
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // Initiate call to retrieve similar images
        this.mImageDetailPresenter.fetchAndDisplayImageMetaDataWithSimilarImages(this.mContext);
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
    public void showData(@NonNull ImageDetailModel imageDetailModel) {
        this.hideProgress();
        Picasso.with(getContext()).load(imageDetailModel.getSelectedImageUri()).into(this.mIvSelectedImage);
        if(imageDetailModel.getMetadataResponse() != null && imageDetailModel.getMetadataResponse().getMetadata() != null
                && !imageDetailModel.getMetadataResponse().getMetadata().isEmpty()) {
            this.mTvTitle.setText(imageDetailModel.getMetadataResponse().getMetadata().get(0).getTitle());
            this.mTvArtist.setText(imageDetailModel.getMetadataResponse().getMetadata().get(0).getArtist());
        }
        if (imageDetailModel.getSimilarImages().isEmpty()){
            return;
        }
        int i = 1;
        for (ImageResult imageResult: imageDetailModel.getSimilarImages()) {
            Picasso.with(getContext()).load(imageResult.getThumbUri()).into((i > 2) ? this.mIvSimilarImage3 : (i == 1) ? this.mIvSimilarImage1 : this.mIvSimilarImage2);
            i++;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mImageDetailPresenter.onDestroy();
        this.mImageDetailPresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }
}
