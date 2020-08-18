package com.lingtao.ltvideo.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lingtao.ltvideo.R;

public class ScrollerViewViewHolder extends RecyclerView.ViewHolder {

    private View item;

    public ScrollerViewViewHolder(@NonNull View itemView) {

        super(itemView);
        item = itemView.findViewById(R.id.item);
    }
}
