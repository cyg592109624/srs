package com.sunrise.srs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.sunrise.srs.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ChuHui on 2017/10/18.
 */

public class LineChat extends View {
    private float mX;
    private float mY;

    private int viewWidth;
    private int viewHeight;

    private float avgWidth;
    private float avgHeight;

    private Paint mGesturePaint = new Paint();
    private Path mPath = new Path();
    private List<Integer> data;
    private final int MIN_SIZE = 30;
    private int rankCount = 36;

    public LineChat(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChat(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGesturePaint.setAntiAlias(true);
        mGesturePaint.setStyle(Paint.Style.STROKE);
        mGesturePaint.setStrokeWidth(5);
        mGesturePaint.setColor(ContextCompat.getColor(context, R.color.factory_tabs_on));

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.lineChat);
        rankCount = ta.getInt(R.styleable.lineChat_rankCount, rankCount);
        ta.recycle();
        ta = null;
        data = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        avgHeight = (viewHeight * 0.01f * 100f / rankCount);
        calcWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mGesturePaint);
    }

    /**
     * 重新测量宽度
     */
    private void calcWidth() {
        if (data.size() <= MIN_SIZE) {
            avgWidth = (viewWidth * 0.01f * 100f / (MIN_SIZE - 1));
        } else {
            avgWidth = (viewWidth * 0.01f * 100f / (data.size() - 1));
        }
        System.out.println("data.size()  ---->" + data.size());
        System.out.println("avgWidth     ---->" + avgWidth);
    }

    private void drawData() {
        if (data.size() <= 0) {
            return;
        }
        mX = 0;

        mY = viewHeight - data.get(0) * avgHeight;
        mPath.moveTo(mX, mY);
        float x = 1.00f;
        float y = 1.00f;
        float previousX = 1.00f;
        float previousY = 1.00f;
        for (int i = 1; i < data.size(); i++) {
            x = avgWidth * i;
            y = viewHeight - data.get(i) * avgHeight;

            previousX = mX;
            previousY = mY;

            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;

            //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
            mPath.quadTo(previousX, previousY, cX, cY);

            //第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
            mX = x;
            mY = y;
        }
    }

    public void setData(List<Integer> datas) {
        data = datas;
    }

    public void reFlashView() {
        calcWidth();
        drawData();
        invalidate();
    }

    public void recycle() {
        mGesturePaint.reset();
        mGesturePaint = null;
        mPath.reset();
        mPath = null;
        data.clear();
        data = null;
    }
}
