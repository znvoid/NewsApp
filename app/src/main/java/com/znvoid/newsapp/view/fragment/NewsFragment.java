package com.znvoid.newsapp.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.znvoid.newsapp.R;
import com.znvoid.newsapp.Utils.Util;
import com.znvoid.newsapp.bean.Channel;
import com.znvoid.newsapp.presenter.Presenter;
import com.znvoid.newsapp.view.adapt.ChannelAdapter;

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

    private LinearLayout linerLayout;
    private List<Channel> channels_user;
    private List<Channel> outList;
    private View parentView;
    private GridView mGridView_useer;
    private GridView mGridView_out;
    private ChannelAdapter channelAdapter_out;
    private ChannelAdapter channelAdapter_user;
    private TextView bjTextView;
    private ImageView chooeImg;
    private FragmentStatePagerAdapter viewpagerAdapter;
    private LinearLayout chooeText;

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
        parentView = inflater.inflate(R.layout.fragment_news, null);
        viewpage= (ViewPager) parentView.findViewById(R.id.viewpage);
//        pageTabStrip= (PagerTabStrip) view.findViewById(pageTabStrip);
        tableLayout= (TabLayout) parentView.findViewById(R.id.newsfragm_tb);
        chooeImg= (ImageView) parentView.findViewById(R.id.fragment_news_channelimg);
        chooeText= (LinearLayout) parentView.findViewById(R.id.fragment_news_channelText);
        if (chanels!=null)
            init();
        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init() {

        viewpage.setAdapter(viewpagerAdapter=new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
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
        initChannelChoose();
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

    private void initChannelChoose() {
        linerLayout = (LinearLayout)parentView.findViewById(R.id.news_channel_ly);

        String spString =  Presenter.getInstance().getDataFromPerference("newChannels", "{}");
        List<Channel> channels_all = JSON.parseArray(spString, Channel.class);
        String spString1 = Presenter.getInstance().getDataFromPerference("userNewsChannels", "{}");
        channels_user =JSON.parseArray(spString1,Channel.class);
        if (channels_user!=null)
            chanels=channels_user;
        outList = Util.getOutList(channels_all, channels_user);
        channellayoutfindView();

    }

    private void channellayoutfindView() {
        mGridView_useer = (GridView)parentView. findViewById(R.id.news_channel_gv1);
        mGridView_out = (GridView) parentView.findViewById(R.id.news_channel_gv2);
        channelAdapter_out = new ChannelAdapter(outList,false);
        channelAdapter_user = new ChannelAdapter(channels_user,true);
        channelAdapter_user.setSeletPosition(0);
        mGridView_useer.setAdapter(channelAdapter_user);
        mGridView_out.setAdapter(channelAdapter_out);
        mGridView_out.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransitionManager.beginDelayedTransition(linerLayout);
                Channel channel= (Channel) channelAdapter_out.getItem(position);
                outList.remove(position);
                channels_user.add(channel);
                pages.add(null);
                viewpagerAdapter.notifyDataSetChanged();
                channelAdapter_user.setSeletPosition(channels_user.size()-1);
                channelAdapter_out.setSeletPosition(-1);
                channelAdapter_out.notifyDataSetChanged();
                channelAdapter_user.notifyDataSetChanged();

            }
        });

        mGridView_useer.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (channelAdapter_user.isImgFlag()){
                    if (position==0)
                        return;
                    TransitionManager.beginDelayedTransition(linerLayout);
                    Channel channel= (Channel) channelAdapter_user.getItem(position);
                    if (channelAdapter_user.getSeletPosition()==position)
                        channelAdapter_user.setSeletPosition(0);

                    if (channelAdapter_user.getSeletPosition()>position)
                        channelAdapter_user.setSeletPosition(channelAdapter_user.getSeletPosition()-1);
                    channels_user.remove(position);
                    pages.remove(position);
                    outList.add(channel);
                    channelAdapter_out.notifyDataSetChanged();
                    channelAdapter_user.notifyDataSetChanged();
                    viewpagerAdapter.notifyDataSetChanged();

                }else {
                    channelAdapter_user.setSeletPosition(position);
                    channelAdapter_user.notifyDataSetChanged();
                    chooeImg.performClick();
                }

            }
        });
        bjTextView = (TextView) parentView.findViewById(R.id.news_channel_bj);
        bjTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (channelAdapter_user.isImgFlag()){
                    channelAdapter_user.setImgFlag(false);
                    bjTextView.setText("编辑");
                }else {
                    channelAdapter_user.setImgFlag(true);
                    bjTextView.setText("确定");
                }


            }
        });
        chooeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linerLayout.getVisibility()==View.GONE){
                    chooeImg.setBackgroundResource(R.drawable.ic_tr_up);
                    chooeText.setVisibility(View.VISIBLE);
                    linerLayout.setVisibility(View.VISIBLE);
                    channelAdapter_user.setSeletPosition(viewpage.getCurrentItem());
                    channelAdapter_user.notifyDataSetChanged();
                }else {
                    chooeImg.setBackgroundResource(R.drawable.ic_tr_down);
                    chooeText.setVisibility(View.GONE);
                    linerLayout.setVisibility(View.GONE);
                    String s= JSONArray.toJSONString(chanels);
                    Presenter.getInstance().saveDataToPreference("userNewsChannels",s);
                    viewpage.setCurrentItem(channelAdapter_user.getSeletPosition());
                }
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (linerLayout.getVisibility()==View.VISIBLE){
            chooeImg.performClick();
            return true;
        }
        return false;
    }
}
