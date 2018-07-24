package com.example.kingj.movieholic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    List<NowPlayingMovies> movies;
    Context context;

    MoviesAdapter(Context context,List<NowPlayingMovies> movies)
    {
        this.context=context;
        this.movies=movies;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.row_layout,parent,false);

        return new MoviesViewHolder(rowLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

        NowPlayingMovies nowPlayingMovies = movies.get(position);
        List<Result> result = nowPlayingMovies.getResults();
        Result movie = result.get(position);
        holder.title.setText(movie.getTitle());
        String imageUrl = movie.getPosterPath();

        Picasso.with(context).load(imageUrl).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
