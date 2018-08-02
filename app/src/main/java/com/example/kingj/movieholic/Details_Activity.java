package com.example.kingj.movieholic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.kingj.movieholic.R.id.app_bar;

public class Details_Activity extends AppCompatActivity {

    Intent intent;
    String ID_K="Id";
    String director="";
    DetailPojo detailPojo;
    CreditsPojo creditsPojo;
    List<Crew> crewArrayList=new ArrayList<>();
    String baseurl = "http://image.tmdb.org/t/p/w780";
    ImageView imageView;
    View ab;
    CreditsPojo creditsResult;
    String overViewString;
    TextView overViewTv;
    TextView releaseDate;
    String title="";
    RecyclerView castRecyclerView;
    CastAdapter adapter;
    List<Cast> casts = new ArrayList<>();

    String date;

    ProgressBar backDropProgressBar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle("");
        setSupportActionBar(toolbar);
        setTitle("");
        imageView=findViewById(R.id.imageViewBack);
        ab=(View) findViewById(app_bar);
        backDropProgressBar=findViewById(R.id.backdropProgressBar);
        releaseDate=findViewById(R.id.releaseDate);
        overViewTv=findViewById(R.id.overView);

        castRecyclerView=findViewById(R.id.castRecyclerView);

        adapter=new CastAdapter(Details_Activity.this,casts);
        castRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        castRecyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        intent=getIntent();

        final long backDrop=intent.getLongExtra(ID_K,1);

        Call<DetailPojo> call=ApiClient.getService().getDetails(backDrop);

        call.enqueue(new Callback<DetailPojo>() {
            @Override
            public void onResponse(Call<DetailPojo>  call, Response<DetailPojo> response) {


                if(response.body()!=null)
                {
                    detailPojo=response.body();
                    String url = detailPojo.getBackdropPath();
                    Toast.makeText(Details_Activity.this,"phle wala",Toast.LENGTH_LONG).show();
                    title = detailPojo.getTitle();
                    date=detailPojo.getReleaseDate();
                    overViewString=detailPojo.getOverview();
                    releaseDate.setText(date);
                    overViewTv.setText(overViewString);
                    collapsingToolbarLayout.setTitle(title);
                    //setTitle(title);

                    backDropProgressBar.setVisibility(View.GONE);
                    Picasso.with(Details_Activity.this).load(baseurl + url).into(imageView);
//                    progressBar.setVisibility(View.GONE);
//                    for(int i=0;i<result.getResults().size();i++) {
//                        movies.add(result.getResults().get(i));
//                    }
//                    adapter.notifyDataSetChanged();
//                    totalPages=result.getTotalPages();
                }
                else
                    Toast.makeText(Details_Activity.this,"Not Result",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<DetailPojo> call, Throwable t) {

                Log.i("Error","= " + t.getMessage());

                Toast.makeText(Details_Activity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


        Call<CreditsPojo> call1=ApiClient.getService().getCredits(backDrop);
        Toast.makeText(Details_Activity.this,"dusra",Toast.LENGTH_LONG).show();
        call1.enqueue(new Callback<CreditsPojo>() {

            @Override
            public void onResponse(Call<CreditsPojo>  call1, Response<CreditsPojo> response1) {


                if(response1.body()!=null)
                {
                    creditsPojo=response1.body();
                    casts.addAll(creditsPojo.getCast());
                    adapter.notifyDataSetChanged();
                    crewArrayList = creditsPojo.getCrew();
                    Log.v("our body"," =res= "+response1.toString());

//                      progressBar.setVisibility(View.GONE);
                    for(int i=0;i<crewArrayList.size();i++) {
                        Crew crew = crewArrayList.get(i);
                        if(crew.getJob().equals("Director"))
                        {
                            director=crew.getName();
                            Toast.makeText(Details_Activity.this,"dkho = "+director+" ",Toast.LENGTH_LONG).show();
                        }
                    }

//                    totalPages=result.getTotalPages();
                }
                else
                    Toast.makeText(Details_Activity.this,"Not Result",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<CreditsPojo> call1, Throwable t) {

                Log.i("Error","= " + t.getMessage());

                Toast.makeText(Details_Activity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

//        setTitle(title);



    }


}
