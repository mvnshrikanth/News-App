package com.android.sunny.newsapp;


/**
 * Created by Sunny on 1/13/2017.
 */

public class News {

    private String mSectionName;
    private String mWebPublicationDate;
    private String mWebURL;
    private String mWebTitle;
    private String mThumbnail;

    public News(String mSectionName, String mWebPublicationDate, String mWebURL, String mWebTitle, String mThumbnail) {
        this.mSectionName = mSectionName;
        this.mWebPublicationDate = mWebPublicationDate;
        this.mWebURL = mWebURL;
        this.mWebTitle = mWebTitle;
        this.mThumbnail = mThumbnail;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public void setmSectionName(String mSectionName) {
        this.mSectionName = mSectionName;
    }

    public String getmWebPublicationDate() {
        return mWebPublicationDate;
    }

    public void setmWebPublicationDate(String mWebPublicationDate) {
        this.mWebPublicationDate = mWebPublicationDate;
    }

    public String getmWebURL() {
        return mWebURL;
    }

    public void setmWebURL(String mWebURL) {
        this.mWebURL = mWebURL;
    }

    public String getmWebTitle() {
        return mWebTitle;
    }

    public void setmWebTitle(String mWebTitle) {
        this.mWebTitle = mWebTitle;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }
}
