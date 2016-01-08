package com.example.planb;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * @author xujiahui
 * @since 2015/10/27
 */
public class LicenseView extends View {

    private String mText;
    private float mTextSize;
    private int mColor;
    private int[] mColors;
    private int mIndex;
    private float mBorderWidth;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (mIndex < mColors.length) {
                    mColor = mColors[mIndex];
                    mIndex++;
                } else {
                    mIndex = 0;
                }
                invalidate();
            }
            super.handleMessage(msg);
        }
    };

    public LicenseView(Context context) {
        super(context);
    }

    public LicenseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LicenseView);
        mColor = typedArray.getColor(R.styleable.LicenseView_backgroundColor, 0);
        mText = getContext().getSharedPreferences("businfo", Context.MODE_PRIVATE).getString("plate", "0968");
        mBorderWidth = 5;
        mTextSize = DisplayUtil.sp2px(getContext(), 45);
        mColors = new int[]{
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
                Color.parseColor("#8B6854"),
        };
        mIndex = 0;
        typedArray.recycle();
    }

    public LicenseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 外边的白色边框
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), 10, 10, paint);

        // 内边的背景
        paint.setColor(mColor);
        canvas.drawRoundRect(new RectF(mBorderWidth, mBorderWidth, getWidth() - mBorderWidth, getHeight() - mBorderWidth),
                10, 10, paint);

        // 中间的文字
        paint.setColor(Color.WHITE);
        paint.setTextSize(mTextSize);
        paint.setTextAlign(Paint.Align.CENTER);
//		paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setFakeBoldText(true);
        canvas.drawText("粤BM" + mText, 0, mText.length() + 3, getWidth() / 2, getHeight() / 2 + mTextSize / 3, paint);

        mHandler.sendEmptyMessageDelayed(0, 200);
    }
}
