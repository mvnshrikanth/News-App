package com.android.sunny.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter newsAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        gridView.setEmptyView(mEmptyStateTextView);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        gridView.setAdapter(newsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsAdapter.getItem(position);
                Uri newsUri = Uri.parse(news.getmWebURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(intent);
            }
        });

        if (networkAvailable()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            loadingIndicator.setVisibility(View.GONE);
        }
    }

    public boolean networkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        final String NEWSAPI_BASE_URL = "http://content.guardianapis.com/search?";
        final String API_KEY_PARAM = "api-key";
        final String FIELD_PARAM = "show-fields";

        Uri uri = Uri.parse(NEWSAPI_BASE_URL).buildUpon()
                .appendQueryParameter(FIELD_PARAM, "thumbnail")
                .appendQueryParameter(API_KEY_PARAM, getResources().getString(R.string.text_api_key))
                .build();

        //Log.v(LOG_TAG, uri.toString());

        return new NewsLoader(this, uri.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsData) {
        View loadIndicator = findViewById(R.id.loading_indicator);
        loadIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setVisibility(View.GONE);
        newsAdapter.clear();

        if (newsData != null && !newsData.isEmpty()) {
            newsAdapter.addAll(newsData);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }
}
