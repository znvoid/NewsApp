package com.znvoid.newsapp.view.adapt;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.znvoid.newsapp.R;
import com.znvoid.newsapp.view.widget.TimeEvent;

import java.util.List;

/**
 * Created by zn on 2017/4/13.
 */

public class ExpAdapter extends BaseRecyclerViewAdapter<TimeEvent>{

    public ExpAdapter(Context ctx, List<TimeEvent> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.exp_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, TimeEvent item) {
        TextView dot = holder.getTextView(R.id.tvDot);
        TextView topline = holder.getTextView(R.id.tvTopLine);
        TextView event = holder.getTextView(R.id.tvAcceptStation);
        TextView time = holder.getTextView(R.id.tvAcceptTime);

        if (position==0){
            topline.setVisibility(View.INVISIBLE);
            dot.setBackgroundResource(R.drawable.timelline_dot_first);
            time.setTextColor(0xff00ff55);
            event.setTextColor(0xff00ff55);
        }else {
            topline.setVisibility(View.VISIBLE);
            dot.setBackgroundResource(R.drawable.timelline_dot_normal);
            time.setTextColor(0xff999999);
            event.setTextColor(0xff999999);
        }
        event.setText(item.getEvent());
        time.setText(item.getTime());
    }
    private void setData(List<TimeEvent> timeEvents){
        if (mData==null)
            return;
        mData.clear();
        mData.addAll(timeEvents);
    }
}
