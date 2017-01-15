package com.android.sunny.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 1/13/2017.
 */

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String reqUrl) {
        URL url = createUrl(reqUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<News> news = extractDataFormJson(jsonResponse);
        return news;
    }


    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }


        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();


            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }


        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        }

        return jsonResponse;
    }

    private static URL createUrl(String reqUrl) {
        URL url = null;
        try {
            url = new URL(reqUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return stringBuilder.toString();
    }

    private static List<News> extractDataFormJson(String newsJsonResponse) {

        if (TextUtils.isEmpty(newsJsonResponse)) {
            return null;
        }

        List<News> news = new ArrayList<News>();


        try {
            JSONObject rootJsonObject = new JSONObject(newsJsonResponse);
            JSONObject responseJsonObject = rootJsonObject.getJSONObject("response");
            JSONArray resultsJsonArray = responseJsonObject.getJSONArray("results");

            for (int i = 0; i < resultsJsonArray.length(); i++) {

                JSONObject newsJsonObject = resultsJsonArray.getJSONObject(i);

                String mSectionName;
                String mWebPublicationDate;
                String mWebURL;
                String mWebTitle;
                String mThumbnail;


                if (newsJsonObject.toString().toLowerCase().contains("\"sectionname\":")) {
                    mSectionName = newsJsonObject.getString("sectionName");
                } else {
                    mSectionName = null;
                }

                if (newsJsonObject.toString().toLowerCase().contains("\"webpublicationdate\":")) {
                    mWebPublicationDate = newsJsonObject.getString("webPublicationDate").substring(0, 10) + " " +
                            newsJsonObject.getString("webPublicationDate").substring(11, 19);
                } else {
                    mWebPublicationDate = null;
                }

                if (newsJsonObject.toString().toLowerCase().contains("\"weburl\":")) {
                    mWebURL = newsJsonObject.getString("webUrl");
                } else {
                    mWebURL = null;
                }

                if (newsJsonObject.toString().toLowerCase().contains("\"webtitle\":")) {
                    mWebTitle = newsJsonObject.getString("webTitle");
                } else {
                    mWebTitle = null;
                }

                if (newsJsonObject.toString().toLowerCase().contains("\"fields\":")) {
                    JSONObject fieldJsonObject = newsJsonObject.getJSONObject("fields");
                    mThumbnail = fieldJsonObject.getString("thumbnail");
                } else {
                    mThumbnail = null;
                }

                news.add(new News(mSectionName, mWebPublicationDate, mWebURL, mWebTitle, mThumbnail));

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results.", e);
        }
        return news;
    }


}


