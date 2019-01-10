package android.biginner.com.toolpack;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * 作者：created by huangweizhi.ex on 2018/10/8/008
 * 邮箱：huangweizhi.ex@hx-partners.com
 * 描述：该类用作
 */
public class Chooser extends LinearLayout {

    private int color_bg;
    private int color_divider;
    private int color_gradient;
    private int color_select;
    private int color_unselect;
    private float textSize;
    private Paint mPaint;
    private String[] datas = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private int select = 0;
    //是否设置了间隔线颜色  false 未设置
    private boolean mShowDivider = false;
    //是否设置了渐变色  false 未设置
    private boolean mShowGradient = false;
    public Chooser(Context context) {
        this(context,null);
    }

    public Chooser(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Chooser(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_chooser, this, true);
        setWillNotDraw(false);
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
    }
}
