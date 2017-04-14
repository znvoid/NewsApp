package com.znvoid.newsapp.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.Channel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zn on 2017/4/8.
 */

public class NewsFragment extends Fragment {




    TabLayout tableLayout;
    ViewPager viewpage;

    List<Channel> chanels=new ArrayList<>();
    List<NewsPage> pages;
    private List<String> channelIds;
    private List<String> channelNames;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channelIds = getArguments().getStringArrayList("channelIds");
        channelNames = getArguments().getStringArrayList("channelNames");
        pages =new ArrayList<>(channelIds.size());
        if (channelNames !=null&& channelIds !=null){
            for (int i = 0; i < channelIds.size(); i++) {
                chanels.add(new Channel(channelIds.get(i), channelNames.get(i)));
                pages.add(i,null);
            }

        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        viewpage= (ViewPager) view.findViewById(R.id.viewpage);
//        pageTabStrip= (PagerTabStrip) view.findViewById(pageTabStrip);
        tableLayout= (TabLayout) view.findViewById(R.id.newsfragm_tb);
        if (chanels!=null)
            init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init() {

        viewpage.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                if (pages.get(position)==null) {
                    creatNewsPager(position);
                }
                return pages.get(position) ;
            }

            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return chanels.get(position).getName();
            }
        });

        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               mHander.removeCallbacks(mRunnable);
                mRunnable.setPosition(position);
                mHander.postDelayed(mRunnable,800);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tableLayout.setupWithViewPager(viewpage);
        viewpage.setCurrentItem(0);
    }
    private Handler mHander=new Handler(Looper.getMainLooper());
    private  MRunnable mRunnable=new MRunnable();
    private class MRunnable implements Runnable{

        private int position=0;
        @Override
        public void run() {
            pages.get(position).beginRefresh(true);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
    private void creatNewsPager(int position){

        NewsPage fragment = new NewsPage();
        Bundle bundle = new Bundle();
        bundle.putString("channelId", chanels.get(position).getChannelId());
        bundle.putString("channelName", chanels.get(position).getName());
        if (position==0)
            bundle.putBoolean("init", true);
        fragment.setArguments(bundle);
        pages.set(position,fragment);

    }
}
