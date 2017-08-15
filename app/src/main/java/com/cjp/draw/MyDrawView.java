package com.cjp.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.cjp.R;

/**
 * Created by 蔡金品 on 2017/7/4.
 * email : caijinpin@zhexinit.com
 */
public class MyDrawView extends View {
    public static final String TAG = "MyDrawView";

    public MyDrawView(Context context) {
        this(context,null );

    }

    public MyDrawView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        mPath = new Path();
    }

    private void initPaint() {
        mPaint = new Paint();    //创建一个画笔对象
        mPaint.setColor(Color.WHITE);    //设置画笔的颜色为白色
        mPaint.setAntiAlias(true); // 抗锯齿

    }

    Paint mPaint;
    Path mPath;


    int mX = 50;
    int mY = 100;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置画布背景颜色
        canvas.drawColor(Color.BLACK);

        //创建对应坐标的矩形区域
        RectF mArc = new RectF(10, 10,50, 50);
       //画填充弧,在矩形区域内,从弧的最右边开始,画270度,然后再通过连接圆心来填充
        canvas.drawArc(mArc, 0, 270, true, mPaint);

        //画图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bitmap, 10, 60, mPaint);
        //画圆,(x轴,y轴,半径,画笔)
        canvas.drawCircle(500,100, 60,mPaint);


        //画一条线,(起点横坐标,起点纵坐标,终点横坐标,终点纵坐标,画笔)
        canvas.drawLine(mX, mY + 75, mX + 20, mY + 75, mPaint);
        //画多条线,(坐标数组,画笔)坐标数组里每四个值构成一条线
        canvas.drawLines(new float[] { mX + 50, mY + 45, mX + 50, mY + 75,
                mX + 60, mY + 45, mX + 60, mY + 75 }, mPaint);

        //创建对应矩形区域
        RectF mOval = new RectF(mX, mY + 80, mX + 60, mY + 110);
        //画椭圆
        canvas.drawOval(mOval, mPaint);

        /*
         * Paint qPaint = new Paint(); qPaint.setColor(Color.RED);
         * mCanvas.drawPaint(qPaint);
*/

        //重置Path里的所有路径
        mPath.reset();
        //设置Path的起点
        mPath.moveTo(mX, mY + 120);
        //第二个点
        mPath.lineTo(710, mY + 120);
        //第三个点
        mPath.lineTo(720 - 10, mY + 150);
        //画出路径,这里画的是三角形
        canvas.drawPath(mPath, mPaint);

//        //重置Path里的所有路径
//        mPath.reset();
//        //设置Path的起点
//        mPath.moveTo(qStartX, qStartY);
//        //设置贝塞尔曲线的控制点坐标和终点坐标
//        mPath.quadTo(qControlX, qCOntrolY, qEndX, qEndY);
//        //画出贝塞尔曲线
//        canvas.drawPath(mPath, qPaint);

        //画点
        canvas.drawPoint(mX, mY + 155, mPaint);
        //画多个点,坐标数组每两个值代表一个点的坐标
        canvas.drawPoints(new float[] { mX, mY + 160, mX + 5, mY + 160, mX + 5, mY + 160 }, mPaint);

        //画矩形
        canvas.drawRect(mX, mY + 170, mX + 100, mY + 220, mPaint);

        //设置矩形区域
        RectF mRect = new RectF(mX, mY + 230, mX + 100, mY + 260);
        //画圆角矩形,这个方法的第二第三个参数在后面有图讲解
        canvas.drawRoundRect(mRect, 10, 10, mPaint);

        //画文本
        canvas.drawText("drawText", mX, mY + 290, mPaint);
        //画文本,数组里每两个值代表文本的一个字符的坐标，数组的坐标可以比字符串里的字符多，但不可以少
        canvas.drawPosText("哈哈你好", new float[] { mX, mY + 310, mX + 20,
                mY + 310, mX + 40, mY + 310, mX + 60, mY + 310 }, mPaint);

        //重置Path
        mPath.reset();
        //添加一个圆形路径,坐标,半径,方向(顺时针还是逆时针)
        mPath.addCircle(mX + 10, mY + 340, 10, Path.Direction.CW);
        //画出路径
        canvas.drawPath(mPath, mPaint);
        //把文本画在路径上,但不会画出路径
        canvas.drawTextOnPath("draw", mPath, 30, 0, mPaint);

    }
}
