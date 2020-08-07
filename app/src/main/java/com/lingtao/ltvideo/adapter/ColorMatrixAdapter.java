package com.lingtao.ltvideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.bean.ColorMatrixBean;

import java.util.List;

public class ColorMatrixAdapter extends BaseAdapter {

    private List<ColorMatrixBean> list;
    private Context mContext;
    private LayoutInflater inflater;
    private onItemClick onItemClick;

    public ColorMatrixAdapter(Context mContext, List<ColorMatrixBean> list) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_color_matrix_layot, null);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ColorMatrixBean bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.name.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.onClick(bean, position);
            }
        });

        return convertView;
    }

    public void setOnItemClick(ColorMatrixAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private class ViewHolder {

        private Button name;

    }

    public interface onItemClick{
        void onClick(ColorMatrixBean bean, int position);
    }

}
