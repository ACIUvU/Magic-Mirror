package com.example.magicmirror;

import androidx.appcompat.app.AppCompatActivity;

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

public class AdaptiveThresholdActivity extends AppCompatActivity {

    private ImageView iv_adaptiveThreshold;
    private Bitmap adaptiveThreshold_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("图像阈值化");
        setContentView(R.layout.activity_adaptive_threshold);

        iv_adaptiveThreshold=findViewById(R.id.iv_adaptiveThreshold);
        adaptiveThreshold_img = ImageResource.getInstance().getAdaptiveThreshold_img();
        if(adaptiveThreshold_img!=null){
            iv_adaptiveThreshold.setImageBitmap(adaptiveThreshold_img);
        }
    }




    public void saveImage(View view) {
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String date = DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date());
        file=new File(file,date+".jpg");
        try {
            adaptiveThreshold_img.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this,"保存失败",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iv_adaptiveThreshold = null;
        adaptiveThreshold_img = null;
    }
}
