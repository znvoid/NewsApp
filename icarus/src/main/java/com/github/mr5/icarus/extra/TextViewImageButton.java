package com.github.mr5.icarus.extra;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.R;
import com.github.mr5.icarus.button.TextViewButton;

public class TextViewImageButton extends TextViewButton {

    protected Interrupt mInterrupt;
    protected Dialog dialog;
    public TextViewImageButton(TextView textView, Icarus icarus) {
        super(textView, icarus);
        initDialog();
    }

    private void initDialog() {
        dialog = new Dialog(textView.getContext());

        dialog.setTitle("image source");
        LayoutInflater inflater = (LayoutInflater) textView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formView = inflater.inflate(R.layout.form_image_source, null);
//        dialog.getWindow().setLayout(300, 400);
        dialog.setContentView(formView);
        TextView fileTextView = (TextView) formView.findViewById(R.id.image_source_file);
        TextView netTextView = (TextView) formView.findViewById(R.id.image_source_net);
        fileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterrupt!=null)
                    mInterrupt.onInterrupt();
                dialog.dismiss();
            }
        });
        netTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icarus.jsExec("javascript: editor.toolbar.execCommand('" + getName() + "')");
                dialog.dismiss();
            }
        });
    }

    public void command() {
        dialog.show();

//        if (mInterrupt!=null) {
//            mInterrupt.onInterrupt();
//            return;
//        }
//        icarus.jsExec("javascript: editor.toolbar.execCommand('" + getName() + "')");
    }



    public void setmInterrupt(Interrupt interrupt) {
        this.mInterrupt = interrupt;
    }
    public void clearInterrupt(){
        this.mInterrupt =null;
    }
}
