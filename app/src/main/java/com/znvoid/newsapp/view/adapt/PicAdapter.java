package com.znvoid.newsapp.view.adapt;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.Picture;
import com.znvoid.newsapp.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 图片recycleview 的adapter
 * Created by zn on 2017/4/10.
 */

public class PicAdapter extends BaseRecyclerViewAdapter<Picture> implements View.OnClickListener {
    private  DisplayImageOptions options;
    private List<Integer> heights =  new ArrayList<>();
    private Random random;


    public PicAdapter(Context ctx, List<Picture> list) {
        super(ctx, list);
        random=new Random();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.picfragment_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Picture item) {
        ImageView imageView = holder.getImageView(R.id.picfragm_img);
        ViewGroup.LayoutParams p= (FrameLayout.LayoutParams) imageView.getLayoutParams();
       int width=  mContext.getResources().getDisplayMetrics().widthPixels/3-20;
        p.width=width;
        p.height=width*2-random.nextInt(width/2);
        imageView.setLayoutParams(p);
        ImageLoader.getInstance().displayImage(item.getPicUrl(), imageView,options );
        imageView.setTag(item.getPicUrl());
        holder.setClickListener(R.id.picfragm_img,this);
    }


    //如果pos=-1则在最末端加载
    public void add(int pos, List<Picture> items) {
        if (pos==-1)
            pos=mData.size();
        mData.addAll(pos, items);
        notifyItemInserted(pos);
        notifyItemRangeChanged(pos,items.size());
    }

    @Override
    public void onClick(View v) {
       String url= (String) v.getTag();
        Presenter.getInstance().showImage(url);
    }
}
