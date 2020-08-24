package com.lingtao.ltvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.adapter.HomeViewPaerAdapter;
import com.lingtao.ltvideo.fragment.ListFragment;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoordinatorLayout1Activity extends AppCompatActivity {


    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.index1)
    TextView index1;
    @BindView(R.id.index2)
    TextView index2;
    @BindView(R.id.index3)
    TextView index3;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    List<Fragment> fragments = new LinkedList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, CoordinatorLayout1Activity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout1);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        fragments.add(ListFragment.getInstance(1));
        fragments.add(ListFragment.getInstance(1));
        fragments.add(ListFragment.getInstance(1));

        viewPager.setAdapter(new HomeViewPaerAdapter(getSupportFragmentManager(), fragments));


    }

    @OnClick({R.id.index1, R.id.index2, R.id.index3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.index1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.index2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.index3:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}