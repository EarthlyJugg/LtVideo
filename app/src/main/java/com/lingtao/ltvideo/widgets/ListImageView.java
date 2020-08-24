package com.lingtao.ltvideo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.lingtao.ltvideo.R;

import java.util.LinkedList;
import java.util.List;

public class ListImageView extends FrameLayout {

    private List<String> urlList = new LinkedList<>();
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;


    public ListImageView(@NonNull Context context) {
        this(context, null);
    }

    public ListImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        View view = LayoutInflater.from(context).inflate(R.layout.common_list_imageview_layout, this);
        initView(view);

    }

    private void initView(View view) {
        imageView1 = ((ImageView) view.findViewById(R.id.imageview1));
        imageView2 = ((ImageView) view.findViewById(R.id.imageview2));
        imageView3 = ((ImageView) view.findViewById(R.id.imageview3));


    }

    public void setUrlList(List<String> list) {
        if (list==null||list.size()==0) {
            return;
        }
        urlList.clear();
        urlList.addAll(list);

        int size = urlList.size();
        if (size == 1) {
            imageView1.setVisibility(VISIBLE);
            imageView2.setVisibility(INVISIBLE);
            imageView3.setVisibility(INVISIBLE);
            Glide.with(getContext()).load(urlList.get(0)).error(R.drawable.erciyuan).into(imageView1);

        } else if (size == 2) {
            imageView1.setVisibility(VISIBLE);
            imageView2.setVisibility(VISIBLE);
            imageView3.setVisibility(INVISIBLE);
            Glide.with(getContext()).load(urlList.get(0)).error(R.drawable.erciyuan).into(imageView1);
            Glide.with(getContext()).load(urlList.get(1)).error(R.drawable.erciyuan).into(imageView2);
        } else {
            imageView1.setVisibility(VISIBLE);
            imageView2.setVisibility(VISIBLE);
            imageView3.setVisibility(VISIBLE);
            Glide.with(getContext()).load(urlList.get(0)).error(R.drawable.erciyuan).into(imageView1);
            Glide.with(getContext()).load(urlList.get(1)).error(R.drawable.erciyuan).into(imageView2);
            Glide.with(getContext()).load(urlList.get(2)).error(R.drawable.erciyuan).into(imageView3);
        }


    }
}
