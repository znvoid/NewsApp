package com.znvoid.newsapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.Channel;
import com.znvoid.newsapp.bean.Item;
import com.znvoid.newsapp.model.ApiWorkManager;
import com.znvoid.newsapp.model.NewsLoadLisenter;
import com.znvoid.newsapp.view.adapt.NewsPageAdapter;

import java.util.List;

/**
 * Created by zn on 2017/4/8.
 */

public class NewsPage extends Fragment implements SwipeRefreshLayout.OnRefreshListener,NewsLoadLisenter<Item> {
    Channel channel;

    RecyclerView mRecyclerview;

    SwipeRefreshLayout mSwiperefresh;

    NewsPageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id =  getArguments().getString("channelId");
        String name =  getArguments().getString("channelName");
        boolean flag =  getArguments().getBoolean("init");
        if (flag)
            getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    beginRefresh(true);
                }
            },1000);
       if (id!=null&&name!=null)
           channel=new Channel(id,name);
        if (channel==null)
            channel=new Channel("5572a109b3cdc86cf39001e6");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newsfragment_page, container,false);
        mRecyclerview= (RecyclerView) view.findViewById(R.id.newspage_recyclerview);
        mSwiperefresh= (SwipeRefreshLayout) view.findViewById(R.id.newspage_swiperefresh);
        adapter=new NewsPageAdapter(mRecyclerview,this);
        mSwiperefresh.setOnRefreshListener(this);
        return view;
    }



    private void init() {

//        adapter.setNews(Data.makeData());
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setAdapter(adapter);

    }


    @Override
    public void onRefresh() {
//        adapter.setNews(Data.makeData());
        ApiWorkManager.getInstance().getNews(this,channel.getChannelId());

    }




    @Override
    public void onComplete(List<Item> items) {
        mSwiperefresh.setRefreshing(false);
        if (adapter.getLoadMoreFlag()){
            adapter.setLoadMoreData(items);
        }else {
            adapter.setNews(items);
        }
        if (mRecyclerview.getAdapter()==null)
            init();
    }

    @Override
    public void onError() {
        mSwiperefresh.setRefreshing(false);
        if (adapter.getLoadMoreFlag())
            adapter.setLoadMoreData(null);
        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
    }

    public String getChannelId(){
        return  channel.getChannelId();
    }

    /**
     * 开始刷新
     * @param flag  第一次加载标准，为真实会检查是否是第一次刷新
     */
    public void beginRefresh(boolean flag){
        if (flag&&mRecyclerview.getAdapter()!=null){
           return;

        }else {

            mSwiperefresh.setRefreshing(true);
            onRefresh();
        }

    }
    public void beginLoadMore(){

        onRefresh();
    }

}
