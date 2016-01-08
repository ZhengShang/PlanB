package com.example.planb;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by ZhengShang on 11/18/2015.
 */

public class DaDaSetDialog extends Dialog {

    private Context context;
    private TextView mStartTime;
    private EditText mBusPlate;
    private ImageView mCenterAd, mBotAd;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private EditText mCenterText;

    public DaDaSetDialog(Context context) {
        super(context, R.style.AppTheme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {

        mSharedPreferences = getContext().getSharedPreferences("businfo", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dadasetdialog, null);
        setContentView(view);

        mBusPlate = (EditText) view.findViewById(R.id.plate);
        mStartTime = (TextView) findViewById(R.id.startTime);
        mCenterText = (EditText) view.findViewById(R.id.centerText);
        mCenterAd = (ImageView) findViewById(R.id.centerAd);
        mBotAd = (ImageView) findViewById(R.id.botAd);


        mBusPlate.setText(mSharedPreferences.getString("plate", "0968"));
        mStartTime.setText(mSharedPreferences.getString("startTime", "08:50"));
        mCenterText.setText(mSharedPreferences.getString("centerText", "道孚"));


        findViewById(R.id.startTimePicker).setOnClickListener(new clickListener());
        findViewById(R.id.setConfirm).setOnClickListener(new clickListener());
        findViewById(R.id.setCancel).setOnClickListener(new clickListener());
        mBotAd.setOnClickListener(new clickListener());
        mCenterAd.setOnClickListener(new clickListener());


        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.8
        lp.height = (int) (d.heightPixels * 0.7);
        dialogWindow.setAttributes(lp);
    }


    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.startTimePicker:
                    new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if (hourOfDay < 10)
                                mStartTime.setText("0" + hourOfDay + ":" + minute);
                            else
                                mStartTime.setText(hourOfDay + ":" + minute);

                        }
                    }, Calendar.getInstance().getTime().getHours(), Calendar.getInstance().getTime().getMinutes(), true).show();

                    break;
                case R.id.centerAd:
                    setCenterAd();
                    break;
                case R.id.botAd:

                    break;
                case R.id.setConfirm:
                    doConfirm();
                    break;
                case R.id.setCancel:
                    onBackPressed();
                    break;
            }
        }
    }


    private void doConfirm() {

        if (mBusPlate.getText().length() == 4) {
            mEditor.putString("plate", mBusPlate.getText().toString());
            mEditor.putString("startTime", mStartTime.getText().toString());
            mEditor.putString("centerText", mCenterText.getText().toString());
            mEditor.apply();
        }


        this.dismiss();
        getContext().startActivity(new Intent(context, DaDaActivity.class));

    }

    private void setCenterAd() {


    }
}
