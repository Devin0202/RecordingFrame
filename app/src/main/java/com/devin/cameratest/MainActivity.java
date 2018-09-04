package com.devin.cameratest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NewFrameListener{
    private final String TAG = this.getClass().toString() + "-DY";
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-HH-mm");

    private FrameLayout view;
    private CameraView cameraView;

    private TextView txtview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new FrameLayout(this);
        cameraView = new CameraView(this);
        view.addView(cameraView);

        FrameLayout.LayoutParams tparams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        txtview = new TextView(this);
        txtview.setText("Welcome~");
        txtview.setTextColor(Color.parseColor("#FF0000"));
        txtview.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
        view.addView(txtview, tparams);

        setContentView(view);
        CameraSource.Instance().setNewFrameListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "" + "finger up");

                CameraSource.Instance().recording = !CameraSource.Instance().recording;
                if (CameraSource.Instance().recording){
                    CameraSource.Instance().folderNeedChange = true;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
                    Date now = new Date();
                    CameraSource.Instance().renewDir = dateFormat.format(now);
                    txtview.setText(CameraSource.Instance().renewDir);
                }else{
                    CameraSource.Instance().folderNeedChange = false;
                    txtview.setText("Stop recording!");
                }
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onNewFrame(byte[] data, int preWidth, int preHeight) {
        RendererController.Instance().onFrame(data, preWidth, preHeight);
    }
}
