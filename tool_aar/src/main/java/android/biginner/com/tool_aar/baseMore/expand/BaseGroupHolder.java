package android.biginner.com.tool_aar.baseMore.expand;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ExpandableListView 多布局  GroupView 复用器
 */
public abstract class BaseGroupHolder<T> {
    protected boolean isExpanded;
    protected Context mContext;
    private SparseArray<View> mArray;
    protected View groupView;

    public BaseGroupHolder(Context mContext, View groupView, boolean isExpanded) {
        this.isExpanded = isExpanded;
        this.mContext = mContext;
        this.groupView = groupView;
    }

    /**
     * 数据绑定
     *
     * @param context
     * @param t
     * @param position
     */
    public abstract void bindViewData(Context context, T t, int position);



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
            view = groupView.findViewById(resId);
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
            view = groupView.findViewById(resId);
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
            view = groupView.findViewById(resId);
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
            view = groupView.findViewById(resId);
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
