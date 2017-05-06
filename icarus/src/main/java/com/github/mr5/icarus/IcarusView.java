package com.github.mr5.icarus;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.mr5.icarus.button.Button;
import com.github.mr5.icarus.button.FontScaleButton;
import com.github.mr5.icarus.button.TextViewButton;
import com.github.mr5.icarus.entity.Options;
import com.github.mr5.icarus.extra.Interrupt;
import com.github.mr5.icarus.extra.TextViewImageButton;
import com.github.mr5.icarus.popover.FontScalePopoverImpl;
import com.github.mr5.icarus.popover.HtmlPopoverImpl;
import com.github.mr5.icarus.popover.ImagePopoverImpl;
import com.github.mr5.icarus.popover.LinkPopoverImpl;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by zn on 2017/5/5.
 */

public class IcarusView extends FrameLayout{

    private WebView webView;
    private Icarus icarus;
    private TextViewToolbar toolbar;

    public IcarusView(Context context) {
        this(context,null);
        init(context);
    }

    public IcarusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IcarusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init( Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.icarus_layout, null);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        addView(view);
        webView = (WebView) view.findViewById(R.id.editor);

        toolbar = new TextViewToolbar();
        Options options = new Options();
        options.setPlaceholder("Input something...");
        //  img: ['src', 'alt', 'width', 'height', 'data-non-image']
        // a: ['href', 'target']
        options.addAllowedAttributes("img", Arrays.asList("data-type", "data-id", "class", "src", "alt", "width", "height", "data-non-image"));
        options.addAllowedAttributes("iframe", Arrays.asList("data-type", "data-id", "class", "src", "width", "height"));
        options.addAllowedAttributes("a", Arrays.asList("data-type", "data-id", "class", "href", "target", "title"));

        icarus = new Icarus(toolbar, options, webView);
        prepareToolbar(toolbar, icarus,view);
//        icarus.loadCSS("file:///android_asset/icarus-editor/styles/editor.css");
        icarus.render();
    }

    private void prepareToolbar(TextViewToolbar toolbar, Icarus icarus,View view) {
//        Typeface iconfont = Typeface.createFromAsset(getContext().getAssets(), "Simditor.ttf");
        Typeface iconfont = Typeface.createFromAsset(getContext().getAssets(), "icarus-editor/styles/Simditor.ttf");

        HashMap<String, Integer> generalButtons = new HashMap<>();
        generalButtons.put(Button.NAME_BOLD, R.id.button_bold);
        generalButtons.put(Button.NAME_OL, R.id.button_list_ol);
        generalButtons.put(Button.NAME_BLOCKQUOTE, R.id.button_blockquote);
        generalButtons.put(Button.NAME_HR, R.id.button_hr);
        generalButtons.put(Button.NAME_UL, R.id.button_list_ul);
        generalButtons.put(Button.NAME_ALIGN_LEFT, R.id.button_align_left);
        generalButtons.put(Button.NAME_ALIGN_CENTER, R.id.button_align_center);
        generalButtons.put(Button.NAME_ALIGN_RIGHT, R.id.button_align_right);
        generalButtons.put(Button.NAME_ITALIC, R.id.button_italic);
        generalButtons.put(Button.NAME_INDENT, R.id.button_indent);
        generalButtons.put(Button.NAME_OUTDENT, R.id.button_outdent);
        generalButtons.put(Button.NAME_CODE, R.id.button_math);
        generalButtons.put(Button.NAME_UNDERLINE, R.id.button_underline);
        generalButtons.put(Button.NAME_STRIKETHROUGH, R.id.button_strike_through);

        for (String name : generalButtons.keySet()) {
            TextView textView = (TextView) view.findViewById(generalButtons.get(name));
            if (textView == null) {
                continue;
            }
            textView.setTypeface(iconfont);
            TextViewButton button = new TextViewButton(textView, icarus);
            button.setName(name);
            toolbar.addButton(button);
        }
        TextView linkButtonTextView = (TextView) view.findViewById(R.id.button_link);
        linkButtonTextView.setTypeface(iconfont);
        TextViewButton linkButton = new TextViewButton(linkButtonTextView, icarus);
        linkButton.setName(Button.NAME_LINK);
        linkButton.setPopover(new LinkPopoverImpl(linkButtonTextView, icarus));
        toolbar.addButton(linkButton);

//        TextView imageButtonTextView = (TextView)view. findViewById(R.id.button_image);
//        imageButtonTextView.setTypeface(iconfont);
//        TextViewButton imageButton = new TextViewButton(imageButtonTextView, icarus);
//        imageButton.setName(Button.NAME_IMAGE);
//        imageButton.setPopover(new ImagePopoverImpl(imageButtonTextView, icarus));
//        toolbar.addButton(imageButton);

        TextView imageButtonTextView = (TextView)view. findViewById(R.id.button_image);
        imageButtonTextView.setTypeface(iconfont);
        TextViewImageButton imageButton = new TextViewImageButton(imageButtonTextView, icarus);
        imageButton.setName(Button.NAME_IMAGE);
        imageButton.setPopover(new ImagePopoverImpl(imageButtonTextView, icarus));
        toolbar.addButton(imageButton);

        TextView htmlButtonTextView = (TextView) view.findViewById(R.id.button_html5);
        htmlButtonTextView.setTypeface(iconfont);
        TextViewButton htmlButton = new TextViewButton(htmlButtonTextView, icarus);
        htmlButton.setName(Button.NAME_HTML);
        htmlButton.setPopover(new HtmlPopoverImpl(htmlButtonTextView, icarus));
        toolbar.addButton(htmlButton);

        TextView fontScaleTextView = (TextView)view. findViewById(R.id.button_font_scale);
        fontScaleTextView.setTypeface(iconfont);
        TextViewButton fontScaleButton = new FontScaleButton(fontScaleTextView, icarus);
        fontScaleButton.setPopover(new FontScalePopoverImpl(fontScaleTextView, icarus));
        toolbar.addButton(fontScaleButton);


    }




    public void destroy(){
        webView.destroy();
    }
    public void getContent(Callback callback){
        icarus.getContent(callback);
    }
    public void setContent(String content){
        icarus.setContent(content);
    }

    public Icarus getIcarus(){
        return icarus;
    }
    public void addImage(String filePath){
        String html="<img alt=\"Image\"  src=\"file:///"+filePath+"\">";
        icarus.insertHtml(html);
    }

    public void setmInterrupt(Interrupt interrupt) {
        Button button = toolbar.getButton(Button.NAME_IMAGE);
        if (button instanceof TextViewImageButton)
            ((TextViewImageButton) button).setmInterrupt(interrupt);
    }
    public void clearInterrupt(){
        Button button = toolbar.getButton(Button.NAME_IMAGE);
        if (button instanceof TextViewImageButton)
            ((TextViewImageButton) button).clearInterrupt();
    }
}
