package com.example.kingj.movieholic.Movies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kingj.movieholic.Fragments.NowPlayingFragment;
import com.example.kingj.movieholic.Fragments.Popular_fragment;
import com.example.kingj.movieholic.Fragments.TopRatedFragment;
import com.example.kingj.movieholic.Fragments.UpcomingFragment;

public class MoviesPageAdapter extends FragmentPagerAdapter {


    public MoviesPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0)
        {
            return new NowPlayingFragment();
        }
        else if(position==1)
        {
            return new UpcomingFragment();
        }
        else if(position==2)
        {
            return new Popular_fragment();
        }
        else if(position==3)
        {
            return new TopRatedFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
