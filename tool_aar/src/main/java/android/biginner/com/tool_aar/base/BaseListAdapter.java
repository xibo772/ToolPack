package android.biginner.com.tool_aar.base;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * AbsListView 适配器
 * <p>
 * 使用说明：
 * 使用时继承此类，实现抽象方法 convert（）即可，
 * 在 convert（）方法中实现控件与数据的绑定；
 * <p>
 * 在convert（）中可以直接调用相应的 Holder中的方法 holder.xxx() 方法进行
 * 初始化控件、获取控件、设置控件、绑定数据，
 * <p>
 * <p>
 * 不适用于多布局，
 * 多布局建议使用 RecyclerView 实现
 */
public abstract class BaseListAdapter<DATA> extends BaseAdapter {
    protected List<DATA> mListData;
    protected Context context;
    private String TAG = this.getClass().getName();

    /**
     * 获取itemView 样式布局
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 构造函数----适用于数据不变动或者渲染之前就可以获取数据
     *
     * @param context 上下文
     * @param data    数据源集合
     */
    public BaseListAdapter(Context context, List<DATA> data) {
        this.mListData = data;
        this.context = context;
    }

    /**
     * 构造函数----适用于数据变动频繁
     *
     * @param context 上下文
     */
    public BaseListAdapter(Context context) {
        this.context = context;
    }

    /**
     * 数据源设置-----重置数据源集合
     *
     * @param data
     */
    public final void setData(List<DATA> data) {
        if (data == null)
            Log.e(TAG, "setData() 传入数据集合为 null");
        mListData = data;
        notifyDataSetChanged();
    }

    /**
     * 数据源设置----在数据末尾添加一条数据
     *
     * @param data
     */
    public final void addData(DATA data) {
        if (mListData == null)
            mListData = new ArrayList<>();

        mListData.add(data);
        notifyDataSetChanged();
    }

    /**
     * 数据源设置----在数据末尾添加多条数据
     *
     * @param datas
     */
    public final void addData(List<DATA> datas) {
        if (datas == null)
            mListData = new ArrayList<>();

        mListData.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 数据源设置----在数据某个位置添加一条数据
     *
     * @param index
     * @param data
     */
    public final void addData(int index, DATA data) {
        if (mListData == null)
            mListData = new ArrayList<>();
        if (index > mListData.size() || index < 0) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }
        mListData.add(index, data);
        notifyDataSetChanged();
    }

    /**
     * 数据源设置----从数据某个位置添加多条数据
     *
     * @param index
     * @param datas
     */
    public final void addData(int index, List<DATA> datas) {
        if (mListData == null)
            mListData = new ArrayList<>();
        if (index > mListData.size() || index < 0) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }
        mListData.addAll(index, datas);
        notifyDataSetChanged();
    }

    /**
     * 数据源设置----移除某个位置的数据
     *
     * @param index
     */
    public final void remove(int index) {
        if (mListData == null)
            mListData = new ArrayList<>();
        if (index < 0 || index >= mListData.size()) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }
        mListData.remove(index);
        notifyDataSetChanged();
    }

    /**
     * 数据源设置----移除某个数据
     *
     * @param data
     */
    public final void remove(DATA data) {
        if (mListData == null)
            mListData = new ArrayList<>();

        mListData.remove(data);
        notifyDataSetChanged();
    }

    /**
     * 获取数据源数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return mListData == null || mListData.isEmpty() ? 0 : mListData.size();
    }

    /**
     * 获取某条数据源
     *
     * @param position
     * @return
     */
    @Override
    public DATA getItem(int position) {
        return mListData == null || mListData.isEmpty() ? null : mListData.get(position);
    }

    /**
     * 获取某条数据源的ID
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return mListData == null || mListData.isEmpty() ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(getLayout(), parent, false);
        Holder holder = new Holder(convertView);
        convert(position, context, mListData.get(position), holder);
        return convertView;
    }

    protected abstract void convert(int position, Context context, DATA data, Holder holder);

    protected class Holder {
        public View itemView;
        private SparseArray<View> mArray;

        public Holder(View parent) {
            this.itemView = parent;
            mArray = new SparseArray<>();
        }

        /**
         * 初始化控件------获取控件
         * <p>
         * 控件改变影响其他控件
         *
         * @param resId
         * @param <V>
         * @return
         */
        public <V extends View> V getView(int resId) {
            View view = mArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                mArray.put(resId, view);
            }
            return (V) view;
        }

        /**
         * 设置控件-----单个控件改变不影响其他
         * <p>
         * 可以根据情况是否需要获取返回值
         *
         * @param resId
         * @param callBack
         * @param <V>
         */
        public <V extends View> V setView(@IdRes int resId, Back<V> callBack) {
            View view = mArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                mArray.put(resId, view);

            }
            if (callBack != null && view != null) {
                callBack.run((V) view);
            }
            return (V) view;

        }

        /**
         * 快速设置 TextView 控件
         * <p>
         * 可以根据需要与否获取返回值
         *
         * @param resId
         * @param text
         */
        public final TextView setText(@IdRes int resId, CharSequence text) {
            return setText(resId, text, TextView.class);
        }

        /**
         * 快速设置 继承TextView 的控件
         * <p>
         * 可以根据需要与否获取返回值
         *
         * @param resId
         * @param text
         */
        public final <T extends TextView> T setText(@IdRes int resId, CharSequence text, Class<T> tClass) {
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
         * <p>
         * 可以根据需要与否获取返回值
         *
         * @param resId
         * @param image
         */
        public final ImageView setImage(@IdRes int resId, @DrawableRes int image) {
            return setImage(resId, image, ImageView.class);
        }

        /**
         * 快速设置 继承ImageView 的控件
         * <p>
         * 可以根据需要与否获取返回值
         *
         * @param resId
         * @param image
         */
        public final <V extends ImageView> V setImage(@IdRes int resId, @DrawableRes int image, Class<V> vClass) {
            View view = mArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                mArray.put(resId, view);
            }
            if (view != null)
                ((V) view).setImageResource(image);
            return (V) view;
        }

    }

    /**
     * 初始化控件回调
     */
    protected interface Back<V> {
        void run(@NonNull V v);
    }


}
////////////////////////////////////////////使用示例 👇 ////////////////////////////////////////////
/*
    public class Adapter extends BaseListAdapter<String> {
        @Override
        protected int getLayout() {
            return R.layout.item_layout;
        }

        public Adapter(Context context) {
            super(context);
        }

        @Override
        protected void convert(int position, Context context, final String s, BaseGroupHolder holder) {
            //设置控件方法
            //此处以 TextView 为例
            //方法一：快速设置
            holder.setText(id.tv, s);
            //方法二：使用回调设置
            holder.setView(id.tv, TextView.class, new Back<TextView>() {
                @Override
                public void run(TextView textView) {
                    textView.setText(s);
                }
            });
            //方法三：获取控件设置
            TextView textView = holder.getView(id.tv, TextView.class);
            textView.setText(s);

            //方法一，只提供了 TextView 和 ImageView 控件及其子控件的设置
            //方法二，适用于独立控件，不影响也不参与其他控件的事件
            //方法三，适用于所有情况

            //如果要设置 item 整体的点击事件，可以使用 holder.itemView 进行设置
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
 */
////////////////////////////////////////////使用示例 👆 ////////////////////////////////////////////
