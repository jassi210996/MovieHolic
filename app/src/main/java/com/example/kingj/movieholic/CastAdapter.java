package com.example.kingj.movieholic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {

    List<Cast> castList;
    Context context;
    String url = "http://image.tmdb.org/t/p/w185/";

    CastAdapter(Context context,List<Cast>castList)
    {
        this.context=context;
        this.castList=castList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View castLayout =inflater.inflate(R.layout.castrecyclerlayout,parent,false);

        return new CastViewHolder(castLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {

        Cast castName = castList.get(position);
        String castImage = castName.getProfilePath();
        String castN = castName.getName();
        if(castImage==null)
        {
            holder.castImageView.setImageResource(R.drawable.image_not_available);
        }
        else {
            Picasso.with(context).load(url + castImage).into(holder.castImageView);
        }
        holder.castTextView.setText(castN);

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
