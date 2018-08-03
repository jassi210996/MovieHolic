package com.example.kingj.movieholic.Cast;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kingj.movieholic.R;

public class CastViewHolder extends RecyclerView.ViewHolder {

    TextView castTextView;
    ImageView castImageView;
    View itemView;

    public CastViewHolder(View itemView) {
        super(itemView);

        this.itemView=itemView;
        castTextView=itemView.findViewById(R.id.castTextView);
        castImageView=itemView.findViewById(R.id.castImageView);
    }
}
