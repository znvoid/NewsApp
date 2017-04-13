package com.znvoid.newsapp.view.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.Picture;
import com.znvoid.newsapp.model.ApiWorkManager;
import com.znvoid.newsapp.model.NewsLoadLisenter;
import com.znvoid.newsapp.view.adapt.PicAdapter;

import java.util.List;

/**
 * 图片浏览fragment
 * Created by zn on 2017/4/10.
 */

public class PictureFragemnt extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NewsLoadLisenter<Picture> {
    RecyclerView mRecyclerview;
    SwipeRefreshLayout mSwiperefresh;
    PicAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.newsfragment_page,container,false);
        mRecyclerview= (RecyclerView) view.findViewById(R.id.newspage_recyclerview);
        mSwiperefresh= (SwipeRefreshLayout) view.findViewById(R.id.newspage_swiperefresh);
        mSwiperefresh.setOnRefreshListener(this);
        onRefresh();
        mSwiperefresh.setRefreshing(true);
        return view;
    }

    @Override
    public void onRefresh() {

        ApiWorkManager.getInstance().getPicture(this);
    }


    @Override
    public void onComplete(List<Picture> items) {
        mSwiperefresh.setRefreshing(false);
        if (mRecyclerview.getAdapter()==null) {
            adapter = new PicAdapter(getActivity(), items);
            mRecyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    final int position=parent.getChildViewHolder(view).getAdapterPosition();
                    final int offset=parent.getResources().getDimensionPixelOffset(R.dimen.recyclerView_itemDecoration_offset);
                    outRect.set(offset,position==0?offset:0,offset,offset);

                }
            });
            mRecyclerview.setAdapter(adapter);
            StaggeredGridLayoutManager manager = new  StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
            manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            mRecyclerview.setLayoutManager(manager);


        }else {
            adapter.add(0,items);
        }


    }

    @Override
    public void onError() {
        mSwiperefresh.setRefreshing(false);
    }


}
