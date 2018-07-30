package com.example.kingj.movieholic;


import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Popular_fragment extends Fragment {


    RecyclerView recyclerView;
    ProgressBar progressBar,more;
    MoviesAdapter adapter;
    NowPlayingMovies result;
    long page=1;
    String type="popular";
    Boolean isScrolling = false;
    String ID_K="id";
    int currentItems;
    int totalItems;
    long totalPages;
    int scrolledOutItems;

    List<Result> movies=new ArrayList<>();

    public Popular_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output= inflater.inflate(R.layout.fragment_popular_fragment, container, false);

        recyclerView=output.findViewById(R.id.popularRecyclerView);
        progressBar=output.findViewById(R.id.progressbar);
        more=output.findViewById(R.id.moreMovies);

        progressBar.setVisibility(View.VISIBLE);


        adapter=new MoviesAdapter(getContext(), movies, new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                Intent intent = new Intent(getContext(),Details_Activity.class);
                Result movieResult = movies.get(position);
                String backDrop=movieResult.getBackdropPath();
                long id = movieResult.getId();
                intent.putExtra(ID_K,id);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        Call<NowPlayingMovies> call=ApiClient.getService().getNowplaying(type,page);

        call.enqueue(new Callback<NowPlayingMovies>() {
            @Override
            public void onResponse(Call<NowPlayingMovies>  call, Response<NowPlayingMovies> response) {


                if(response.body()!=null)
                {
                    result=response.body();

                    progressBar.setVisibility(View.GONE);
                    for(int i=0;i<result.getResults().size();i++) {
                        movies.add(result.getResults().get(i));
                    }
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
        Call<NowPlayingMovies> call=ApiClient.getService().getNowplaying(type,page1);

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
