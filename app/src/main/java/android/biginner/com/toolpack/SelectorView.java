package android.biginner.com.toolpack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * 作者：created by huangweizhi.ex on 2018/10/10/010
 * 邮箱：huangweizhi.ex@hx-partners.com
 * 描述：该类用作
 */
public class SelectorView extends View {
    private List<String> mData;//数据源
    private int mSelectedIndex = 0;//选择的条目
    private int mViewWidth;
    private int mViewHeight;
    private int mColorText = Color.parseColor("#121D2B");
    private int mColorText0 = Color.parseColor("#BDC6D1");
    private float mMoveLen = 0;//滑动距离
    private int mMaxTextSize = 54;
    private int mMinTextSize = 48;
    private Paint mPaint;

    public void setData(List<String> data) {
        this.mData = data;
        invalidate();
    }

    public void setSelectedIndex(int index) {
        this.mSelectedIndex = index;
    }

    public SelectorView(Context context) {
        this(context, null);
    }

    public SelectorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 342);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 342);
        }

        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(mColorText);

        mPaint.setTextSize(mMaxTextSize);
        // text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
        float x = (float) (mViewWidth / 2.0);
        float y = (float) (mViewHeight / 2.0 + mMoveLen);
        Paint.FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        canvas.drawText(mData.get(mSelectedIndex), x, baseline, mPaint);


        mPaint.setColor(mColorText0);
        //绘制上方
//        if (mData.size() > 2 && mSelectedIndex > 0) {
//            drawOtherText(canvas, mSelectedIndex - 1, -1);
//        }
//        //绘制下方
//        if (mData.size() > mSelectedIndex + 2)
//            drawOtherText(canvas, mSelectedIndex + 1, 1);
        for (int i = 1; (mSelectedIndex - i) >= 0; i++) {
            drawOtherText(canvas, i, -1);
        }
        // 绘制下方data
        for (int i = 1; (mSelectedIndex + i) < mData.size(); i++) {
            drawOtherText(canvas, i, 1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float start = 0;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                start = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveLen = event.getY() - start;

                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(mMoveLen) - mViewHeight / 3.0 > 0.0001) {
                    if (mMoveLen < 0) {//上滑
                        mSelectedIndex = (mSelectedIndex + 1) % mData.size();

                    } else if (mMoveLen > 0) {//下滑
                        if (mSelectedIndex == 0) {
                            mSelectedIndex = mData.size() - 1;
                        } else {
                            mSelectedIndex -= 1;
                        }
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }


    private void drawOtherText(Canvas canvas, int position, int type) {
        float d = (float) (2 * mMinTextSize * position
//                + type                * mMoveLen
        );
//        float scale = parabola(mViewHeight / 4.0f, d);
//        float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
        mPaint.setTextSize(mMinTextSize);
//        mPaint.setAlpha((int) ((255 - 120) * scale + 120));

        float y = (float) (mViewHeight / 2.0 + type * d);

        Paint.FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        canvas.drawText(mData.get(position),
                (float) (mViewWidth / 2.0), baseline, mPaint);
    }

    private float parabola(float zero, float x) {
        float f = (float) (1 - Math.pow(x / zero, 2));
        return f < 0 ? 0 : f;
    }
}
