package com.lingtao.ltvideo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingtao.ltvideo.R;

import java.util.ArrayList;

import butterknife.BindView;

public class ListFragment extends Fragment {


    public static Fragment getInstance(int number) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        fragment.setArguments(bundle);
        return fragment;
    }

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_layout, container, false);
        initView(view);
        return view;
    }



    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int number = getArguments().getInt("number", 1);
        final ArrayList<String> strings = new ArrayList<>();
        for (int x = 0; x < number; x++) {
            strings.add("我是条目" + x);
        }
        BaseQuickAdapter baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_coor_layout, strings) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_title, item);
            }
        };
        baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        baseQuickAdapter.isFirstOnly(false);
        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        recyclerView.setAdapter(baseQuickAdapter);


    }
}
