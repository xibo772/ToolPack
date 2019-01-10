package android.biginner.com.tool_aar.custom;


import android.biginner.com.tool_aar.R;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * EditText 清楚功能
 */

public class EditTextDel extends android.support.v7.widget.AppCompatEditText {
    private Context mcontext = null;
    private Drawable imageInit;
    private Drawable image;

    public EditTextDel(Context context) {
        super(context);
        mcontext = context;
        init();
    }

    public EditTextDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
        init();
    }

    public EditTextDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
        init();
    }

    private void init() {
        image = mcontext.getResources().getDrawable(R.drawable.ic_delete);
        imageInit = mcontext.getResources().getDrawable(R.drawable.ic_delete_);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (right != null) {
            right.setBounds(right.getIntrinsicWidth()/4,right.getIntrinsicHeight()/4,right.getIntrinsicWidth()*3/4, right.getIntrinsicHeight()*3/4);
        }
    }


    //设置删除图片
    private void setDrawable() {
        try {
            if (length() < 1) {
                setCompoundDrawablesWithIntrinsicBounds(null, null, imageInit, null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(null, null, image, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (image != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e("TAG", "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 10;
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

}
