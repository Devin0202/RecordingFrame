package com.devin.cameratest;

public interface NewFrameListener {
    void onNewFrame(byte[] data,int preWidth,int preHeight);
}
