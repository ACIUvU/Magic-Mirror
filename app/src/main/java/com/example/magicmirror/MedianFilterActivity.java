package com.example.magicmirror;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

public class MedianFilterActivity extends AppCompatActivity {

    private ImageView iv_medianFilter;
    private Bitmap medianFilter_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("中值滤波器");
        setContentView(R.layout.activity_median_filter);
        iv_medianFilter=findViewById(R.id.iv_medianFilter);
        //本来没注释
        //Intent intent = getIntent();
        medianFilter_img = ImageResource.getInstance().getMedianFilter_img();
        if(medianFilter_img!=null){
            iv_medianFilter.setImageBitmap(medianFilter_img);
        }
    }




    public void saveImage(View view) {
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String date = DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date());
        file=new File(file,date+".jpg");
        try {
            medianFilter_img.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this,"保存失败",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iv_medianFilter = null;
        medianFilter_img = null;
    }

}
