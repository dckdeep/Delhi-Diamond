package com.technotion.delhiDiamond.AllFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.technotion.delhiDiamond.R;

import java.util.ArrayList;

public class DelhiDiamondGame extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.owner_delhi_diamond_game_working, container, false);
        tabLayout=view.findViewById(R.id.tabLayout);
        viewPager= view.findViewById(R.id.view_pager);
        context = getContext();
        /*getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);*/
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        ViewPagerAdapter viewPagerAdapter = new DelhiDiamondGame.ViewPagerAdapter(getChildFragmentManager());
        /*   viewPagerAdapter.add(new Ajent_DashBoard(),"DashBoard");
         */
        viewPagerAdapter.add(new CoinsReport_delhi_diamond_game(),"Coins Report");
        viewPagerAdapter.add(new ResultReport_delhi_diamond_game(),"Result Report");
        //viewPagerAdapter.add(new OurStory(),"");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
     /*   submitPlayerInfo(view);
     */   return view;
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int i) {

            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void add(Fragment fragment, String title ){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
