package com.cjp.palette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by 蔡金品 on 2018/3/27.
 * email : caijinpin@zhexinit.com
 */

public class TingView extends View {
    /**
     * 以UI图屏幕为左边系
     * 右上角圆心  651 ，407
     * 第一个拐点  642 ，419
     * 第二个拐点  630 ，497
     * 第三个拐点  620 ，507
     * 第四点      606 ，519
     *
     *
     * 细线 5pX 粗线 14px （750 宽为基准）
     */


    int height = 0;
    int width = 0;
    private Paint paint;

    float circleX ,circleY , smallRadii , bigRadii;

    public TingView(Context context) {
        this(context,null);
    }

    public TingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint( );
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private void reset(){
        circleX = 190f/220f * width;
        circleY = 30f/220f * height;
        smallRadii = 8f/220f * width;
        bigRadii = 14f/220f * width;
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        width = MeasureSpec.getSize(widthMeasureSpec);
//        height = MeasureSpec.getSize(heightMeasureSpec);
//        setMeasuredDimension(width, height);
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        reset();
        if (width <=0 || height <=0 ){
            return;
        }

        // 绘制右上角两个圆形
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.WHITE);
        paint.setAlpha(100);
        canvas.drawCircle(circleX ,circleY ,bigRadii ,paint);


        paint.setAlpha(255);
        paint.setColor(Color.WHITE);
        paint.setShadowLayer(30, 30, 30, Color.BLACK);
        canvas.drawCircle(circleX ,circleY ,smallRadii ,paint);

        paint.reset();

        Path path = new Path();
        path.moveTo(circleX , circleY );
        path.lineTo(circleX - bigRadii, circleY + bigRadii);
        path.lineTo(circleX - bigRadii - 10f/220f * width , circleY + bigRadii + 80f/220f * height );
        path.lineTo(circleX - bigRadii - 10f/220f * width - bigRadii, circleY + bigRadii + 80f/220f * height + bigRadii/2);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE );
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth( 5f/220f * width);
        paint.setPathEffect( new CornerPathEffect(10) );
        canvas.drawPath(path, paint);


        paint.setStrokeWidth( 10f/220f * width);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(circleX - bigRadii - 10f/220f * width - bigRadii, circleY + bigRadii + 80f/220f * height + bigRadii/2 , circleX - bigRadii - 10f/220f * width - bigRadii* 2, circleY + bigRadii + 80f/220f * height + bigRadii, paint);


        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth( 1);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(width/2 ,height/2 ,20f/220f * width ,paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(width/2 ,height/2 ,5f/220f * width ,paint);
    }
}
