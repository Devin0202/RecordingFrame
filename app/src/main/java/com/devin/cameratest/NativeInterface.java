package com.devin.cameratest;

public class NativeInterface {
    static {
        System.loadLibrary("native-lib");
    }

    public static native boolean cutNV21(byte[] src,int x,int y,int srcWidth,int srcHeight,
                                     byte[] dest,int destWidth,int destHeight);
}
