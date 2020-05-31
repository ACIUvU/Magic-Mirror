package com.example.magicmirror;

import android.graphics.Bitmap;
import android.os.Build;
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
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class CartoonActivity extends AppCompatActivity {

    private ImageView iv_cartoon;
    private Bitmap cartoon_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("卡通");
        //相关联的xml
        setContentView(R.layout.cartoon_activity);

        iv_cartoon=findViewById(R.id.iv_cartoon);
        cartoon_img = ImageResource.getInstance().getCartoon_img();

        if(cartoon_img!=null){
            iv_cartoon.setImageBitmap(cartoon_img);
        }
    }

    /*
    public void saveImage(View view) {
        new ImageUtils().saveImageForAll(cartoon_img);
    }
*/





    //存在问题
    public void saveImage(View view) throws IOException {
        File file1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //String date = DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date());
        //file=new File(file,date+".jpg");
        //Toast.makeText(this,Environment.DIRECTORY_PICTURES,Toast.LENGTH_LONG).show();


        String date = DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date());

        //Toast.makeText(this,date,Toast.LENGTH_LONG).show();

        Random random = new Random();
        String fileName2 = String.valueOf(random.nextInt(Integer.MAX_VALUE));

        //Toast.makeText(this,fileName2,Toast.LENGTH_LONG).show();
        File file=new File(file1,fileName2+".jpg");

        // File file=new File(file1,date+".jpg");

      /*  if(file.exists()){
            file.delete();
        }
        if(!file.exists()){
            file.createNewFile();//重点在这里
        }

       */

        Toast.makeText(this,file.getAbsolutePath(),Toast.LENGTH_LONG).show();

        try {
            FileOutputStream out = new FileOutputStream(file);
            cartoon_img.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
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
        cartoon_img = null;
    }

}
