package com.example.kingj.movieholic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MoviesPageAdapter extends FragmentPagerAdapter {


    public MoviesPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0)
        {
            return new  NowPlayingFragment();
        }
        else if(position==1)
        {
            return new UpcomingFragment();
        }
        else if(position==2)
        {

        }
        else if(position==3)
        {

        }
        else if(position==4)
        {

        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
