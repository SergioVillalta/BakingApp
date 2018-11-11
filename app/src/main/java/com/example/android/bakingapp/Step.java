package com.example.android.bakingapp;

import java.io.Serializable;
import java.net.URL;

public class Step implements Serializable {
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mThumbnailUrl;
    private String mVideoUrl;

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public void setThumbnailUrl(String mThumbnailUrl) {
        this.mThumbnailUrl = mThumbnailUrl;
    }

    public void setVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

}
