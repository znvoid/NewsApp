package com.znvoid.newsapp.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.NoteBean;
import com.znvoid.newsapp.view.activity.NoteEditActivity;
import com.znvoid.newsapp.view.adapt.BaseRecyclerViewAdapter;
import com.znvoid.newsapp.view.adapt.NoteAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zn on 2017/5/5.
 */

public class NoteListFragment extends Fragment implements BaseRecyclerViewAdapter.OnItemClickListener, BaseRecyclerViewAdapter.OnItemLongClickListener {
    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private NoteAdapter adapter;

    private Runnable runnable =new Runnable() {
        @Override
        public void run() {

            notes = DataSupport.where("id > ?","-1").order("time desc").find(NoteBean.class);
            adapter.setNoteDate(notes);
        }
    };
    private List<NoteBean> notes=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        recyclerView= (RecyclerView)view. findViewById(R.id.recy);
        actionButton= (FloatingActionButton)view. findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNoteEditActivity(-1);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NoteAdapter(getActivity(),null);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.removeCallbacks(runnable);
        recyclerView.postDelayed(runnable,500);
    }


    private void startNoteEditActivity(long id){
        Intent intent =new Intent(getActivity(),NoteEditActivity.class);
        intent.putExtra("noteId",id);
        startActivity(intent);

    }

    @Override
    public void onItemClick(View itemView, int pos) {
        long id=notes.get(pos).getId();
        startNoteEditActivity(id);
    }

    @Override
    public void onItemLongClick(View itemView, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("删除笔记");
        builder.setMessage("确认是否删除笔记，彻底删除笔记将不可恢复");


        builder.setPositiveButton("删除",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NoteBean remove = notes.remove(pos);
                        adapter.delete(pos);
                        remove.delete();
                        dialog.dismiss();
                    }
                });


        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();


    }
}
