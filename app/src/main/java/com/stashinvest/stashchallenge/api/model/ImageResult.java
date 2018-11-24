package com.stashinvest.stashchallenge.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ImageResult implements Parcelable {
    private String id;
    private String title;
    @SerializedName("display_sizes")
    private List<DisplaySize> displaySizes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DisplaySize> getDisplaySizes() {
        return displaySizes;
    }

    public void setDisplaySizes(List<DisplaySize> displaySizes) {
        this.displaySizes = displaySizes;
    }

    public String getThumbUri() {
        if (displaySizes == null) {
            return null;
        }

        for (DisplaySize displaySize : displaySizes) {
            if ("thumb".equals(displaySize.getName())) {
                return displaySize.getUri();
            }
        }

        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeList(this.displaySizes);
    }

    public ImageResult() {
    }

    protected ImageResult(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.displaySizes = new ArrayList<>();
        in.readList(this.displaySizes, DisplaySize.class.getClassLoader());
    }

    public static final Parcelable.Creator<ImageResult> CREATOR = new Parcelable.Creator<ImageResult>() {
        @Override
        public ImageResult createFromParcel(Parcel source) {
            return new ImageResult(source);
        }

        @Override
        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}
