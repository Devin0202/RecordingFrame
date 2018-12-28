//
// Created by devin on 12/17/18.
//
#include "com_devin_cameratest_NativeInterface.h"
#include <string.h>

JNIEXPORT jboolean JNICALL Java_com_devin_cameratest_NativeInterface_cutNV21
  (JNIEnv * env, jclass, jbyteArray src_, jint x, jint y, jint srcWidth, jint srcHeight, \
  jbyteArray dest_, jint desWidth, jint desHeight)
{
/*****************************************************************
Function:       cutNV21
Description:    Get ROI in nv21 data
Input:          jbyteArray src_, jint x, jint y, jint srcWidth,
                jint srcHeight, jint desWidth, jint desHeight
ParaExplain:    x is the left of ROI.
                y is the top of ROI.
Output:         jbyteArray dest_
Return:         jboolean
Others:         Assure that desWidth and desHeight are even.
                Allocate the space of dest_ before invoked.
*****************************************************************/
    jbyte *Src = env->GetByteArrayElements(src_, NULL);
    jbyte *Dst = env->GetByteArrayElements(dest_, NULL);

    int nIndex = 0;
    int BPosX = x;
    int BPosY = y;

    if (0 == BPosX % 2) {
        ;
    }else{
        BPosX -= 1;
    }
    if (0 == desWidth % 2) {
        ;
    }else{
        return false;
    }
    if (0 > BPosX || srcWidth < BPosX + desWidth) {
        return false;
    }else{
        ;
    }

    if (0 == BPosY % 2) {
        ;
    }else{
        BPosY -= 1;
    }
    if (0 == desHeight % 2) {
        ;
    }else{
        return false;
    }
    if (0 > BPosY || srcHeight < BPosY + desHeight) {
        return false;
    }else{
        ;
    }

    for (int i = 0; i < desHeight; i++) {
        memcpy(Dst + desWidth * i, Src + (srcWidth * BPosY) + BPosX + nIndex, desWidth);
        nIndex += (srcWidth);
    }

    nIndex = 0;
    jbyte *pUSour = Src + srcWidth * srcHeight;
    jbyte *pUDest = Dst + desWidth * desHeight;
    for (int i = 0; i < desHeight / 2; i++) {
        memcpy(pUDest + desWidth * i, pUSour + (srcWidth * BPosY / 2) + BPosX + nIndex, desWidth);
        nIndex += (srcWidth);
    }
    return true;
}