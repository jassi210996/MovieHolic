package com.example.kingj.movieholic.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kingj.movieholic.ApiClient;
import com.example.kingj.movieholic.MovieClickListener;
import com.example.kingj.movieholic.Movies.MoviesAdapter;
import com.example.kingj.movieholic.Pojo.NowPlayingMovies;
import com.example.kingj.movieholic.Pojo.Result;
import com.example.kingj.movieholic.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar,more;
    MoviesAdapter adapter;
    NowPlayingMovies result;
    long page=1;
    Boolean isScrolling = false;
    int currentItems;
    int totalItems;
    long totalPages;
    int scrolledOutItems;

    List<Result> movies=new ArrayList<>();


    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output= inflater.inflate(R.layout.fragment_upcoming, container, false);

        recyclerView=output.findViewById(R.id.upcomingRecycler);
        progressBar=output.findViewById(R.id.progressbar);
        more=output.findViewById(R.id.moreMovies);

        progressBar.setVisibility(View.VISIBLE);


        adapter=new MoviesAdapter(getContext(), movies, new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

//
//        String sPage = (String) page;

        Call<NowPlayingMovies> call= ApiClient.getService().getUpcoming(page);

        call.enqueue(new Callback<NowPlayingMovies>() {
            @Override
            public void onResponse(Call<NowPlayingMovies>  call, Response<NowPlayingMovies> response) {


                if(response.body()!=null)
                {
                    result=response.body();

                    progressBar.setVisibility(View.GONE);
                    movies.addAll(result.getResults());
                    adapter.notifyDataSetChanged();
                    totalPages=result.getTotalPages();
                }
                else
                    Toast.makeText(getContext(),"Not Result",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<NowPlayingMovies> call, Throwable t) {

                Log.i("Error","= " + t.getMessage());

                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems=layoutManager.getChildCount();
                totalItems=layoutManager.getItemCount();
                scrolledOutItems=layoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrolledOutItems==totalItems) && page<totalPages)
                {
                    page=page+1;
                    isScrolling=false;
                    more.setVisibility(View.VISIBLE);
                    fetchData(page);
                    //datafetch
                }

            }
        });


        return output;
    }

    void fetchData( long page1)
    {
        Call<NowPlayingMovies> call=ApiClient.getService().getUpcoming(page1);

        call.enqueue(new Callback<NowPlayingMovies>() {
            @Override
            public void onResponse(Call<NowPlayingMovies>  call, Response<NowPlayingMovies> response) {


                if(response.body()!=null)
                {
                    result=response.body();

                    more.setVisibility(View.GONE);
                    movies.addAll(result.getResults());
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(getContext(),"Not Result",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<NowPlayingMovies> call, Throwable t) {

                Log.i("Error","= " + t.getMessage());

                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

}

