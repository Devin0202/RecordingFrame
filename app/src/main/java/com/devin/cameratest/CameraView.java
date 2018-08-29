package com.devin.cameratest;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.util.concurrent.locks.ReentrantLock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by li on 2017/4/8.
 */

public class CameraView extends GLSurfaceView {
    private byte[] recognizerData;

    private ReentrantLock lock = new ReentrantLock();
    private ReentrantLock qrLock = new ReentrantLock();
    private boolean isNeedQRRecognize = false;
    private Display display;


    enum State {
        DORECOGNIZED,
        QUEUE,
        NONE
    }

    private State processState = State.NONE;

    public CameraView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        MyRenderer mRenderer = new MyRenderer();
        setEGLConfigChooser(8, 8, 8, 8, 24, 8);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);

        CameraSource.Instance().openCamera(CameraSource.CAMERA_DIRECTION_BACK);
    }

    public boolean isPreviewing() {
        return CameraSource.Instance().isPreviewing();
    }

    public void setPreviewing(boolean isPreviewing) {
        CameraSource.Instance().setPreviewing(isPreviewing);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        CameraSource.Instance().setPreviewing(false);
        CameraSource.Instance().stopPreview();
        CameraSource.Instance().closeCamera();

    }

    class MyRenderer implements Renderer {
        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            RendererController.Instance().init();
        }

        @Override
        public void onSurfaceChanged(GL10 gl10, int width, int height) {
            RendererController.Instance().configScreen(width, height);
            CameraSource.Instance().startPreview();
        }

        @Override
        public void onDrawFrame(GL10 gl10) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
            RendererController.Instance().configOrientation(getOrientationType());
            RendererController.Instance().drawVideoBackground();
            GLES20.glFinish();
        }
    }

    private synchronized int getOrientationType() {

        float rotation = 0.0F;
        switch (this.display.getRotation()) {
            case 0:
                rotation = 0.0F;
                break;
            case 1:
                rotation = 90.0F;
                break;
            case 2:
                rotation = 180.0F;
                break;
            case 3:
                rotation = 270.0F;
        }
        rotation = rotation - 90;
        if (rotation == -90) {
            return 0;
        } else if (rotation == 180) {
            return 3;
        } else if (rotation == 90) {
            return 2;
        } else {
            return 1;
        }
    }
}
