package com.znvoid.newsapp.view.adapt;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.NoteBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zn on 2017/5/5.
 */

public class NoteAdapter extends BaseRecyclerViewAdapter<NoteBean> {

    public NoteAdapter(Context ctx, List<NoteBean> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.fragment_onte_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, final NoteBean item) {
        TextView content = holder.getTextView(R.id.note_content);
        TextView time = holder.getTextView(R.id.note_time);

        content.setText( Html.fromHtml(item.getContent()));
        Date date=new Date(item.getTime());
        SimpleDateFormat sdf=new SimpleDateFormat("MM月dd    HH:mm");
        time.setText(sdf.format(date));

    }



    public void setNoteDate(List<NoteBean> list){
        if (list==null)
            return;
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }
}
