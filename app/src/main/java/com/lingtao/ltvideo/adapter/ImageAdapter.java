package com.lingtao.ltvideo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.base.ImageBean;
import com.lingtao.ltvideo.giflib.GIfBean;
import com.lingtao.ltvideo.giflib.LtGif;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private List<GIfBean> images;
    private Context context;

    public ImageAdapter(List<GIfBean> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GIfBean imageBean = images.get(position);
        imageBean.showImage(holder.imageView);
        holder.player.setVisibility(imageBean.isPlayer() ? View.GONE : View.VISIBLE);
        holder.player.setOnClickListener(v -> {
            for (int i = 0; i < images.size(); i++) {
                GIfBean image = images.get(i);
                if (image.isPlayer()) {
                    image.pause();
                    notifyItemChanged(i);
                    break;
                }
            }

            holder.player.setVisibility(View.GONE);
            imageBean.start(holder.imageView);
        });

    }


    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        //释放资源
        int position = holder.getAdapterPosition();
        //越界检查
        if (position < 0 || position >= images.size()) {
            return;
        }
        GIfBean gIfBean = images.get(position);
        if (gIfBean.isPlayer()) {
            gIfBean.pause();
            holder.player.setVisibility(View.VISIBLE);
            gIfBean.showImage(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView player;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            player = itemView.findViewById(R.id.player);

        }
    }
}

