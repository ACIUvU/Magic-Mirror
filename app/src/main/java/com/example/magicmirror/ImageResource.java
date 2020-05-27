package com.example.magicmirror;

import android.graphics.Bitmap;


//图片资源类
public class ImageResource {
    private Bitmap orig_img;//原始图片
    private Bitmap beauty_img;//美颜后的图片
//    private DetectResponse detectResponse;//人脸分析结果

    private static ImageResource instance;

    private ImageResource(){}

    public static ImageResource getInstance(){
        if(instance==null){
            instance=new ImageResource();
        }
        return instance;
    }

    public  void setOrig_img(Bitmap orig_img) {
        this.orig_img = orig_img;
    }

    public  Bitmap getOrig_img() {
        return orig_img;
    }

    public  Bitmap getBeauty_img() {
        return beauty_img;
    }

    public void setBeauty_img(Bitmap beauty_img) {
        this.beauty_img = beauty_img;
    }

/*    public DetectResponse getDetectResponse() {
        return detectResponse;
    }

    public void setDetectResponse(DetectResponse detectResponse) {
        this.detectResponse = detectResponse;
    }

 */

    public void destory(){
        orig_img=null;
        beauty_img=null;
 //       detectResponse=null;
    }
}
