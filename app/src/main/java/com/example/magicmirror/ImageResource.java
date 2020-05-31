package com.example.magicmirror;

import android.graphics.Bitmap;


//图片资源类
public class ImageResource {
    private Bitmap orig_img;//初始未处理图片
    private Bitmap cartoon_img;//卡通图片
    private Bitmap medianFilter_img;//中值滤波器图像
    private Bitmap adaptiveThreshold_img;//图像阈值化图像
    private Bitmap reduceImageColors_img;//减少颜色
    private Bitmap colorsGray_img;//完全灰度

    private static ImageResource instance;//实例化

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


    //返回各种图片
    public  Bitmap getCartoon_img() {
        return cartoon_img;
    }
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





    //设置各种图片
    public void setCartoon_img(Bitmap cartoon_img) {
        this.cartoon_img = cartoon_img;
    }
    public void setMedianFilter_img(Bitmap medianFilter_img) { this.medianFilter_img = medianFilter_img; }
    public void setAdaptiveThreshold_img(Bitmap adaptiveThreshold_img) { this.adaptiveThreshold_img = adaptiveThreshold_img; }
    public void setReduceImageColors_img(Bitmap reduceImageColors_img) { this.reduceImageColors_img = reduceImageColors_img; }
    public void setColorsGray_img(Bitmap colorsGray_img){
        this. colorsGray_img =  colorsGray_img;
    }




    public void destory(){
        orig_img=null;
        cartoon_img=null;
        medianFilter_img=null;
        adaptiveThreshold_img=null;
        reduceImageColors_img=null;
        colorsGray_img=null;
    }
}
