package com.example.magicmirror;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

public class CartoonActivity extends AppCompatActivity {

    private ImageView iv_cartoon;
    private Bitmap beauty_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("卡通");
        setContentView(R.layout.cartoon_activity);
        iv_cartoon=findViewById(R.id.iv_cartoon);
        Intent intent = getIntent();
        beauty_img = ImageResource.getInstance().getBeauty_img();
        if(beauty_img!=null){
            iv_cartoon.setImageBitmap(beauty_img);
        }
    }


    public void saveImage(View view) {
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String date = DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date());
        file=new File(file,date+".jpg");
        try {
            beauty_img.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this,"保存失败",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iv_cartoon = null;
        beauty_img = null;
    }

}
