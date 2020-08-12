package com.lingtao.ltvideo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.bean.ScrollerBean;

import java.util.List;

public class ScrollerViewAdapter extends RecyclerView.Adapter<ScrollerViewViewHolder> {


    private List<ScrollerBean> list;


    public ScrollerViewAdapter(List<ScrollerBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ScrollerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scroller_view_layout, parent, false);
        ScrollerViewViewHolder holder = new ScrollerViewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollerViewViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
