package com.example.magicmirror;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;



import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;


public class MainActivity extends AppCompatActivity {

    private ImageView iv_show;
    private Bitmap img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OpenCVLoader.initDebug();

        iv_show=findViewById(R.id.iv_show);

    }

    //拍照点击事件
    public void takeImage(View view) {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    //图库点击事件
    public void chooseImage(View view) {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }



    //卡通按钮点击事件
    public void cartoon(View view) {
        if(!isImageExsit()){
            return;
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG,80,baos);
//        DetectResponse response=null;
//        new FaceUtils().beauty_face(this,baos.toByteArray());
          new ImageUtils().cartoonImage(this,baos.toByteArray());
    }


    //图片中值滤波器按钮点击事件
    public void medianFilter(View view) {
        if(!isImageExsit()){
            return;
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG,80,baos);
//        DetectResponse response=null;
//        new FaceUtils().beauty_face(this,baos.toByteArray());
        new ImageUtils().medianFilterImage(this,baos.toByteArray());
    }

    //图片阈值化按钮点击事件
    public void adaptiveThreshold(View view) {
        if(!isImageExsit()){
            return;
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG,80,baos);
//        DetectResponse response=null;
//        new FaceUtils().beauty_face(this,baos.toByteArray());
        new ImageUtils().adaptiveThresholdImage(this,baos.toByteArray());
    }


    //图像灰度化
    public void reduceImageColors(View view) {
        if(!isImageExsit()){
            return;
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG,80,baos);
//        DetectResponse response=null;
//        new FaceUtils().beauty_face(this,baos.toByteArray());
        new ImageUtils().reduceImageColorsImage(this,baos.toByteArray());
    }


    public void partLengthen(View view){
        startActivity(new Intent(this, PartLengthenActivity.class));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bitmap img = (Bitmap) data.getExtras().get("data");
            this.img = img;
            ImageResource.getInstance().setOrig_img(img);
            iv_show.setImageBitmap(img);
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                this.img = BitmapFactory.decodeStream(cr.openInputStream(uri));
                ImageResource.getInstance().setOrig_img(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            iv_show.setImageURI(uri);
        }
        if (requestCode == 2) {

        }
    }



    //判断图片是否存在
    private Boolean isImageExsit(){
        if(img==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher_round)
                    .setCancelable(false)
                    .setMessage("请选择图片")
                    .setTitle("提示")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
            return false;
        }
        return true;
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        iv_show=null;
        img=null;
        ImageResource.getInstance().destory();
    }

}
