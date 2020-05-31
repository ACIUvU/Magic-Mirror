package com.example.magicmirror;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

public class ReduceImageColorsActivity extends AppCompatActivity {

    private ImageView iv_reduceImageColors;
    private Bitmap reduceImageColors_img;
    private Bitmap colorsGray_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("图像灰度化");
        setContentView(R.layout.activity_reduce_image_colors);

        iv_reduceImageColors=findViewById(R.id.iv_reduceImageColors);
        reduceImageColors_img = ImageResource.getInstance().getReduceImageColors_img();
        if(reduceImageColors_img!=null){
            iv_reduceImageColors.setImageBitmap(reduceImageColors_img);
        }
    }


    public void colorsGray(View view) {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        reduceImageColors_img.compress(Bitmap.CompressFormat.JPEG,80,baos);
        new ImageUtils().reduceImageColorsGray(this,baos.toByteArray());

        colorsGray_img = ImageResource.getInstance().getColorsGray_img();
        if(colorsGray_img!=null){
            iv_reduceImageColors.setImageBitmap(colorsGray_img);
        }
    }


    public void saveImage(View view) {
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String date = DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date());
        file=new File(file,date+".jpg");
        try {
            reduceImageColors_img.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this,"保存失败",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iv_reduceImageColors = null;
        reduceImageColors_img = null;
    }

}
