package com.znvoid.newsapp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.ExpressData;
import com.znvoid.newsapp.bean.ExpressRespondBody;
import com.znvoid.newsapp.model.ApiWorkManager;
import com.znvoid.newsapp.model.LoadLisenter;
import com.znvoid.newsapp.view.adapt.BaseRecyclerViewAdapter;
import com.znvoid.newsapp.view.adapt.RecyclerViewHolder;
import com.znvoid.newsapp.view.widget.TimeEvent;

import java.util.List;

/**
 * Created by zn on 2017/4/12.
 */

public class ExpBottonSheetDialogFragment extends BottomSheetDialogFragment implements LoadLisenter<ExpressRespondBody> {
    private RecyclerView recyclerView;
    private String nu;
    private TextView tv;

    private Adapt adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.expshow,container,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.exp_recycleview);
        tv= (TextView) view.findViewById(R.id.exp_tv);
        adapter=new Adapt(getActivity(),null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        tv.setVisibility(View.VISIBLE);
        tv.setText("正在加载中。。");
        return view;
    }

   public void setNu(String nu){
       this.nu=nu;
       if (tv!=null){
           tv.setVisibility(View.VISIBLE);
           tv.setText("正在加载中。。");
       }
       ApiWorkManager.getInstance().getExpressData(nu,this);
   }

    @Override
    public void onComplete(ExpressRespondBody item) {
        List<ExpressData> data = item.getData();
        if (data!=null) {

            loadDateForRecycle(data);

        }


    }
    private Handler handler=new Handler();
    private void loadDateForRecycle(final List<ExpressData> data){
        if (getActivity()!=null&&recyclerView!=null){
            for (int i = data.size()-1; i >=0; i--) {
                TimeEvent d=new TimeEvent(data.get(i).getTime(),data.get(i).getContext());
                System.out.println(d.getEvent());
                adapter.add(0,d);
            }

            tv.setVisibility(View.GONE);
        }else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadDateForRecycle(data);
                }
            },500);

        }

    }

    @Override
    public void onError() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setText("加载失败");
            }
        },1000);
    }

   private class Adapt extends BaseRecyclerViewAdapter<TimeEvent>{

       public Adapt(Context ctx, List<TimeEvent> list) {
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

   }
}
