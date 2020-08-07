package com.lingtao.ltvideo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lingtao.ltvideo.R;
import com.lingtao.ltvideo.util.LogUtils;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 相机预览界面
 */
public class CameraPreviewActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_CODE = 1;
    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int PREVIEW_FORMAT = ImageFormat.NV21;

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.openPreview)
    Button openPreview;
    @BindView(R.id.switchCamera)
    Button switchCamera;

    /*摄像头的数据*/
    @Nullable
    private Camera.CameraInfo mFrontCameraInfo = null;
    private int mFrontCameraId = -1;//前置摄像头id

    @Nullable
    private Camera.CameraInfo mBackCameraInfo = null;
    private int mBackCameraId = -1;//后置摄像头id
    private int mCameraId = -1;//当前摄像头id
    private Camera.CameraInfo mCameraInfo = null;
    private SurfaceHolder mSurfaceHolder;
    private int mPreviewSurfaceWidth;
    private int mPreviewSurfaceHeight;

    Camera mCamera;


    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

            LogUtils.d("MainActivity_log", "surfaceCreated: ");
        }

        /**
         * 实际需求经常要求 APP 能够支持前后置摄像头的切换，
         * 。大部分情况下我们在切换前后置摄像头的时候，
         * 都会直接复用同一个 Surface，
         * 所以我们会在 surfaceChanged() 的时候把 Surface 保存下来
         *
         */
        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
            mSurfaceHolder = surfaceHolder;
            mPreviewSurfaceWidth = width;
            mPreviewSurfaceHeight = height;
            LogUtils.d("MainActivity_log", "surfaceChanged: ");
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            mSurfaceHolder = null;
            mPreviewSurfaceWidth = 0;
            mPreviewSurfaceHeight = 0;
            LogUtils.d("MainActivity_log", "surfaceDestroyed: ");
        }
    };

    Camera.PreviewCallback previewCallback1 = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] bytes, Camera camera) {
            LogUtils.d("MainActivity_log", "onPreviewFrame: " + bytes.length);
        }
    };

    Camera.PreviewCallback previewCallback2 = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] bytes, Camera camera) {
            camera.addCallbackBuffer(bytes);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        ButterKnife.bind(this);
        surfaceView.getHolder().addCallback(callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int numberOfCameras = Camera.getNumberOfCameras();// 获取摄像头个数
        LogUtils.d("Camera1Activity_log", "onClick: " + numberOfCameras);
        // 动态权限检查
        if (!isRequiredPermissionsGranted() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_PERMISSIONS_CODE);
        }
        initCameraInfo();

//        int cameraFacingBack = Camera.CameraInfo.CAMERA_FACING_BACK;
//        Camera camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
//        //是否支持静音拍照
//        //当它返回 false 的时候，即使你调用 Camera.enableShutterSound(false)，相机在拍照的时候也会发出声音。
//        boolean sound = camera.enableShutterSound(false);
//        LogUtils.d("MainActivity_log", "onResume: " + sound);


    }

    @Override
    protected void onPause() {
        super.onPause();
        closeCamera();
    }

    /**
     * 关闭相机。
     */
    private void closeCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;

        }
    }


    /**
     * 判断我们需要的权限是否被授予，只要有一个没有授权，我们都会返回 false。
     *
     * @return true 权限都被授权
     */
    private boolean isRequiredPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 初始化摄像头信息。
     */
    private void initCameraInfo() {
        int numberOfCameras = Camera.getNumberOfCameras();// 获取摄像头个数
        LogUtils.d("MainActivity_log", "initCameraInfo: " + numberOfCameras);
        for (int cameraId = 0; cameraId < numberOfCameras; cameraId++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // 后置摄像头信息
                mBackCameraId = cameraId;
                mBackCameraInfo = cameraInfo;
                //默认
                mCameraId = cameraId;
            } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // 前置摄像头信息
                mFrontCameraId = cameraId;
                mFrontCameraInfo = cameraInfo;
                if (mCameraId == -1) {
                    mCameraId = cameraId;
                }
            }
        }
    }


    /**
     * 开启指定摄像头
     */
    private void openCamera(int cameraId) {
        if (mCamera != null) {
            throw new RuntimeException("相机已经被开启，无法同时开启多个相机实例！");
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mCamera = Camera.open(cameraId);
            mCameraId = cameraId;
            mCameraInfo = cameraId == mFrontCameraId ? mFrontCameraInfo : mBackCameraInfo;
            mCamera.setDisplayOrientation(getCameraDisplayOrientation(mCameraInfo));
        }
    }

    /**
     * 官网给出的调整摄像头方向方法
     *
     * @param cameraInfo
     * @return
     */
    private int getCameraDisplayOrientation(Camera.CameraInfo cameraInfo) {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (cameraInfo.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (cameraInfo.orientation - degrees + 360) % 360;
        }
        return result;
    }


    @OnClick({R.id.openPreview, R.id.switchCamera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.openPreview:
                initCamera(mBackCameraId);
                break;
            case R.id.switchCamera:
                mCameraId = (mCameraId == mBackCameraId) ? mFrontCameraId : mBackCameraId;
                initCamera(mCameraId);
                break;
        }
    }

    /**
     * 相机功能的强大与否完全取决于各手机厂商的底层实现，
     * 在基于相机开发任何功能之前，你都需要通过某些手段判断当前设备相机的能力是否足以支撑你要开发的功能，
     * 而 Camera.Parameters 就是我们判断相机能力大小的手段，在 Camera.Parameters
     * 里提供了大量形如 getSupportedXXX 的方法，通过这些方法你就可以判断相机某方面的功能是否达到你的要求，
     * 例如通过 getSupportedPreviewSizes() 可以获取相机支持的预览尺寸列表，进而从这个列表中查询是否有满足你需求的尺寸。
     * <p>
     * 除了通过 Camera.Parameters 判断相机功能的支持情况之外，我们还通过 Camera.Parameters 设置绝大部分相机参数，
     * 并且通过 Camera.setParameters() 方法将设置好的参数传给底层，让这些参数生效。所以相机参数的配置流程基本就是以下三个步骤：
     * <p>
     * 1、通过 Camera.getParameters() 获取 Camera.Parameters 实例。
     * 2、通过 Camera.Parameters.getSupportedXXX 获取某个参数的支持情况。
     * 3、通过 Camera.Parameters.set() 方法设置参数。
     * 4、通过 Camera.setParameters() 方法将参数应用到底层。
     */
    private void initCamera(int cameraId) {
        if (mCamera != null) {
            closeCamera();
        }

        openCamera(cameraId);
        Camera.Parameters parameters = mCamera.getParameters();
        List<Integer> list = parameters.getSupportedPictureFormats();
        for (Integer integer : list) {
            LogUtils.d("MainActivity_log", "initCamera: " + integer);
        }
        /**
         * w=1440,h=720
         * w=1920,h=1088
         * w=1920,h=1080
         * w=1440,h=1080
         * w=1280,h=960
         * w=1280,h=720
         * w=1088,h=1088
         * w=960,h=540
         * w=720,h=480
         * w=640,h=480
         * w=352,h=288
         * w=320,h=240
         * w=176,h=144
         */
        //不使用缓存回调,设置尺寸
        setPreviewSize1(1080, 1920);
        mCamera.setPreviewCallback(previewCallback1);

        //使用缓存回调
//        setPreviewSize2(1080, 1920);
//        mCamera.setPreviewCallbackWithBuffer(previewCallback2);


        if (isPreviewFormatSupported(parameters, ImageFormat.NV21)) {
            //配置预览数据的格式
            parameters.setPreviewFormat(ImageFormat.NV21);
        }
        setPreviewSurface(mSurfaceHolder);

        Toast.makeText(this, "初始化完成", Toast.LENGTH_SHORT).show();


        startPreview();
    }

    /**
     * 根据指定的尺寸要求设置预览尺寸，我们会同时考虑指定尺寸的比例和大小。
     *
     * @param shortSide 短边长度 1080
     * @param longSide  长边长度 1920
     */
    @WorkerThread
    private void setPreviewSize1(int shortSide, int longSide) {

        if (mCamera != null && shortSide != 0 && longSide != 0) {
            float aspectRatio = (float) longSide / shortSide;
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            for (Camera.Size previewSize : supportedPreviewSizes) {
                if ((float) previewSize.width / previewSize.height == aspectRatio &&
                        previewSize.height <= shortSide &&
                        previewSize.width <= longSide) {
                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                    mCamera.setParameters(parameters);
                    break;
                }
            }
        }
    }


    /**
     * 上面说到我们是通过回调的方式获取相机预览数据的，
     * 所以相机为我们提供了一个回调接口叫 Camera.PreviewCallback，
     * 我们只需实现该接口并且注册给相机就可以在预览的时候接收到数据了，
     * 注册回调接口的方式有两种
     * 1.setPreviewCallback()：注册预览回调
     * 2.setPreviewCallbackWithBuffer()：注册预览回调，并且使用已经配置好的缓冲池
     * <p>
     * 使用 setPreviewCallback() 注册预览回调获取预览数据是最简单的，
     * 因为你不需要其他配置流程，直接注册即可，但是出于性能考虑，
     * 官方推荐我们使用 setPreviewCallbackWithBuffer()，
     * 因为它会使用我们配置好的缓冲对象回调预览数据，避免重复创建内存占用很大的对象。
     * 所以接下来我们重点介绍如何根据预览尺寸配置对象池并注册回调，整个步骤如下
     * <p>
     * 1.根据需求确定预览尺寸
     * 2.根据需求确定预览数据格式
     * 3.根据预览尺寸和数据格式计算出每一帧画面要占用的内存大小
     * 4.通过 addCallbackBuffer() 方法提前添加若干个创建好的 byte 数组对象作为缓冲对象供回调预览数据使用
     * 5.通过 setPreviewCallbackWithBuffer() 注册预览回调
     * 6.使用完缓冲对象之后，通过 addCallbackBuffer() 方法回收缓冲对象
     */

    @WorkerThread
    private void setPreviewSize2(int shortSide, int longSide) {
        Camera camera = mCamera;
        if (camera != null && shortSide != 0 && longSide != 0) {
            float aspectRatio = (float) longSide / shortSide;
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            for (Camera.Size previewSize : supportedPreviewSizes) {
                if ((float) previewSize.width / previewSize.height == aspectRatio &&
                        previewSize.height <= shortSide &&
                        previewSize.width <= longSide) {

                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                    LogUtils.d("MainActivity_log", "setPreviewSize() called with: width = " + previewSize.width + "; height = " + previewSize.height);
                    if (isPreviewFormatSupported(parameters, PREVIEW_FORMAT)) {
                        parameters.setPreviewFormat(PREVIEW_FORMAT);
                        int frameWidth = previewSize.width;
                        int frameHeight = previewSize.height;
                        int previewFormat = parameters.getPreviewFormat();
                        PixelFormat pixelFormat = new PixelFormat();
                        PixelFormat.getPixelFormatInfo(previewFormat, pixelFormat);
                        int bufferSize = (frameWidth * frameHeight * pixelFormat.bitsPerPixel) / 8;
                        camera.addCallbackBuffer(new byte[bufferSize]);
                        camera.addCallbackBuffer(new byte[bufferSize]);
                        camera.addCallbackBuffer(new byte[bufferSize]);
                        LogUtils.d("MainActivity_log", "Add three callback buffers with size: " + bufferSize);
                    }

                    camera.setParameters(parameters);
                    break;
                }
            }
        }
    }


    /**
     * 相机输出的预览画面最终都是绘制到指定的 Surface 上，
     * 这个 Surface 可以来自 SurfaceHolder 或者 SurfaceTexture，
     * 至于什么是 Surface 这里就不过多解释，大家可以自行了解。所以在开启预览之前，
     * 我们还要告诉相机把画面输出到哪个 Surface 上，Camera 支持两种方式设置预览的
     * Surface：
     * 1.通过 Camera.setPreviewDisplay() 方法设置 SurfaceHolder 给相机，通常是在你使用 SurfaceView 作为预览控件时会使用该方法。
     * 2.通过 Camera.setPreviewTexture() 方法设置 SurfaceTexture 给相机，通常是在你使用 TextureView 作为预览控件或者自己创建 SurfaceTexture 时使用该方法。
     *
     */

    /**
     * 设置预览 Surface。
     */
    @WorkerThread
    private void setPreviewSurface(SurfaceHolder previewSurface) {
        if (mCamera != null && previewSurface != null) {
            try {
                mCamera.setPreviewDisplay(previewSurface);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPreviewTexture(SurfaceTexture holder) {
        if (mCamera != null && holder != null) {
            try {
                mCamera.setPreviewTexture(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 判断指定的预览格式是否支持。
     */
    private boolean isPreviewFormatSupported(Camera.Parameters parameters, int format) {
        List<Integer> supportedPreviewFormats = parameters.getSupportedPreviewFormats();
        return supportedPreviewFormats != null && supportedPreviewFormats.contains(format);
    }


    /**
     * 开始预览。
     */
    @WorkerThread
    private void startPreview() {
        if (mCamera != null) {
            mCamera.startPreview();
            LogUtils.d("MainActivity_log", "startPreview: startPreview() called");
        }
    }

    /**
     * 停止预览。
     */
    @WorkerThread
    private void stopPreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
            LogUtils.d("MainActivity_log", "stopPreview: stopPreview() called");
        }
    }
}