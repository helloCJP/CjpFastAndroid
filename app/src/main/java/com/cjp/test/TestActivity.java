package com.cjp.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cjp.R;
import com.cjp.util.FastBlur;

/**
 * Created by 蔡金品 on 2017/7/18.
 * email : caijinpin@zhexinit.com
 */
public class TestActivity extends Activity {
    public static final String TAG = "TestActivity";

    ImageView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        t = (ImageView) findViewById(R.id.test_image);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap sentBitmap = takeScreenShot(TestActivity.this);//((BitmapDrawable)getResources().getDrawable(R.drawable.cjp202, getTheme())).getBitmap();
//        Bitmap sentBitmap = convertDrawable2BitmapByCanvas((Drawable) getResources().getDrawable(R.drawable.bg, getTheme()));

                Bitmap bitmap = FastBlur.doBlur(sentBitmap,25, true);
//                if (Build.VERSION.SDK_INT > 16) {
//                    Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
//                    final RenderScript rs = RenderScript.create(TestActivity.this);
//                    final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
//                            Allocation.USAGE_SCRIPT);
//                    final Allocation output = Allocation.createTyped(rs, input.getType());
//                    final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//                    script.setRadius(25.f /* e.g. 3.f */);
//                    script.setInput(input);
//                    script.forEach(output);
//                    output.copyTo(bitmap);

                if (bitmap != null) {
                    Drawable drawable = coverColor(TestActivity.this, bitmap, 0x80000000);
                    t.setScaleType(ImageView.ScaleType.FIT_XY);
                    t.setImageDrawable(drawable);
                } else {
                    t.setImageBitmap(null);
                    t.setBackgroundColor( 0x80000000);
                }
//                    t.setImageBitmap(bitmap);
//                }
            }
        });

    }

    /**
     * 将bitmap转成蒙上颜色的Drawable
     *
     * @param context
     * @param bitmap
     * @param color   要蒙上的颜色
     * @return Drawable
     */
    public Drawable coverColor(Context context, Bitmap bitmap, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        new Canvas(bitmap).drawRoundRect(rect, 0, 0, paint);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width , height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        700,
                        1800,
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
// canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
