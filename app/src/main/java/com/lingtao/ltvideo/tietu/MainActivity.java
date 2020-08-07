package com.lingtao.ltvideo.tietu;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.util.LogUtils;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private DecalView mDecalView;

    private Toolbar toolbar;

    private RecyclerView mRecyclerView;

    private List<Integer> decalList;
    private String[] desList;
    private ImageView showImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
    }

    public void initView() {

        showImage = ((ImageView) findViewById(R.id.showImage));

        mDecalView = (DecalView) findViewById(R.id.main_decal_view);
        initRecyclerView();
    }

    public void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.decal_list);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        decalList = getResources().getStringArray(R.array.decal_bitmap_items);
        desList = getResources().getStringArray(R.array.decal_des_items);
        initData();

        mRecyclerView.setAdapter(new MyAdapter(this));
    }

    private void initData() {
        decalList = new LinkedList<>();
        decalList.add(R.mipmap.decal_abdominalmuscles);
        decalList.add(R.mipmap.decal_baymaxandjianjian);
        decalList.add(R.mipmap.decal_beaddicted);
        decalList.add(R.mipmap.decal_beastmode);
        decalList.add(R.mipmap.decal_beyourself);
        decalList.add(R.mipmap.decal_broccoli);
        decalList.add(R.mipmap.decal_captainamerica);
        decalList.add(R.mipmap.decal_catwoman);
        decalList.add(R.mipmap.decal_cute);
        decalList.add(R.mipmap.decal_darkcuisine);
        decalList.add(R.mipmap.decal_death);
        decalList.add(R.mipmap.decal_dontwait);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == MediaPickerActivity.REQUEST_MEDIA_PICKER_ACTIVITY && resultCode == RESULT_OK) {
//            ImageLoader.getInstance().loadImage(data.getStringExtra(MediaPickerActivity.MEDIA_URI), new SimpleImageLoadingListener() {
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    // Do whatever you want with Bitmap
//                    mCropView.setBackgroundBitmap(loadedImage);
//                }
//            });
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MediaPickerActivity.REQUEST_MEDIA_PICKER_ACTIVITY && resultCode == RESULT_OK) {
            String stringExtra = data.getStringExtra(MediaPickerActivity.MEDIA_URI);
//            Bitmap loadedImage = BitmapFactory.decodeFile(stringExtra);
//            showImage.setImageBitmap(loadedImage);
            Picasso.get().load(stringExtra).into(showImage);

        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                clickCameraButton();
//                break;
//        }
//        return true;
//    }

    public void clickCameraButton(View view) {
        Intent intent = new Intent(this, MediaPickerActivity.class);
        intent.putExtra(MediaPickerActivity.MEDIA_TYPE, MediaPickerActivity.MEDIA_IMAGE);
        startActivityForResult(intent, MediaPickerActivity.REQUEST_MEDIA_PICKER_ACTIVITY);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    private void onItemClick(int position) {
        Integer resIds = decalList.get(position);

        Bitmap loadedImage = BitmapFactory.decodeResource(getResources(), resIds);
        mDecalView.addDecal(loadedImage);
//        showImage.setImageBitmap(loadedImage);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private final Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dical, parent, false);
            MyViewHolder vh = new MyViewHolder(itemView);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_des.setText(desList[position]);
//            ImageLoader.getInstance().displayImage(decalList[position], holder.iv_decal);

//            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), decalList.get(position));
            Picasso.get().load(decalList.get(position)).into(holder.iv_decal);
            holder.iv_decal.setOnClickListener(v -> {

//                BitmapFactory.decodeResource(getResources())
//                mDecalView.addDecal(loadedImage);
                onItemClick(position);
            });
        }

        @Override
        public int getItemCount() {
            return decalList.size();
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder  {
        public ImageView iv_decal;
        public TextView tv_des;

        public MyViewHolder(View view) {
            super(view);

            iv_decal = (ImageView) view.findViewById(R.id.iv_decal);
            tv_des = (TextView) view.findViewById(R.id.tv_des);
        }

    }
}