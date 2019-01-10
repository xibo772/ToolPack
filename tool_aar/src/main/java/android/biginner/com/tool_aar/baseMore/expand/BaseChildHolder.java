package android.biginner.com.tool_aar.baseMore.expand;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作者：created by huangweizhi.ex on 2018/9/10/010
 * 邮箱：huangweizhi.ex@hx-partners.com
 * 描述：该类用作
 */
public abstract class BaseChildHolder<T> {
    protected boolean isLastChild;
    protected Context mContext;
    private SparseArray<View> mArray;
    protected View childView;

    public BaseChildHolder( Context mContext, View childView,boolean isLastChild) {
        this.isLastChild = isLastChild;
        this.mContext = mContext;
        this.childView = childView;
    }


    /**
     * 数据绑定
     * @param context
     * @param t
     * @param groupPosition
     * @param childPosition
     */
    public abstract void bindViewData(Context context, T t, int groupPosition,int childPosition);




    /**
     * 初始化控件------获取控件
     * <p>
     * 控件改变影响其他控件
     *
     * @param resId
     * @param <V>
     * @return
     */
    protected <V extends View> V getView(int resId) {
        View view = mArray.get(resId);
        if (view == null) {
            view = childView.findViewById(resId);
            mArray.put(resId, view);
        }
        return (V) view;
    }

    /**
     * 设置控件-----单个控件改变不影响其他
     *
     * @param resId
     * @param callBack
     * @param <V>
     */
    protected <V extends View> V setView(@IdRes int resId, Back<V> callBack) {
        View view = mArray.get(resId);
        if (view == null) {
            view = childView.findViewById(resId);
            mArray.put(resId, view);
        }
        if (callBack != null && view != null)
            callBack.run((V) view);
        return (V) view;
    }

    /**
     * 快速设置 TextView 控件
     *
     * @param resId
     * @param text
     */
    protected final TextView setText(@IdRes int resId, CharSequence text) {
        return setText(resId, text);
    }

    /**
     * 快速设置 继承TextView 的控件
     *
     * @param resId
     * @param text
     */
    protected final <T extends TextView> T setText(@IdRes int resId, CharSequence text, Class<T> tClass) {
        View view = mArray.get(resId);
        if (view == null) {
            view = childView.findViewById(resId);
            mArray.put(resId, view);
        }
        if (view != null)
            ((T) view).setText(text);
        return (T) view;
    }

    /**
     * 快速设置 ImageView 控件
     *
     * @param resId
     * @param image
     */
    protected final ImageView setImage(@IdRes int resId, @DrawableRes int image) {
        return setImage(resId, image, ImageView.class);
    }

    /**
     * 快速设置 继承ImageView 的控件
     *
     * @param resId
     * @param image
     */
    protected final <V extends ImageView> V setImage(@IdRes int resId, @DrawableRes int image, Class<V> vClass) {
        View view = mArray.get(resId);
        if (view == null) {
            view = childView.findViewById(resId);
            mArray.put(resId, view);
        }
        if (view != null)
            ((V) view).setImageResource(image);
        return (V) view;

    }


    /*回调函数*/
    public interface Back<V> {
        void run(V v);
    }
}
