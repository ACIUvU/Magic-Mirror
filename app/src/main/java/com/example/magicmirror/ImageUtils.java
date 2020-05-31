package com.example.magicmirror;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;



import java.util.ArrayList;
import java.util.List;


import static org.opencv.core.Core.LUT;
import static org.opencv.core.CvType.CV_8UC1;



public class ImageUtils {

    public void cartoonImage(final AppCompatActivity activity, byte[] img) {
        //关于options inScaled为真会扩大解码图像的大小，具体看文档
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inScaled = true; // 保留为真会扩大解码图像的大小。
        //Bitmap original = BitmapFactory.decodeByteArray(img,0,img.length,options);
        Bitmap original = BitmapFactory.decodeByteArray(img,0,img.length);

        //将Bigmap图像矩阵化处理
        Mat img1 = new Mat();
        //Mat img1 = new Mat(original.getHeight(), original.getHeight(), CvType.CV_8UC4);
        Utils.bitmapToMat(original, img1);

        //转换色彩空间cvtColor(source, destination, Color_Conversion_Code)
        //COLOR_BGRA2BGR：从RGB或BGR图像中删除Alpha通道
        Imgproc.cvtColor(img1, img1, Imgproc.COLOR_BGRA2BGR);

        //卡通效果处理过程
        Mat result = cartoon(img1, 40, 15, 10);

        //创建一个Bitmap   ARGB_8888：每个像素存储在4个字节上.
        Bitmap imgBitmap = Bitmap.createBitmap(result.cols(), result.rows(), Bitmap.Config.ARGB_8888);
        //将矩阵转换为Bitmap
        Utils.matToBitmap(result, imgBitmap);


        //设置图片资源里的Cartoon_img
        ImageResource.getInstance().setCartoon_img(imgBitmap);
        //开启CartoonActivity
        Intent intent = new Intent();
        intent.setClass(activity,CartoonActivity.class);
        activity.startActivityForResult(intent,3);
    }

    //如下三种操作与Cartoon过程类似

    //中值滤波器（朦胧质感）
    public void medianFilterImage(final AppCompatActivity activity, byte[] img) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap original = BitmapFactory.decodeByteArray(img,0,img.length,options);

        Mat img1 = new Mat();
        Utils.bitmapToMat(original, img1);
        Mat medianFilter = new Mat();
        Imgproc.cvtColor(img1, medianFilter, Imgproc.COLOR_BGR2GRAY);

        //图像的中心元素将替换为内核区域中所有像素的中值，ksize为内核区域
        Imgproc.medianBlur(medianFilter, medianFilter, 15);

        Bitmap imgBitmap = Bitmap.createBitmap(medianFilter.cols(), medianFilter.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(medianFilter, imgBitmap);

        ImageResource.getInstance().setMedianFilter_img(imgBitmap);
        Intent intent = new Intent();
        intent.setClass(activity,MedianFilterActivity.class);
        activity.startActivityForResult(intent,3);
    }


    //图像阈值化（素描轮廓）
    public void adaptiveThresholdImage(final AppCompatActivity activity, byte[] img) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap original = BitmapFactory.decodeByteArray(img,0,img.length,options);

        Mat adaptiveTh = new Mat();
        Utils.bitmapToMat(original, adaptiveTh);
        Imgproc.cvtColor(adaptiveTh, adaptiveTh, Imgproc.COLOR_BGR2GRAY);

        //图像的中心元素将替换为内核区域中所有像素的中值，ksize为内核区域
        Imgproc.medianBlur(adaptiveTh, adaptiveTh, 15);

        Imgproc.adaptiveThreshold(adaptiveTh, adaptiveTh, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 9, 2);

        Bitmap imgBitmap = Bitmap.createBitmap(adaptiveTh.cols(), adaptiveTh.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(adaptiveTh, imgBitmap);

        ImageResource.getInstance().setAdaptiveThreshold_img(imgBitmap);
        Intent intent = new Intent();
        intent.setClass(activity,AdaptiveThresholdActivity.class);
        activity.startActivityForResult(intent,3);
    }

    //图像灰度化
    public void reduceImageColorsImage(final AppCompatActivity activity, byte[] img){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap original = BitmapFactory.decodeByteArray(img,0,img.length,options);

        Mat img1 = new Mat();
        Utils.bitmapToMat(original, img1);

        //无需再做其他opencv操作，直接矩阵运算
        Mat result = reduceColors(img1, 180, 10, 5);

        Bitmap imgBitmap = Bitmap.createBitmap(result.cols(), result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, imgBitmap);

        ImageResource.getInstance().setReduceImageColors_img(imgBitmap);
        Intent intent = new Intent();
        intent.setClass(activity,ReduceImageColorsActivity.class);
        activity.startActivityForResult(intent,3);
    }


    //图像完全灰度化
    public void reduceImageColorsGray(final AppCompatActivity activity, byte[] img){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap original = BitmapFactory.decodeByteArray(img,0,img.length,options);

        Mat img1 = new Mat();
        Utils.bitmapToMat(original, img1);

        Imgproc.cvtColor(img1, img1, Imgproc.COLOR_BGR2GRAY);
        Mat result = reduceColorsGray(img1, 5);

        Bitmap imgBitmap = Bitmap.createBitmap(result.cols(), result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, imgBitmap);

        ImageResource.getInstance().setColorsGray_img(imgBitmap);
        //无需与其他Activity互动
    }







    Mat cartoon(Mat img, int numRed, int numGreen, int numBlue) {
        Mat reducedColorImage = reduceColors(img, numRed, numGreen, numBlue);

        Mat result = new Mat();
        Imgproc.cvtColor(img, result, Imgproc.COLOR_BGR2GRAY);
        Imgproc.medianBlur(result, result, 15);

        Imgproc.adaptiveThreshold(result, result, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 2);

        Imgproc.cvtColor(result, result, Imgproc.COLOR_GRAY2BGR);

        Log.d("PPP", result.height() + " " + result.width() + " " + reducedColorImage.type() + " " + result.channels());
        Log.d("PPP", reducedColorImage.height() + " " + reducedColorImage.width() + " " + reducedColorImage.type() + " " + reducedColorImage.channels());

        Core.bitwise_and(reducedColorImage, result, result);

        return result;
    }

    Mat reduceColors(Mat img, int numRed, int numGreen, int numBlue) {
        Mat redLUT = createLUT(numRed);
        Mat greenLUT = createLUT(numGreen);
        Mat blueLUT = createLUT(numBlue);

        List<Mat> BGR = new ArrayList<>(3);
        Core.split(img, BGR); // splits the image into its channels in the List of Mat arrays.

        LUT(BGR.get(0), blueLUT, BGR.get(0));
        LUT(BGR.get(1), greenLUT, BGR.get(1));
        LUT(BGR.get(2), redLUT, BGR.get(2));

        Core.merge(BGR, img);

        return img;

    }

    Mat reduceColorsGray(Mat img, int numColors) {
        Mat LUT = createLUT(numColors);

        LUT(img, LUT, img);

        return img;
    }


    Mat createLUT(int numColors) {
        // When numColors=1 the LUT will only have 1 color which is black.
        if (numColors < 0 || numColors > 256) {
            System.out.println("Invalid Number of Colors. It must be between 0 and 256 inclusive.");
            return null;
        }

        Mat lookupTable = Mat.zeros(new Size(1, 256), CV_8UC1);

        int startIdx = 0;
        for (int x = 0; x < 256; x += 256.0 / numColors) {
            lookupTable.put(x, 0, x);

            for (int y = startIdx; y < x; y++) {
                if (lookupTable.get(y, 0)[0] == 0) {
                    lookupTable.put(y, 0, lookupTable.get(x, 0));
                }
            }
            startIdx = x;
        }
        return lookupTable;
    }



}




