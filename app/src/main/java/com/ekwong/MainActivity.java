package com.ekwong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTvCenter, mTvBottom;
    private RelativeLayout mLayout;

    private SelectImgPopWindow mSelectImgPopWindow;
    private CenterPopWindow mCenterPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mLayout = findViewById(R.id.layout);
        mTvCenter = findViewById(R.id.tv_pop_center);
        mTvBottom = findViewById(R.id.tv_pop_bottom);
        mTvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCenter();
            }
        });
        mTvBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBottom();
            }
        });
    }


    private void showDialogBottom() {
        if (mSelectImgPopWindow == null) {
            mSelectImgPopWindow = new SelectImgPopWindow(this, mLayout);
            mSelectImgPopWindow.setWindowClickListener(new SelectImgPopWindow.WindowClickListener() {
                @Override
                public void onGalleryClick() {
                    Toast.makeText(MainActivity.this, "Chose Photo", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCameraClick() {
                    Toast.makeText(MainActivity.this, "Take Photo", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelClick() {

                }
            });
        }
        mSelectImgPopWindow.show();

    }

    private void showDialogCenter() {
        if (mCenterPopWindow==null){
            mCenterPopWindow = new CenterPopWindow(this,mLayout);
        }
        mCenterPopWindow.show();
    }
}
