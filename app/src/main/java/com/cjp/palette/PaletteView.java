package com.cjp.palette;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 蔡金品 on 2018/3/23.
 * email : caijinpin@zhexinit.com
 */

public class PaletteView extends View {

    private Paint paint;
    private Path  mPath;
    private float mLastX;
    private float mLastY;
    private Bitmap mBufferBitmap;
    private Canvas mBufferCanvas;

    public PaletteView(Context context) {
        this(context,null);
    }

    public PaletteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PaletteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 设置 抗锯齿 与防抖动
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);


//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setFilterBitmap(true);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mDrawSize = 20;
//        mEraserSize = 40;
//        mPaint.setStrokeWidth(mDrawSize);
//        mPaint.setColor(0XFF000000);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        final float x = event.getX();
        final float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                if (mPath == null) {
                    mPath = new Path();
                }
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                //这里终点设为两点的中心点的目的在于使绘制的曲线更平滑，如果终点直接设置为x,y，效果和lineto是一样的,实际是折线效果
                mPath.quadTo(mLastX, mLastY, (x + mLastX) / 2, (y + mLastY) / 2);
                if (mBufferBitmap == null) {
                    initBuffer();
                }
//                if (mMode == Mode.ERASER && !mCanEraser) {
//                    break;
//                }
                mBufferCanvas.drawPath(mPath,paint);
                invalidate();
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                if (mMode == Mode.DRAW || mCanEraser) {
//                    saveDrawingPath();
//                }
                mPath.reset();
                break;
        }
        return true;
    }

    private void initBuffer(){
        mBufferBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mBufferCanvas = new Canvas(mBufferBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mBufferBitmap != null) {
            canvas.drawBitmap(mBufferBitmap, 0, 0, null);
        }
    }
}
