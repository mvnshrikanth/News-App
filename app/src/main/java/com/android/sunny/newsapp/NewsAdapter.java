package com.android.sunny.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sunny on 1/13/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    private final LayoutInflater mInflater;

    public NewsAdapter(Context context, List<News> news) {

        super(context, 0, news);
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ViewHolder viewHolder;

        if (v == null) {

            v = mInflater.inflate(R.layout.grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.pictureImageView = (ImageView) v.findViewById(R.id.picture);
            viewHolder.nameTextView = (TextView) v.findViewById(R.id.text);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }


//        viewHolder.pictureImageView.setImageResource(item.drawableId);
//        viewHolder.nameTextView.setText(item.name);

        return v;
    }

    class ViewHolder {
        ImageView pictureImageView;
        TextView nameTextView;
    }

}
