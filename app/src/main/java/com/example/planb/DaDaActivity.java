package com.example.planb;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


public class DaDaActivity extends AppCompatActivity {


    private TextView mName, mStartTime;
    private SharedPreferences.Editor mEditor;
    private ImageView mAd;
    private int[] mColors;
    private int mIndex;

    int screenWidth;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (mIndex < mColors.length) {
                    mName.setTextColor(mColors[mIndex]);
                    mIndex++;
                } else {
                    mIndex = 0;
                }
                mHandler.sendEmptyMessageDelayed(0, 200);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_da);

        SharedPreferences sp = getSharedPreferences("businfo", Context.MODE_PRIVATE);
        mEditor = sp.edit();

        mStartTime = (TextView) findViewById(R.id.time);
        int date = Calendar.getInstance().getTime().getDate();
        int month = Calendar.getInstance().getTime().getMonth();
        mStartTime.setText("" + (month + 1) + "月" + date + "日 " + sp.getString("startTime", "08:50"));

        mName = (TextView) findViewById(R.id.name);
        mName.setText(sp.getString("centerText", "道孚"));

        mColors = new int[]{
                Color.parseColor("#8B6854"),
                Color.parseColor("#AE670B"),
                Color.parseColor("#3F7A96"),
                Color.parseColor("#008BF8"),
                Color.parseColor("#0081F2"),
                Color.parseColor("#304BB3"),
                Color.parseColor("#3E3EA1"),
                Color.parseColor("#5C4C91"),
                Color.parseColor("#7B5B78"),
                Color.parseColor("#916D50"),
                Color.parseColor("#A08721"),
                Color.parseColor("#42B4C0"),
                Color.parseColor("#25C0EB"),
                Color.parseColor("#3FA8C3"),
        };
        mIndex = 0;
        mHandler.sendEmptyMessage(0);

        mAd = (ImageView) findViewById(R.id.ad_move);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        mAd.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(mAd, "translationX", -mAd.getWidth(), screenWidth);
                animator.setDuration(5000);
                animator.setRepeatCount(-1);
                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }
}
