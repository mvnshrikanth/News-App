package com.android.sunny.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Sunny on 1/13/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private static final String LOG_TAG = NewsLoader.class.getSimpleName();
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
