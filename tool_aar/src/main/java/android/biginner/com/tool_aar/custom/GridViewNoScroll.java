package android.biginner.com.tool_aar.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 不允许滑动的 GridView
 */
public class GridViewNoScroll extends GridView {
    public GridViewNoScroll(Context context) {
        this(context,null);
    }

    public GridViewNoScroll(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GridViewNoScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction()==MotionEvent.ACTION_MOVE){
            return true;
        }
        return true;
    }
}
