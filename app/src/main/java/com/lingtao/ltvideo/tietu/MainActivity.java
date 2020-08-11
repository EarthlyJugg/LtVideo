package com.lingtao.ltvideo.tietu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.bean.ColorMatrixBean;
import com.lingtao.ltvideo.util.LogUtils;
import com.lingtao.ltvideo.util.ToastUtil;
import com.squareup.picasso.Picasso;
import com.wildma.pictureselector.PictureSelector;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity  {


    @BindView(R.id.showImage)
    ImageView showImage;
    @BindView(R.id.main_decal_view)
    DecalView2 mDecalView;
    @BindView(R.id.filterBtn)
    TextView filterBtn;
    @BindView(R.id.decalBtn)
    TextView decalBtn;
    @BindView(R.id.decalRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.filterRecyclerView)
    RecyclerView filterRecyclerView;


    private List<Integer> decalList;
    private List<ColorMatrixBean> matrixBeanList;
    private String picturePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        initView();
    }

    public void initView() {

        initFilterRecyclerView();
        initRecyclerView();
    }

    private void initFilterRecyclerView() {
        getData();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        filterRecyclerView.setLayoutManager(manager);
        filterRecyclerView.setAdapter(new MyFilterAdapter());
    }

    public void initRecyclerView() {
        initData();
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new MyDecalAdapter());
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

    private List<ColorMatrixBean> getData() {
        matrixBeanList = new LinkedList<>();
        matrixBeanList.add(new ColorMatrixBean("原图", new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        })));
        matrixBeanList.add(new ColorMatrixBean("灰度", new ColorMatrix(new float[]{0.33F, 0.59F, 0.11F, 0F, 0F, 0.33F, 0.59F, 0.11F, 0F, 0F, 0.33F, 0.59F, 0.11F, 0F, 0F, 0F, 0F, 0F, 1F, 0F})));
        matrixBeanList.add(new ColorMatrixBean("黑白", new ColorMatrix(new float[]{
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0, 0, 0, 1, 0,
        })));
        matrixBeanList.add(new ColorMatrixBean("高亮", new ColorMatrix(new float[]{
                1.2f, 0, 0, 0, 0,
                0, 1.2f, 0, 0, 0,
                0, 0, 1.2f, 0, 0,
                0, 0, 0, 1, 0,
        })));
        matrixBeanList.add(new ColorMatrixBean("怀旧", new ColorMatrix(new float[]{
                0.394F, 0.769F, 0.189F, 0F, 0F,
                0.349F, 0.6856F, 0.168F, 0F, 0F,
                0.272F, 0.534F, 0.131F, 0F, 0F,
                0F, 0F, 0F, 1F, 0F
        })));
        matrixBeanList.add(new ColorMatrixBean("高饱和度", new ColorMatrix(new float[]{
                1.438F, -0.122F, -0.016F, 0F, -0.03F,
                -0.062F, 1.378F, -0.016F, 0F, 0.05F,
                -0.062F, -0.122F, 1.483F, 0F, -0.02F,
                0F, 0F, 0F, 1F, 0F
        })));
        matrixBeanList.add(new ColorMatrixBean("颜色反向", new ColorMatrix(new float[]{
                -1, 0, 0, 0, 255,
                0, -1, 0, 0, 255,
                0, 0, -1, 0, 255,
                0, 0, 0, 1, 0
        })));
        return matrixBeanList;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MediaPickerActivity.REQUEST_MEDIA_PICKER_ACTIVITY && resultCode == RESULT_OK) {
            picturePath = data.getStringExtra(MediaPickerActivity.MEDIA_URI);
            Picasso.get().load(picturePath).into(showImage);
        }

        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                LogUtils.d("onActivityResult: " + picturePath);
                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                showImage.setImageBitmap(bm);
//                Picasso.get().load(picturePath).into(showImage);
//                Picasso.get().load(picturePath).into(showImage);
            }
        }

    }


    public void clickCameraButton(View view) {
//        Intent intent = new Intent(this, MediaPickerActivity.class);
//        intent.putExtra(MediaPickerActivity.MEDIA_TYPE, MediaPickerActivity.MEDIA_IMAGE);
//        startActivityForResult(intent, MediaPickerActivity.REQUEST_MEDIA_PICKER_ACTIVITY);
        PictureSelector
                .create(this, PictureSelector.SELECT_REQUEST_CODE)
                .selectPicture(false, 200, 200, 1, 1);

    }



    private void onItemClick(int position) {
        Integer resIds = decalList.get(position);

        Bitmap loadedImage = BitmapFactory.decodeResource(getResources(), resIds);
        mDecalView.addDecal(loadedImage);
    }

    @OnClick({R.id.filterBtn, R.id.decalBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.filterBtn:
                filterBtn.setSelected(true);
                decalBtn.setSelected(false);

                filterBtn.setTextColor(Color.WHITE);
                decalBtn.setTextColor(Color.GRAY);

                filterRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);

                break;
            case R.id.decalBtn:
                filterBtn.setSelected(false);
                decalBtn.setSelected(true);

                filterBtn.setTextColor(Color.GRAY);
                decalBtn.setTextColor(Color.WHITE);

                filterRecyclerView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void changeFilter(ColorMatrix colorMatrix) {
        Bitmap bmp = null;
        if (TextUtils.isEmpty(picturePath)) {
            bmp = handleColorRotateBmp(colorMatrix, R.drawable.zhengfangxing);
        } else {
            bmp = handleColorRotateBmp(colorMatrix, picturePath);
        }
        LogUtils.d("changeFilter: "+picturePath);
        if (bmp != null) {
            LogUtils.d("changeFilter: bitmap不为空");
            showImage.setImageBitmap(bmp);
        } else {
            LogUtils.d("changeFilter: bitmap为空");
            showImage.setBackgroundColor(Color.YELLOW);
        }
    }

    /**
     * tempBmp   需要处理生成的bitmap
     * originBmp 原始bitmap
     */
    private Bitmap handleColorRotateBmp(ColorMatrix colorMatrix, String path) {

        Bitmap originBmp = BitmapFactory.decodeFile(path);
        if (originBmp == null) {
            return null;
        }
        Bitmap tempBmp = Bitmap.createBitmap(originBmp.getWidth(), originBmp.getHeight(), Bitmap.Config.ARGB_8888);
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(tempBmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));// 设置颜色变换效果

        canvas.drawBitmap(originBmp, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return tempBmp;
    }

    private Bitmap handleColorRotateBmp(ColorMatrix colorMatrix, int res) {

        Bitmap originBmp = BitmapFactory.decodeResource(getResources(), res);
        if (originBmp == null) {
            return null;
        }
        Bitmap tempBmp = Bitmap.createBitmap(originBmp.getWidth(), originBmp.getHeight(), Bitmap.Config.ARGB_8888);
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(tempBmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));// 设置颜色变换效果

        canvas.drawBitmap(originBmp, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return tempBmp;
    }

    class MyDecalAdapter extends RecyclerView.Adapter<MyViewHolder> {


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


    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_decal;

        public MyViewHolder(View view) {
            super(view);

            iv_decal = (ImageView) view.findViewById(R.id.iv_decal);
        }

    }


    class MyFilterAdapter extends RecyclerView.Adapter<MyViewHolder2> {


        @NonNull
        @Override
        public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_matrix_layot, parent, false);
            MyViewHolder2 vh = new MyViewHolder2(itemView);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
            ColorMatrixBean bean = matrixBeanList.get(position);
            holder.name.setText(bean.getName());
            LogUtils.d("onBindViewHolder: " + bean.getName());
            holder.name.setOnClickListener(v -> {
//                Toast.makeText(MainActivity.this, bean.getName(), Toast.LENGTH_SHORT).show();
                ToastUtil.show(MainActivity.this, bean.getName());
                changeFilter(bean.getColorMatrix());
            });
            Bitmap bmp = handleColorRotateBmp(bean.getColorMatrix(), R.drawable.zhengfangxing);
            if (bmp != null) {
                holder.showFilterImage.setImageBitmap(bmp);
            } else {
                holder.showFilterImage.setBackgroundColor(Color.RED);
            }
        }

        @Override
        public int getItemCount() {
            return matrixBeanList.size();
        }


    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView showFilterImage;

        public MyViewHolder2(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            showFilterImage = view.findViewById(R.id.showFilterImage);
        }

    }

}
