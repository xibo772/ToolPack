package android.biginner.com.tool_aar.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * 圆形头像
 */

public class ImageRound extends android.support.v7.widget.AppCompatImageView {
    private Paint paint = new Paint();

    public ImageRound(Context context) {
        super(context);
    }

    public ImageRound(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageRound(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap rawBitmap = ((BitmapDrawable) drawable).getBitmap();

            //处理Bitmap 转成正方形
            Bitmap newBitmap = dealRawBitmap(rawBitmap);
            //将newBitmap 转换成圆形
            Bitmap circleBitmap = toRoundCorner(newBitmap, 14);

            final Rect rect = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());
            paint.reset();
            //绘制到画布上
            canvas.drawBitmap(circleBitmap, rect, rect, paint);
        } else {
            super.onDraw(canvas);
        }
    }

    //将头像按比例缩放
    private Bitmap scaleBitmap(Bitmap bitmap) {
        int width = getWidth();
        //一定要强转成float 不然有可能因为精度不够 出现 scale为0 的错误
        float scale = (float) width / (float) bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

    //将原始图像裁剪成正方形
    private Bitmap dealRawBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();//原宽度
        int height = bitmap.getHeight();//原高度
        int minWidth = width > height ? height : width;//获取最小尺寸
        //计算正方形的范围
        int leftTopX = (width - minWidth) / 2;
        int leftTopY = (height - minWidth) / 2;
        //裁剪成正方形
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, leftTopX, leftTopY, minWidth, minWidth, null, false);
        return scaleBitmap(newBitmap);
    }

    private Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        //指定为 ARGB_4444 可以减小图片大小
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();
//        canvas.drawCircle(x / 2, x / 2, (x / 2), paint);
        canvas.drawRoundRect(rectF,500,500,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        return output;
    }
}
