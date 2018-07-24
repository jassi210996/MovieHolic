package com.example.kingj.movieholic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {

    RecyclerView recyclerView;
    MoviesAdapter adapter;
    List<NowPlayingMovies> result = new ArrayList<>();
    List<NowPlayingMovies> movies=new ArrayList<>();


    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment





        View output = inflater.inflate(R.layout.fragment_now_playing, container, false);
        recyclerView=output.findViewById(R.id.recyclerView);

        adapter=new MoviesAdapter(NowPlayingFragment.super.getContext(),movies);
        recyclerView.setAdapter(adapter);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit.Builder builder = new Retrofit.Builder()
                                    .baseUrl("https://api.themoviedb.org/3/movie/")
                                    .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit =builder.build();

        MoviesService service = retrofit.create(MoviesService.class);

        Call<List<NowPlayingMovies>> call=service.getNowplaying();

        call.enqueue(new Callback<List<NowPlayingMovies>>() {
            @Override
            public void onResponse(Call<List<NowPlayingMovies>> call, Response<List<NowPlayingMovies>> response) {


                if(response.body()!=null)
                {
                    result=response.body();
                    for(int i=0;i<result.size();i++) {
                        movies.add(result.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(NowPlayingFragment.super.getContext(),"Not Result",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<NowPlayingMovies>> call, Throwable t) {

                Toast.makeText(NowPlayingFragment.super.getContext(),"Not Correct",Toast.LENGTH_LONG).show();

            }
        });

        return output;
    }

}
