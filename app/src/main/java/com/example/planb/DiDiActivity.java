package com.example.planb;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class DiDiActivity extends Activity {
    public TextView mDestinationText, mDateText;
    private Timer mTimer;
    private int red = 5, green = 5, blue = 5, op = 5;
    private int type = 1;
    private GradientDrawable gd;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                gd.setColor(Color.rgb(red, green, blue));
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di_di);

        mDestinationText = (TextView) findViewById(R.id.label);
        gd = (GradientDrawable) mDestinationText.getBackground();

        mDateText = (TextView) findViewById(R.id.date_text);

        int date = Calendar.getInstance().getTime().getDate();
        int month = Calendar.getInstance().getTime().getMonth();

        TextView ticketDate = (TextView) findViewById(R.id.ticketDate);

        ticketDate.setText("" + (month + 1) + "月" + date + "日");
        mDateText.setText("" + (month + 1) + "月" + date + "日" + " 18:15");


        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {


                while (type != -1) {
                    switch (type) {
                        case 1:
                            if (green > 0 || blue > 0) {
                                green -= op;
                                blue -= op;
                            }
                            red += op;
                            if (red == 240) type = 2;
                            break;
                        case 2:
                            red -= op;
                            green += op;
                            if (green == 240) type = 3;
                            break;
                        case 3:
                            green -= op;
                            blue += op;
                            if (blue == 240) type = 4;
                            break;
                        case 4:
                            blue -= op;
                            red += op;
                            green += op;
                            if (red == 240 && green == 240) type = 5;
                            break;
                        case 5:
                            green -= op;
                            blue += op;
                            if (red == 240 && blue == 240) type = 6;
                            break;
                        case 6:
                            red -= op;
                            green += op;
                            if (green == 240 && blue == 240) type = 1;
                            break;
                        default:
                            type = 1;
                            break;
                    }


                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        handler.removeMessages(1);
        type = -1;
        super.onDestroy();
    }
}

