package com.lingtao.ltvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.bean.GirlItemData;
import com.lingtao.ltvideo.util.ImageLoader;
import com.lingtao.ltvideo.widgets.ScaleImageView;

import java.util.List;

public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.ViewHodlder> {


    private List<GirlItemData> list;

    private Context context;

    public GirlAdapter(List<GirlItemData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_girl_layout, parent, false);
        return new ViewHodlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodlder holder, int position) {
        ScaleImageView imageView = holder.scaleImageView;
        GirlItemData girlItemData = list.get(position);
        imageView.setInitSize(girlItemData.getWidth(), girlItemData.getHeight());
        ImageLoader.load(context, girlItemData.getUrl(), imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodlder extends RecyclerView.ViewHolder {

        private ScaleImageView scaleImageView;

        public ViewHodlder(@NonNull View itemView) {
            super(itemView);
            scaleImageView = itemView.findViewById(R.id.girl_item_iv);
        }
    }

}