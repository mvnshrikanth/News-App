package com.android.sunny.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridView);

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
    }
}
