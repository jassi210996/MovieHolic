package com.example.kingj.movieholic.Movies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kingj.movieholic.R;

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    ImageView poster;
    View itemView;
    TextView ratings;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        ratings=itemView.findViewById(R.id.rating);
        title=itemView.findViewById(R.id.textView);
        poster=itemView.findViewById(R.id.imageView);

    }
}
