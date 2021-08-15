package com.example.lightsout;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String movies[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] movies) {
        this.context = context;
        this.movies = movies;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView movie = (TextView) view.findViewById(R.id.textView15);
        movie.setText(movies[i]);
        return view;
    }
}