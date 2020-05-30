package com.example.magicmirror;

import android.graphics.Bitmap;


//图片资源类
public class ImageResource {
    private Bitmap orig_img;//原始图片
    //private Bitmap beauty_img;//美颜后的图片
    private Bitmap cartoon_img;//美颜后的图片
    private Bitmap medianFilter_img;
    private Bitmap adaptiveThreshold_img;
    private Bitmap reduceImageColors_img;
    private Bitmap colorsGray_img;
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

    //public  Bitmap getBeauty_img() {
//        return beauty_img;
 //   }

    public  Bitmap getCartoon_img() {
        return cartoon_img;
    }

//    public void setBeauty_img(Bitmap beauty_img) {
//        this.beauty_img = beauty_img;
//    }

    public  Bitmap getMedianFilter_img() {
        return medianFilter_img;
    }
    public  Bitmap getAdaptiveThreshold_img() {
        return adaptiveThreshold_img;
    }
    public  Bitmap getReduceImageColors_img() {
        return reduceImageColors_img;
    }
    public  Bitmap getColorsGray_img(){
        return colorsGray_img;
    }






    public void setCartoon_img(Bitmap cartoon_img) {
        this.cartoon_img = cartoon_img;
    }

    public void setMedianFilter_img(Bitmap medianFilter_img) { this.medianFilter_img = medianFilter_img; }
    public void setAdaptiveThreshold_img(Bitmap adaptiveThreshold_img) { this.adaptiveThreshold_img = adaptiveThreshold_img; }
    public void setReduceImageColors_img(Bitmap reduceImageColors_img) { this.reduceImageColors_img = reduceImageColors_img; }
    public void setColorsGray_img(Bitmap colorsGray_img){
        this. colorsGray_img =  colorsGray_img;
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
        cartoon_img=null;
        medianFilter_img=null;
        adaptiveThreshold_img=null;
        reduceImageColors_img=null;
        colorsGray_img=null;
 //       detectResponse=null;
    }
}
