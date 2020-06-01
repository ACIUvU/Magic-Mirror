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


public class PartLengthenActivity extends AppCompatActivity {

    //准备操作的图
    private Bitmap LengthenBitmap;

    //操作前后两个按钮
    private ImageView img;
    private ImageView imgResult;

    //自定义视图（进行局部延长的操作视图）
    private AdjustLengthenView adjustlengthenView;
    private DistortImageView distortimageView;

    //用作对比操作前后
    private View showPreview;
    private View showOperate;

    //右上角对比按钮
    private Button compare;
    private  boolean isShowCompare = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("局部延长");
        setContentView(R.layout.activity_part_lengthen);

        img = findViewById(R.id.img);
        imgResult = findViewById(R.id.imgResult);
        adjustlengthenView = findViewById(R.id.adjustlengthenView);
        distortimageView = findViewById(R.id.distortimageView);
        showOperate = findViewById(R.id.showOperate);
        showPreview = findViewById(R.id.showPreview);

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
        //加载图片
        LengthenBitmap = ImageResource.getInstance().getOrig_img();
        img.setImageBitmap(LengthenBitmap);

        distortimageView.setEnableOperate(false);
        distortimageView.setBitmap(LengthenBitmap);


        //将Runnable添加到消息队列中；runnable将在用户界面线程上运行。
        //public boolean post(Runnable action)
        distortimageView.post(new Runnable() {
            @Override
            public void run() {
                adjustlengthenView.setLineLimit(LengthenBitmap.getHeight() * distortimageView.getScale());
            }
        });
    }

    private void loadImage() {

        showPreview.setVisibility(isShowCompare?View.VISIBLE:View.GONE);
        showOperate.setVisibility(!isShowCompare?View.VISIBLE:View.GONE);


        adjustlengthenView.setListener(new AdjustLengthenView.Listener() {
            @Override
            public void down() {

            }

            @Override
            public void up(Rect rect) {
                int height1 = LengthenBitmap.getHeight();
                int height2 = distortimageView.getHeight();

                float scale = (float) height1 /height2;
                Rect rect1 = new Rect(0,(int)(rect.top * scale + 0.5f),
                        0,(int)(rect.bottom * scale+ 0.5f));

                float strength = 0.2f;
                Bitmap bitmap = LengthenUtils.longLeg(LengthenBitmap, rect1, strength);
                distortimageView.setBitmap(bitmap);
                imgResult.setImageBitmap(bitmap);
            }
        });

    }
}
