package com.znvoid.newsapp.view.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.NewsContentRespondBody;
import com.znvoid.newsapp.model.ApiWorkManager;
import com.znvoid.newsapp.model.LoadLisenter;

/**
 * Created by zn on 2017/4/10.
 */

public class NewsReadActivity extends BaseSlidingPaneActivity implements LoadLisenter<NewsContentRespondBody>{
    private WebView webView;
    private String html="<p>原标题：新疆维吾尔自治区检察院依法对阿布都热扎克·沙依木立案侦查</p><p><img src='http://n.sinaimg.cn/news/transform/20170410/tr3i-fyecrxv5059102.jpg' /></p><p>日前，新疆维吾尔自治区人民检察院经审查决定，依法对新疆社会科学院原党委委员、副院长阿布都热扎克·沙依木（副厅级）以涉嫌受贿罪立案侦查并采取强制措施。案件侦查工作正在进行中。</p>";
    private String time;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsread);
        initWebViewSetting();
        webView.loadUrl("file:///android_asset/newsRead.html");
        String url=getIntent().getStringExtra("url");
        System.out.println(url);
        getHtml(url);
//        webView.loadUrl("javascript:showBody(" +"你好"+ ")");
        System.out.println("屏幕宽度："+getResources().getDisplayMetrics().widthPixels);
        ;
    }
    private void initWebViewSetting(){
        webView= (WebView) findViewById(R.id.newsread_webview);
        WebSettings settings = webView.getSettings();
        settings. setJavaScriptEnabled(true);




    }
    private void getHtml(String url){
        if (url!=null) {
            ApiWorkManager.getInstance().getNewsContent(url, this);
        }else {
            onError();
        }

    }



    @Override
    public void onComplete(NewsContentRespondBody items) {

        getSupportActionBar().setTitle(items.getTitle());

        title = items.getTitle().replaceAll("'","\"");


        time = items.getTime();


////        System.out.println(items.getHtml());
        html=items.getHtml().replaceAll("'","\"");
//        System.out.println(html);
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                inntHtml();
            }
        },800);


    }
    private void inntHtml(){
        webView.loadUrl("javascript:loadTitle('"+ title +"')");
        webView.loadUrl("javascript:loadTime('"+ time +"')");
        webView.loadUrl("javascript:loadBody('" +html + "')");
    }

    @Override
    public void onError() {
        webView.loadUrl("javascript:loadBody('" +"加载失败！！" + "')");

    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }


}
