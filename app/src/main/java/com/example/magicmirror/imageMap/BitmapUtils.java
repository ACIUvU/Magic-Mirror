package com.example.magicmirror.imageMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.IOException;


public class BitmapUtils {

    public static Bitmap getBitmap_(Context context, String name){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inMutable = true;

        try {
            return BitmapFactory.decodeStream(context.getAssets().open(name),new Rect(),options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmap_RGB(Context context, String name){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inMutable = true;

        try {
            return BitmapFactory.decodeStream(context.getAssets().open(name),new Rect(),options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
