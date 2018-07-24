package com.example.kingj.movieholic;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface MoviesService {

    @GET("now_playing?api_key=d883c71561d799acb1eb729418f054d6&language=en-US")
    Call<List<NowPlayingMovies>> getNowplaying();


}
