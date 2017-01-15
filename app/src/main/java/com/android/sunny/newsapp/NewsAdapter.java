package com.android.sunny.newsapp;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sunny on 1/13/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();
    private final LayoutInflater mInflater;
    Context context;

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ViewHolder viewHolder;

        if (v == null) {

            v = mInflater.inflate(R.layout.grid_item, null);
            viewHolder = new ViewHolder(v);

            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        News news = getItem(i);
        String imageUrl = news.getmThumbnail();

        if (imageUrl != null) {
            Picasso.with(context)
                    .load(imageUrl)
                    .into(viewHolder.pictureImageView);
        } else {
            viewHolder.pictureImageView.setImageResource(R.drawable.no_image_icon);
        }
        viewHolder.titleTextView.setText(news.getmWebTitle());


        String mWebPublicationDate = news.getmWebPublicationDate();
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(mWebPublicationDate);
            Date now = new Date(System.currentTimeMillis());
            long timeDiff = Math.abs(date.getTime() - now.getTime());
            int hours = (int) (timeDiff / (60 * 60 * 1000));
            if (hours < 24) {
                mWebPublicationDate = hours + " hours ago";
            } else {
                simpleDateFormat.applyPattern("mm/dd/yy");
                mWebPublicationDate = simpleDateFormat.format(date);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Date parsing error.", e);
        }
        String sectionName = "<font color=\"red\">" + news.getmSectionName() + "</font>";
        viewHolder.sectionTextView.setText(mWebPublicationDate + " | " + Html.fromHtml(sectionName));

        return v;
    }

    class ViewHolder {
        ImageView pictureImageView;
        TextView titleTextView;
        TextView sectionTextView;

        ViewHolder(View view) {
            pictureImageView = (ImageView) view.findViewById(R.id.picture);
            titleTextView = (TextView) view.findViewById(R.id.title_textView);
            sectionTextView = (TextView) view.findViewById(R.id.section_textView);
        }


    }

}
