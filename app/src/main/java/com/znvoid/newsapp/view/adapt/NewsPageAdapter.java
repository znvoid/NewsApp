package com.znvoid.newsapp.view.adapt;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.Img;
import com.znvoid.newsapp.bean.Item;
import com.znvoid.newsapp.presenter.Presenter;
import com.znvoid.newsapp.view.fragment.NewsPage;

import java.util.ArrayList;
import java.util.List;

/**
 * newspage 中recycleview 的adapter
 * Created by zn on 2017/4/8.
 */

public class NewsPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private NewsPage newsPage;
    List<Item> news;
    private int mRefreshPosition = -1;//上次加载的位置
    private static final int VIEWTYPE_IMGS = 0;//多图新闻
    private static final int VIEWTYPE_NOIMG = 1;//图片少于3的新闻
    private static final int VIEWTYPE_BOUNDARY = 3;//刷新界限
    private static final int VIEWTYPE_FOOT = 4;//刷新界限
    private RecyclerView recyclerView;
    private boolean flag = false;//是否有数据
    private boolean loadMoreFlag = false;
    private FootViewHold mFootViewHold;
    private List<String> mFootViewList;

    public NewsPageAdapter(RecyclerView recyclerView, NewsPage newsPage) {
        this.newsPage = newsPage;
        this.recyclerView = recyclerView;
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                final int position = parent.getChildViewHolder(view).getAdapterPosition();
                final int offset = parent.getResources().getDimensionPixelOffset(R.dimen.recyclerView_itemDecoration_offset);
                outRect.set(offset, position == 0 || getItemViewType(position) == VIEWTYPE_BOUNDARY ? offset : 0, offset, offset);


            }
        });
        news = new ArrayList<>();
        mFootViewList = new ArrayList<>(1);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && news.size() > 0) {
                    //得到当前显示的最后一个item的view
                    View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                    //得到lastChildView的bottom坐标值
                    int lastChildBottom = lastChildView.getBottom();
                    //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                    int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                    //通过这个lastChildView得到这个view当前的position值
                    int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
                    if (lastChildBottom <= recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1 && !loadMoreFlag) {
//
                        mFootViewList.add("add");
                        mFootViewHold.textView.setText("正在加载更多...");
                        notifyItemInserted(news.size());
                        recyclerView.scrollToPosition(news.size());
                        loadMoreFlag = true;

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                NewsPageAdapter.this.newsPage.beginLoadMore();
                            }
                        }, 1000);

                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (mFootViewHold == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfragment_item_footview, parent, false);
            mFootViewHold = new FootViewHold(view);
        }
        if (viewType == VIEWTYPE_NOIMG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfragment_item1, parent, false);
            return new ViewHold1(view);
        } else if (viewType == VIEWTYPE_FOOT) {

            return mFootViewHold;
        } else if (viewType == VIEWTYPE_IMGS) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfragment_item2, parent, false);
            return new ViewHold2(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsfragment_item3, parent, false);
            return new ViewHold3(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (mRefreshPosition==position)
//            return;
        if (holder instanceof ViewHold1) {
            Item item = news.get(position);
            ViewHold1 viewHold1 = (ViewHold1) holder;
            viewHold1.title.setText(item.getTitle());
            viewHold1.desc.setText(item.getDesc());
            viewHold1.souce.setText(item.getSource());
            viewHold1.date.setText(item.getPubDate());
            Img[] img = item.getImageurls();
            if (img != null && img.length > 0) {
                viewHold1.img.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(item.getImageurls()[0].getUrl(), viewHold1.img);
            } else {
                viewHold1.img.setVisibility(View.GONE);
            }


        } else if (holder instanceof ViewHold2) {
            Item item = news.get(position);
            ViewHold2 viewHold2 = (ViewHold2) holder;
            viewHold2.title.setText(item.getTitle());
            viewHold2.desc.setText(item.getDesc());
            viewHold2.souce.setText(item.getSource());
            viewHold2.date.setText(item.getPubDate());
            Img[] img = item.getImageurls();
            ImageLoader.getInstance().displayImage(img[0].getUrl(), viewHold2.img1);
            ImageLoader.getInstance().displayImage(img[1].getUrl(), viewHold2.img2);
            ImageLoader.getInstance().displayImage(img[2].getUrl(), viewHold2.img3);

        } else if (holder instanceof FootViewHold) {
//            FootViewHold footViewHold = (FootViewHold) holder;
//            footViewHold.itemView.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position < news.size() && position >= 0) {
            if (mRefreshPosition > 0 && mRefreshPosition == position)
                return VIEWTYPE_BOUNDARY;
            if (news.get(position).getImageurls() != null && news.get(position).getImageurls().length > 2)
                return VIEWTYPE_IMGS;
            return VIEWTYPE_NOIMG;
        } else {
            return VIEWTYPE_FOOT;
        }
    }

    @Override
    public int getItemCount() {
//        if (mRefreshPosition>0)
//            return news.size()+mRefreshPosition;
        return news.size() + mFootViewList.size();
    }


    //少图
    private class ViewHold1 extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        TextView souce;
        TextView date;
        ImageView img;

        public ViewHold1(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_title);
            desc = (TextView) itemView.findViewById(R.id.news_desc);
            souce = (TextView) itemView.findViewById(R.id.news_souce);
            date = (TextView) itemView.findViewById(R.id.news_date);
            img = (ImageView) itemView.findViewById(R.id.news_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = news.get(getAdapterPosition()).getLink();
                    Presenter.getInstance().startNewsReadActivity(url);
                }
            });
        }
    }

    //多图
    private class ViewHold2 extends RecyclerView.ViewHolder {

        TextView title;
        TextView desc;
        TextView souce;
        TextView date;
        ImageView img1;
        ImageView img2;
        ImageView img3;

        public ViewHold2(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_title);
            desc = (TextView) itemView.findViewById(R.id.news_desc);
            souce = (TextView) itemView.findViewById(R.id.news_souce);
            date = (TextView) itemView.findViewById(R.id.news_date);
            img1 = (ImageView) itemView.findViewById(R.id.news_img1);
            img2 = (ImageView) itemView.findViewById(R.id.news_img2);
            img3 = (ImageView) itemView.findViewById(R.id.news_img3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = news.get(getAdapterPosition()).getLink();
                    Presenter.getInstance().startNewsReadActivity(url);
                }
            });
        }
    }

    //分界线
    private class ViewHold3 extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHold3(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.news_boundary);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //刷新
//                    ApiWorkManager.getInstance().getNews(newsPage,newsPage.getChannelId());
                    newsPage.beginRefresh(false);
                }
            });
        }
    }

    private class FootViewHold extends RecyclerView.ViewHolder {
        TextView textView;

        public FootViewHold(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.newsfragm_item_foot);

        }
    }

    public void setNews(List<Item> news) {
        if (news == null)
            return;
        news = doDereplication(news);
        if (news.size() == 0)
            return;
        if (mRefreshPosition != -1) {
            if (mRefreshPosition > 0)
                this.news.remove(mRefreshPosition);
            mRefreshPosition = news.size();
            this.news.add(0, new Item("", "", "", "", null));

        }
        if (!flag) {
            mRefreshPosition = 0;
            flag = true;
        }

        this.news.addAll(0, news);


//        notifyDataSetChanged();
        notifyItemRangeChanged(0, news.size());
        recyclerView.scrollToPosition(0);
    }

    /**
     * 去重复
     */
    public List<Item> doDereplication(List<Item> items) {
        if (news != null) {
            for (int i = 0; i < items.size(); i++) {
                if (news.contains(items.get(i))) {
                    items.remove(i);
                    i--;
                }
            }
        }
        return items;
    }

    public boolean getLoadMoreFlag() {
        return loadMoreFlag;
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    public void setLoadMoreData(List<Item> news) {

        if (news == null) {
            footViewChange("没有更多数据了");
            return;
        }
        news = doDereplication(news);
        if ( news.size() > 0 ) {

            loadMoreFlag = false;
            int p = this.news.size();
            this.news.addAll(news);
            mFootViewList.remove(0);
            notifyItemRangeChanged(p, news.size());


        } else {
            footViewChange("没有更多数据了");

        }

        }
        private void footViewChange(String msg){

            if (mFootViewHold == null )
                return;
            mFootViewHold.textView.setText(msg);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadMoreFlag = false;
                    mFootViewList.remove(0);
                    notifyItemRemoved(NewsPageAdapter.this.news.size());
                }
            }, 1200);

        }
    }
