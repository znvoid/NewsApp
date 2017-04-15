package com.znvoid.newsapp.view.adapt;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zn on 2017/4/15.
 */

public class ChannelAdapter extends BaseAdapter {
    private List<Channel> channels;
    private int seletPosition=-1;
    private boolean imgFlag;
    private boolean userFlag;
    public ChannelAdapter(List<Channel> channels,boolean userFlag) {
        this.channels = channels!=null?channels:new ArrayList<Channel>();
        this.userFlag=userFlag;
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public Object getItem(int position) {
        return channels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=  LayoutInflater.from(parent.getContext()).inflate(R.layout.news_channel_item,parent,false);
            ViewHolder holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ViewHolder holder= (ViewHolder) convertView.getTag();
        holder.position=position;
        holder.textView.setText(channels.get(position).getName());
        if (position==seletPosition) {
            holder.textView.setSelected(true);
        }else {
            holder.textView.setSelected(false);
        }
        if (imgFlag&&position!=0){
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.imageView.setVisibility(View.GONE);
        }
        return convertView;
    }
    private class ViewHolder implements View.OnTouchListener {
        View iteView;
        TextView textView;
        ImageView imageView;
        int position;
        public ViewHolder(View iteView) {
            this.iteView = iteView;
            textView= (TextView) iteView.findViewById(R.id.news_channel_item_tv);
            imageView= (ImageView) iteView.findViewById(R.id.news_channel_item_img);
            iteView.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (!imgFlag) {
                setSeletPosition(position);
                notifyDataSetChanged();
                if (!userFlag) {
                    v.removeCallbacks(mRunnable);
                    v.postDelayed(mRunnable, 1000);
                }
            }
            return false;
        }
    }

    public int getSeletPosition() {
        return seletPosition;
    }

    public void setSeletPosition(int seletPosition) {
        this.seletPosition = seletPosition;
    }

    public void setImgFlag(boolean imgFlag) {
        this.imgFlag = imgFlag;
        notifyDataSetChanged();
    }

    public boolean isImgFlag() {
        return imgFlag;
    }

    private Runnable mRunnable =new Runnable() {
        @Override
        public void run() {
            if (seletPosition!=-1){
                setSeletPosition(-1);
                notifyDataSetChanged();
            }

        }
    };
}
