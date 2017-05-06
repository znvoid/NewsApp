package com.znvoid.newsapp.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.github.mr5.icarus.Callback;
import com.github.mr5.icarus.IcarusView;
import com.github.mr5.icarus.extra.Interrupt;
import com.google.gson.Gson;
import com.znvoid.newsapp.bean.NoteBean;

import org.litepal.crud.DataSupport;

/**
 * Created by zn on 2017/5/5.
 */

public class NoteEditActivity extends BaseSlidingPaneActivity implements Interrupt {

    private static final int RESULT_LOAD_IMAGE = 21;
    private IcarusView icarview;
    private NoteBean noteBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        icarview=new IcarusView(this);
        setContentView(icarview);
//        icarview = (IcarusView) findViewById(R.id.icarusview);
        icarview.setmInterrupt(this);
        long id;
        if ((id=getIntent().getLongExtra("noteId",-1))!=-1){
            noteBean = DataSupport.find(NoteBean.class,id);
            icarview.getIcarus().setContent(noteBean.getContent());
        }else {
            noteBean=new NoteBean();

        }
        Log.d("Content id",id+"");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        icarview.destroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
        icarview.getIcarus().getContent(new Callback() {
            @Override
            public void run(String params) {
                Gson gson = new Gson();
                NoteBean temp = gson.fromJson(params, NoteBean.class);
                if (temp.getContent().equals("")||temp.getContent().equals(noteBean.getContent()))
                    return;
                noteBean.setContent(temp.getContent());
                noteBean.setTime(System.currentTimeMillis());
                noteBean.save();
                Log.d("Content gotten", noteBean.getContent());
            }
        });
    }

    @Override
    public void onInterrupt() {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            icarview.addImage(picturePath);


        }

    }
}
