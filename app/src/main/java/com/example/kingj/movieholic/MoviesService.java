package com.example.kingj.movieholic;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MoviesService {

    @GET("movie/{type}?api_key=d883c71561d799acb1eb729418f054d6&language=en-US&page=?")
    Call<NowPlayingMovies>getNowplaying(@Path("type") String type,@Query("page") long page);

    @GET("movie/upcoming?api_key=d883c71561d799acb1eb729418f054d6&language=en-US&page=?")
    Call<NowPlayingMovies>getUpcoming(@Query("page") long page);

    @GET("movie/top_rated?api_key=d883c71561d799acb1eb729418f054d6&language=en-US&page=?")
    Call<NowPlayingMovies>getTopRated(@Query("page") long page);

    @GET("movie/latest?api_key=d883c71561d799acb1eb729418f054d6&language=en-US&page=?")
    Call<NowPlayingMovies> getLatest(@Query("page") long page);

    @GET("movie/{id}/credits?api_key=d883c71561d799acb1eb729418f054d6")
    Call<CreditsPojo>getCredits(@Path("id") long id);

    @GET("movie/{id}?api_key=d883c71561d799acb1eb729418f054d6&language=en-US")
    Call<DetailPojo>getDetails(@Path("id") long id);


}
