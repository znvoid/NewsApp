package com.znvoid.newsapp.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.activity.CaptureActivity;
import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.ExpressRespondBody;
import com.znvoid.newsapp.model.LoadLisenter;
import com.znvoid.newsapp.view.fragment.ExpBottonSheetDialogFragment;

import java.util.regex.Pattern;

/**
 * Created by zn on 2017/4/8.
 * 二维码扫描结果展示
 */

public class ZXingActivity extends AppCompatActivity implements LoadLisenter<ExpressRespondBody> {
    private static final int REQUEST_CODE = 3;
    private static final int STAT_EXP = 1;
    private static final int STAT_WEBSITE = 2;
    private static final int STAT_UNKONW = 3;
    EditText zxingEdit;

    Button zxingBt_scan;

    Button zxingBt_action;
    private int stat = STAT_UNKONW;
    private ExpBottonSheetDialogFragment dialogFragment;
//    private RecyclerView recyclerView;
//    private ExpAdapter adapter;
//    private TextView tv;

    private Handler handler=new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        initView();
        boolean zxing = getIntent().getBooleanExtra("zxing",false);
        if (zxing)
            startZxing();
    }

    private void initView() {
        zxingEdit = (EditText) findViewById(R.id.zxing_edit);
        zxingBt_scan = (Button) findViewById(R.id.zxing_bt_scan);
        zxingBt_action = (Button) findViewById(R.id.zxing_bt_action);

        dialogFragment=new ExpBottonSheetDialogFragment();
//
//        recyclerView= (RecyclerView) findViewById(R.id.exp_recycleview);
//        adapter=new ExpAdapter(this,null);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        tv= (TextView) findViewById(R.id.exp_tv);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zxing_bt_action:
                switch (stat){
                    case STAT_EXP:
//                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                        dialogFragment.show(getSupportFragmentManager(), "bottom sheet");
                        dialogFragment.setNu(zxingEdit.getText().toString().trim());
//                        tv.setVisibility(View.VISIBLE);
//                        tv.setText("正在加载中。。。");
//                        ApiWorkManager.getInstance().getExpressData(zxingEdit.getText().toString().trim(),this);
                        break;
                    case STAT_WEBSITE:
                        startWeb(zxingEdit.getText().toString().trim());
                        break;
                    case STAT_UNKONW:

                        break;
                }
                break;
            case R.id.zxing_bt_scan:
                startZxing();
                break;
        }

    }

    //打开二维码扫描
    private void startZxing() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    //打开浏览器
    private void startWeb(String url) {
        System.out.println(url);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result = data.getExtras().getString("result");
                zxingEdit.setText(result);
                check(result);
            } else {
                finish();
            }
        }

    }

    private final String regex_net = "((http|ftp|https):\\/\\/)?[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
    private final String regex_num = "^[1-9][0-9]{11,12}$";

    private void check(String result) {
        if (Pattern.matches(regex_net, result)) {
            zxingBt_action.setEnabled(true);
            zxingBt_action.setText("网页访问");
            stat=STAT_WEBSITE;
        } else if (Pattern.matches(regex_num, result)) {
            zxingBt_action.setEnabled(true);
            zxingBt_action.setText("快递单号？\n查询");
            stat=STAT_EXP;
        } else {
            zxingBt_action.setEnabled(false);
            zxingBt_action.setText("未知数据");
            stat=STAT_UNKONW;
        }


    }


    @Override
    public void onComplete(ExpressRespondBody item) {
//        List<ExpressData> data = item.getData();
//        if (data!=null) {
//            tv.setVisibility(View.GONE);
//            for (int i = 0; i < data.size(); i++) {
//                TimeEvent d = new TimeEvent(data.get(i).getTime(), data.get(i).getContext());
//                System.out.println(d.getEvent());
//                adapter.add(0,d);
//            }
//        }


    }

    @Override
    public void onError() {
//        tv.setText("获取失败！！");
    }
}
