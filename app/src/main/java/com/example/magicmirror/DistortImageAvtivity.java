package com.example.magicmirror;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.magicmirror.imageHandle.DistortImageView;


public class DistortImageAvtivity extends AppCompatActivity {

    private ImageView img;
    private ImageView imgResult;

    private DistortImageView smallFaceView;
    private View showPreview;
    private Button compare;

    private  boolean isShowCompare = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("对点偏移");
        setContentView(R.layout.activity_distort_image);



        imgResult = findViewById(R.id.imgResult);
        img = findViewById(R.id.img);
        showPreview = findViewById(R.id.showPreview);
        smallFaceView = findViewById(R.id.smallFaceView);





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


        smallFaceView.setBitmap(ImageResource.getInstance().getOrig_img());
        loadImage();
    }

    private void loadImage() {

        showPreview.setVisibility(isShowCompare ?View.VISIBLE:View.GONE);
        smallFaceView.setVisibility(!isShowCompare ?View.VISIBLE:View.GONE);

        if(isShowCompare){
            img.setImageBitmap(ImageResource.getInstance().getOrig_img());
            imgResult.setImageBitmap(smallFaceView.getBitmap());
        }


//        img.setImageBitmap(CommonShareBitmap.originBitmap);
//
//        String faceJson = FacePoint.getFaceJson(this,"face_point1.json");
//
//        Bitmap bitmap = SmallFaceUtils.smallFaceMesh(CommonShareBitmap.originBitmap,
//                FacePoint.getLeftFacePoint(faceJson),
//                FacePoint.getRightFacePoint(faceJson),
//                FacePoint.getCenterPoint(faceJson), 5);
//
//
//        imgResult.setImageBitmap(bitmap);

    }
}
