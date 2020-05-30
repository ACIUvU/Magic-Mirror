package com.example.magicmirror;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.magicmirror.imageHandle.AdjustLengthenView;
import com.example.magicmirror.imageHandle.LengthenUtils;
import com.example.magicmirror.imageHandle.DistortImageView;
import com.example.magicmirror.imageMap.BitmapUtils;

/**
 * author:DingDeGao
 * time:2019-08-23-16:16
 * function: default function
 */
public class PartLengthenActivity extends AppCompatActivity {


    private AdjustLengthenView longLegView;

    private ImageView img;
    private ImageView imgResult;

    private DistortImageView smallFaceView;
    private View showPreview;
    private Button compare;
    private View showOperate;
    private Bitmap legBitmap;

    private  boolean isShowCompare = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_part_lengthen);
        longLegView = findViewById(R.id.longLegView);
        smallFaceView = findViewById(R.id.smallFaceView);
        showOperate = findViewById(R.id.showOperate);

        imgResult = findViewById(R.id.imgResult);
        img = findViewById(R.id.img);
        showPreview = findViewById(R.id.showPreview);
        smallFaceView = findViewById(R.id.smallFaceView);

        compare = findViewById(R.id.compare);
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowCompare = !isShowCompare;
                compare.setText(isShowCompare?"返回":"对比");


                loadImage();
            }
        });


        initData();
        loadImage();
    }

    private void initData() {
        //legBitmap = BitmapUtils.getBitmapByAssetsName(this, "leg.jpeg");
        legBitmap = ImageResource.getInstance().getOrig_img();
        smallFaceView.setEnableOperate(false);
        smallFaceView.setBitmap(legBitmap);
        img.setImageBitmap(legBitmap);

        smallFaceView.post(new Runnable() {
            @Override
            public void run() {
                longLegView.setLineLimit(legBitmap.getHeight() * smallFaceView.getScale());
            }
        });
    }

    private void loadImage() {

        showPreview.setVisibility(isShowCompare?View.VISIBLE:View.GONE);
        showOperate.setVisibility(!isShowCompare?View.VISIBLE:View.GONE);


        longLegView.setListener(new AdjustLengthenView.Listener() {
            @Override
            public void down() {

            }

            @Override
            public void up(Rect rect) {
                int height1 = legBitmap.getHeight();
                int height2 = smallFaceView.getHeight();

                float scale = (float) height1 /height2;
                Rect rect1 = new Rect(0,(int)(rect.top * scale + 0.5f),
                        0,(int)(rect.bottom * scale+ 0.5f));

                float strength = 0.2f;
                Bitmap bitmap = LengthenUtils.longLeg(legBitmap, rect1, strength);
                smallFaceView.setBitmap(bitmap);
                imgResult.setImageBitmap(bitmap);
            }
        });

    }
}
