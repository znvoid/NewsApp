package com.znvoid.newsapp.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.znvoid.newsapp.R;
import com.znvoid.newsapp.Utils.Util;
import com.znvoid.newsapp.bean.Channel;
import com.znvoid.newsapp.view.adapt.ChannelAdapter;

import java.util.List;

/**
 * Created by zn on 2017/4/15.
 */

public class TextActivity extends AppCompatActivity {
    private GridView mGridView_useer;
    private GridView mGridView_out;
    private List<Channel> channels_user;
    private List<Channel> channels_all;
    private ChannelAdapter channelAdapter_out;
    private ChannelAdapter channelAdapter_user;
    private List<Channel> outList;
    public boolean flag;
    private LinearLayout linerLayout;
    private TextView bjTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_channel);
        linerLayout = (LinearLayout) findViewById(R.id.news_channel_ly);
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        String spString = sp.getString("newChannels", "{}");
        channels_all =JSON.parseArray(spString,Channel.class);
        String spString1 = sp.getString("userNewsChannels", "{}");
        channels_user =JSON.parseArray(spString1,Channel.class);
        outList = Util.getOutList(channels_all, channels_user);

        init();
    }

    private void init() {
        mGridView_useer = (GridView) findViewById(R.id.news_channel_gv1);
        mGridView_out = (GridView) findViewById(R.id.news_channel_gv2);
        channelAdapter_out = new ChannelAdapter(outList, false);
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
                    TransitionManager.beginDelayedTransition(linerLayout);
                    Channel channel= (Channel) channelAdapter_user.getItem(position);
                    if (channelAdapter_user.getSeletPosition()==position)
                        channelAdapter_user.setSeletPosition(0);
                    channels_user.remove(position);
                    outList.add(channel);
                    channelAdapter_out.notifyDataSetChanged();
                    channelAdapter_user.notifyDataSetChanged();

                }

            }
        });
        bjTextView = (TextView) findViewById(R.id.news_channel_bj);
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
