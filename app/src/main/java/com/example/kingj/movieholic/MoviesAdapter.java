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

    List<Result> movies;
    Context context;
    String url="http://image.tmdb.org/t/p/w780/";
    MovieClickListener listener;

    MoviesAdapter(Context context,List<Result> movies,MovieClickListener listener)
    {
        this.listener=listener;
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
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, int position) {

        Result nowPlayingMovies = movies.get(position);
//        Result result = nowPlayingMovies.getResults();
//        Result movie = result.get(position);
        holder.title.setText(nowPlayingMovies.getTitle());
        holder.ratings.setText(nowPlayingMovies.getVoteAverage()+"");
        String imageUrl =nowPlayingMovies.getPosterPath();
        if(imageUrl==null)
        {
            holder.poster.setImageResource(R.drawable.image_not_available);
        }
        else {
            Picasso.with(context).load(url + imageUrl).into(holder.poster);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieClicked(v,holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
