package com.sunrise.srs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.bean.Level;
import com.sunrise.srs.bean.LevelColumn;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用该view时必须与背景图等比例缩放
 */
public class LevelView extends View {
    private Paint mPaint;
    private int viewHeight;
    private int viewWidth;

    private float toX;
    private float toY;

    /**
     * 柱子的高度
     */
    private float maxTall = 0;


    /**
     * 柱子的上边距
     */
    private float topSpace = 0;

    /**
     * 柱子的下边距
     */
    private float bottomSpace = 0;

    /**
     * 柱子的左边距
     */
    private float leftSpace = 0;
    /**
     * 柱子的右边距
     */
    private float rightSpace = 0;

    /**
     * 柱子的宽度
     */
    private float columnWidth = 0;
    /**
     * 柱子的间隔
     */
    private float columnMargin = 0;

    /**
     * 柱子低部位置同时也是柱子的起点
     */
    private float columnStartArea = 0;

    /**
     * 每一段的高度 一共36段
     */
    private float levelHeight = 0;

    /**
     * 一共36阶
     */
    private final int levelCount = Constant.LEVEL_MAX;

    /**
     * 时间将分为30份
     */
    private final int columnCount = Constant.LEVEL_TIME_AVG;

    /**
     * 柱状数组
     */
    private LevelColumn[] levelColumns;

    private boolean slideEnable = true;


    /**
     * 浮标
     */
    private Bitmap buoyBitmap;

    private float buoyBitmapWidth, buoyBitmapHeight;


    /**
     * 浮标所在Level
     */
    private int tgLevel = 0;

    /**
     * 浮标位置
     */
    private float buoyLeft;

    public LevelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.factory_tabs_on));
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            mPaint.setTypeface(TextUtils.Microsoft(context));
        } else {
            mPaint.setTypeface(TextUtils.Arial(context));
        }

        levelColumns = new LevelColumn[columnCount];

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.levelView);
        columnMargin = ta.getFloat(R.styleable.levelView_columnMargin, 2f);
        slideEnable = ta.getBoolean(R.styleable.levelView_slideEnable, true);

        topSpace = ta.getFloat(R.styleable.levelView_topSpace, 2f);
        rightSpace = ta.getFloat(R.styleable.levelView_rightSpace, 2f);
        bottomSpace = ta.getFloat(R.styleable.levelView_bottomSpace, 2f);
        leftSpace = ta.getFloat(R.styleable.levelView_leftSpace, 2f);

        int opWidth = ta.getInt(R.styleable.levelView_buoyBitmapWidth, -1);
        int opHeight = ta.getInt(R.styleable.levelView_buoyBitmapHeight, -1);

        ta.recycle();
        ta = null;
        if (opWidth > 1 && opHeight > 1) {
            setBuoyBitmap(opWidth, opHeight);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        calcLength();
    }

    /**
     * 重新测量布局数据
     */
    public void calcLength() {
        columnWidth = Math.round(((viewWidth - ((columnMargin * 29) + leftSpace + rightSpace)) / 30) * 1000f) / 1000f;
        columnStartArea = Math.round((viewHeight - bottomSpace) * 1000f) / 1000f;
        maxTall = Math.round((columnStartArea - topSpace) * 1000f) / 1000f;
        levelHeight = Math.round((maxTall / levelCount) * 1000f) / 1000f;

        mPaint.setTextSize(bottomSpace * 0.8f);
    }

    public void setBuoyBitmap(int optionsWidth, int optionsHeight) {
        if (optionsWidth < 0 | optionsHeight < 0) {
            return;
        }
        buoyBitmapWidth = optionsWidth / 3;
        buoyBitmapHeight = optionsHeight / 3;

        buoyBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_sportmode_profile_dot_1).copy(Bitmap.Config.ARGB_8888, false);
        //最后一个参数的作用是 是否裁剪原图
        buoyBitmap = Bitmap.createScaledBitmap(buoyBitmap, optionsWidth, optionsHeight, false);
    }

    /**
     * 设置浮标位置
     *
     * @param tgLevel
     */
    public void setTgLevel(int tgLevel) {
        if (tgLevel > -1 && tgLevel < columnCount) {
            this.tgLevel = tgLevel;
        }
        calcBuoyLeft();
    }

    /**
     * 将浮标移动到指定位置
     */
    private void calcBuoyLeft() {
        buoyLeft = (columnMargin + columnWidth) * tgLevel;
    }

    public void reFlashView() {
        invalidate();
    }

    /**
     * 获取每阶段的段数
     *
     * @return
     */
    public List<Level> getLevelList() {
        List<Level> list = new ArrayList<>();
        for (LevelColumn column : levelColumns) {
            Level level = new Level();
            if (column != null) {
                level.setLevel(column.getLevel());
            } else {
                level.setLevel(0);
            }
            list.add(level);
        }
        return list;
    }

    /**
     * 将段数写入数组
     *
     * @param levelList
     */
    public void setLevelList(List<Level> levelList) {
        if (levelList.size() < columnCount) {
            return;
        }
        int start = (levelList.size() / columnCount) - 1;
        start = start * 30;
        for (int i = start; i < levelList.size(); i++) {
            Level level = levelList.get(i);
            setColumn(i - start, level.getLevel());
        }
    }


    /**
     * 设置某一段位的段数
     *
     * @param rank  段位 0-29
     * @param level 段数 0-36;
     */
    public void setColumn(int rank, int level) {
        if ((rank >= columnCount | rank < -1)) {
            return;
        }
        LevelColumn cell = levelColumns[rank];
        if (cell == null) {
            cell = new LevelColumn();
        }
        cell.setToX1(leftSpace + columnWidth * rank + columnMargin * rank);
        cell.setToX2(leftSpace + columnWidth * (rank + 1) + columnMargin * rank);
        cell.setToY1(topSpace + levelHeight * (levelCount - level));
        cell.setToY2(columnStartArea);
        cell.setLevel(level);
        levelColumns[rank] = cell;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!slideEnable) {
            performClick();
            return false;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!slideEnable) {
            return true;
        }
        final float x = event.getX();
        final float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                toX = x;
                toY = y;
                calcPoint();
                break;
            case MotionEvent.ACTION_MOVE:
                toX = x;
                toY = y;
                calcPoint();
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        return true;
    }

    private void calcPoint() {
        float left = 0, right = 0, top = 0, bottom = 0;
        top = toY;
        bottom = columnStartArea;
        if (top < topSpace) {
            top = topSpace;
        }
        if (top > bottom) {
            top = bottom;
        }
        int tg = -1;
        for (int i = 0; i < columnCount; i++) {
            if (toX > (leftSpace + columnWidth * i + columnMargin * i) && toX < (leftSpace + columnWidth * (i + 1) + columnMargin * i)) {
                left = leftSpace + columnWidth * i + columnMargin * i;
                right = leftSpace + columnWidth * (i + 1) + columnMargin * i;
                tg = i;
                break;
            }
        }
        if (tg != -1) {
            LevelColumn cell = levelColumns[tg];
            if (cell == null) {
                cell = new LevelColumn();
                levelColumns[tg] = cell;
            }
            for (int i = 0; i < levelCount; i++) {
                if (toY > (columnStartArea - levelHeight * (i + 1)) && toY < (columnStartArea - levelHeight * i)) {
                    top = topSpace + levelHeight * (levelCount - i);
                    cell.setLevel((levelCount - i));
                    break;
                }
            }
            cell.setXY(left, top, right, bottom);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (LevelColumn cell : levelColumns) {
            if (cell != null) {
                canvas.drawRect(cell.getToX1(), cell.getToY1(), cell.getToX2(), cell.getToY2(), mPaint);
            }
        }
        if (buoyBitmap != null) {
            canvas.drawBitmap(buoyBitmap, buoyLeft + buoyBitmapWidth, topSpace - buoyBitmapHeight * 2, null);
        }
    }

    public void recycle() {
        mPaint.reset();
        mPaint = null;
        if (buoyBitmap != null) {
            buoyBitmap.recycle();
        }
        buoyBitmap = null;
        levelColumns = null;
    }
}
