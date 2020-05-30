package com.example.magicmirror;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
//import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.magicmirror.R;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static org.opencv.core.Core.LUT;
import static org.opencv.core.CvType.CV_8UC1;



public class ImageUtils {

//    public void cartoonImage(View view) {

    public void cartoonImage(final AppCompatActivity activity, byte[] img) {
        //关于options 没问题
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true; // Leaving it to true enlarges the decoded image size.
        Bitmap original = BitmapFactory.decodeByteArray(img,0,img.length,options);
        //here！！！   part3！！
        //Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.part3, options);




        //Mat img1 = new Mat(original.getHeight(), original.getHeight(), CvType.CV_8UC4);
        Mat img1 = new Mat();
                                                                                                                                                                                             Utils.bitmapToMat(original, img1);

        Imgproc.cvtColor(img1, img1, Imgproc.COLOR_BGRA2BGR);

        Mat result = cartoon(img1, 80, 15, 10);

        Bitmap imgBitmap = Bitmap.createBitmap(result.cols(), result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, imgBitmap);




        //test
        //Bitmap imgBitmap = BitmapFactory.decodeByteArray(img,0,img.length, options);
        //ImageResource.getInstance().setCartoon_img(original);

        //以下没问题
        ImageResource.getInstance().setCartoon_img(imgBitmap);
        Intent intent = new Intent();
        intent.setClass(activity,CartoonActivity.class);
        activity.startActivityForResult(intent,3);

        //ImageView imageView = findViewById(R.id.opencvImg);
        //imageView.setImageBitmap(imgBitmap);
        //saveBitmap(imgBitmap, "cartoon");
    }



    /*
    //对图片进行美颜和美白
    public void beauty_face(final AppCompatActivity activity, byte[] img){
        HashMap<String, String> map = new HashMap<>();
        faceApi.beautify(map, img, new IFacePPCallBack<BeautyResponse>() {
            @Override
            public void onSuccess(BeautyResponse beautyResponse) {
                String error = beautyResponse.getError_message();
                if(error!=null){
                    Toast.makeText(activity,error,Toast.LENGTH_LONG).show();
                    Log.e(TAG,error);
                    return;
                }
                String result = beautyResponse.getResult();
                byte[] decode = Base64.decode(result, Base64.DEFAULT);
                ImageResource.getInstance().setBeauty_img(BitmapFactory.decodeByteArray(decode,0,decode.length));
                Intent intent = new Intent();
                intent.setClass(activity,BeautyActivity.class);
                activity.startActivityForResult(intent,3);
            }

            @Override
            public void onFailed(String s) {
                Log.e(TAG, s);
            }
        });
    }
    */


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









/*
    public void cartoonImage(View view) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false; // Leaving it to true enlarges the decoded image size.
        //here！！！   part3！！
        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.part3, options);

        Mat img1 = new Mat();
        Utils.bitmapToMat(original, img1);
        Imgproc.cvtColor(img1, img1, Imgproc.COLOR_BGRA2BGR);

        Mat result = cartoon(img1, 80, 15, 10);

        Bitmap imgBitmap = Bitmap.createBitmap(result.cols(), result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, imgBitmap);

        ImageView imageView = findViewById(R.id.opencvImg);
        imageView.setImageBitmap(imgBitmap);
        saveBitmap(imgBitmap, "cartoon");
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

 */