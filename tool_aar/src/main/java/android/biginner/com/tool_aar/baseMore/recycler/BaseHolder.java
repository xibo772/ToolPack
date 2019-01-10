package android.biginner.com.tool_aar.baseMore.recycler;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * recyclerView 多布局的 ViewHolder
 * <p>
 * 使用说明：
 * 继承此类，覆写 setUpView（） 方法
 * 在 bindViewData 中设置控件（包括初始化控件）
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    private SparseArray<View> mArray;

    public BaseHolder(View itemView) {
        super(itemView);
        mArray = new SparseArray<>();
    }

    /**
     * 控件与数据进行绑定
     * <p>
     * 如果设置 Item 的点击事件，可以使用
     * itemView.setOnClickListener();
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
            view = itemView.findViewById(resId);
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
            view = itemView.findViewById(resId);
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
            view = itemView.findViewById(resId);
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
            view = itemView.findViewById(resId);
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
////////////// BaseGroupHolder 示例 👇 //////////////
    /*
    public class Holder_1 extends BaseGroupHolder<Bean_1> {
    public Holder_1(Context context, View itemView) {
        super(context,itemView);
    }

    @Override
    public void bindViewData(Bean_1 modle, int position) {
        TextView textName = initView(R.id.tv_name, TextView.class);
        textName.setText(modle.getName());
    }
    }
     */
////////////// BaseGroupHolder 示例 👆 //////////////
