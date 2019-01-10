package android.biginner.com.tool_aar.base;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<DATA> extends RecyclerView.Adapter<BaseRecyclerAdapter.Holder> {
    private final String TAG = getClass().getName();
    private List<DATA> mListData = null;
    protected Context mContext = null;
    private ClickListener mClickListener;
    private LongListener mLongListener;

    /**
     * 构造函数
     *
     * @param context
     * @param data
     */
    public BaseRecyclerAdapter(Context context, List<DATA> data) {
        this.mListData = data;
        this.mContext = context;
    }

    /**
     * 构造函数
     *
     * @param mContext
     */
    public BaseRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 设置数据源
     *
     * @param data
     */
    public void setDataList(List<DATA> data) {
        this.mListData = data;
        notifyDataSetChanged();
    }

    /**
     * 更新数据源
     *
     * @param data
     */
    public void updateData(List<DATA> data) {
        if (mListData != null) {
            mListData.clear();
        }
        setDataList(data);
    }

    /**
     * 在 某个位置添加一条数据
     *
     * @param index
     * @param data
     */
    public void addData(int index, DATA data) {
        if (mListData == null)
            mListData = new ArrayList<>();

        if (index < 0 || index > mListData.size()) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }

        mListData.add(index, data);
        //如果想使用动画必用
        notifyItemInserted(index);
    }

    /**
     * 在某个位置添加多条数据
     *
     * @param index
     * @param datas
     */
    public void addDatas(int index, List<DATA> datas) {
        if (mListData == null)
            mListData = new ArrayList<>();

        if (index < 0 || index > mListData.size()) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }

        mListData.addAll(index, datas);
        //如果想使用动画必用
        notifyItemInserted(index);
    }

    /**
     * 在末尾添加一条数据
     *
     * @param data
     */
    public void addData(DATA data) {
        if (mListData == null)
            mListData = new ArrayList<>();

        mListData.add(data);
        //如果想使用动画必用
        notifyItemInserted(mListData.size() - 1);
    }

    /**
     * 在末尾添加多条数据
     *
     * @param datas
     */
    public void addData(List<DATA> datas) {
        if (mListData == null)
            mListData = new ArrayList<>();

        mListData.addAll(datas);
        //如果想使用动画必用
        notifyItemInserted(mListData.size() - 1);
    }

    /**
     * 移除某位置的数据
     */
    public void removeData(int index) {
        if (mListData == null || mListData.isEmpty())
            throw new NullPointerException();

        if (index < 0 || index >= mListData.size()) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }

        mListData.remove(index);
        //如果想使用动画必用
        notifyItemRemoved(index);
    }

    /**
     * 移除某个数据
     *
     * @param t
     */
    public void removeData(DATA t) {
        if (mListData == null || mListData.isEmpty())
            throw new NullPointerException();

        mListData.remove(t);
        //如果想使用动画必用
        notifyDataSetChanged();
    }

    /**
     * 创建复用器
     */
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(getLayoutID(), parent, false);
        final Holder holder = new Holder(inflate);

        return holder;
    }

    /**
     * 绑定ViewHolder
     */
    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.Holder holder, int position) {
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.run(mContext,holder,mListData.get(position),position);
                }
            });
        }
        if (mLongListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongListener.run(mContext,holder,mListData.get(position),position);
                    return false;
                }
            });
        }
        convert(holder, mListData.get(position), position);
    }

    /**
     * 获取 item 布局样式
     *
     * @return
     */
    protected abstract int getLayoutID();


    protected abstract void convert(Holder holder, DATA data, int position);


    /**
     * 获取条目数量
     */
    @Override
    public int getItemCount() {
        return (mListData == null || mListData.isEmpty()) ? 0 : mListData.size();
    }

    /**
     * 获取某位置数据
     *
     * @param position
     * @return
     */
    public DATA getData(int position) {
        if (mListData == null || mListData.isEmpty())
            return null;

        if (position < 0 || position >= mListData.size()) {
            Log.e(TAG, "index = " + position + " 数组越界");
            return null;
        }

        return mListData.get(position);
    }


    public class Holder extends RecyclerView.ViewHolder {
        private SparseArray<View> mArray;

        public Holder(View inflate) {
            super(inflate);
            mArray = new SparseArray<>();
        }

        /**
         * 设置控件-----单个控件改变不影响其他
         */
        public <V extends View> void setView(@IdRes int resId, Class<V> vClass, CallBack<V> callBack) {
            View view = mArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                if (view == null)
                    return;
                mArray.put(resId, view);
            }

            if (callBack != null)
                callBack.run((V) view);

        }

        /**
         * 初始化控件------控件改变影响其他控件
         */
        public <V extends View> V initView(@IdRes int resId, Class<V> vClass) {
            View view = mArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                if (view == null)
                    return null;
                mArray.put(resId, view);
            }

            return (V) view;
        }

        /**
         * 快速设置 继承TextView 的控件
         */
        public final void setText(@IdRes int resId, CharSequence text) {
            View view = mArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                if (view == null)
                    return;
                mArray.put(resId, view);
            }

            ((TextView) view).setText(text);
        }

        /**
         * 快速设置 继承TextView 的控件
         */
        public final <V extends TextView> V setText(@IdRes int resId, Class<V> tClass, CharSequence text) {
            View view = mArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                if (view == null)
                    return null;
                mArray.put(resId, view);
            }

            ((V) view).setText(text);
            return (V) view;
        }

        /**
         * 快速 ImageView 的控件
         */
        public final void setImage(@IdRes int resId, @DrawableRes int image) {
            View view = mArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                if (view == null)
                    return;
                mArray.put(image, view);
            }
            ((ImageView) view).setImageResource(image);
        }

        /**
         * 快速设置 继承ImageView 的控件
         */
        public final <V extends ImageView> V setImage(@IdRes int resId, Class<V> vClass, @DrawableRes int image) {
            View view = mArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                if (view == null)
                    return null;
                mArray.put(image, view);
            }
            ((V) view).setImageResource(image);
            return (V) view;
        }
    }

    /**
     * 回调函数
     */
    public interface CallBack<V> {
        void run(V v);
    }

    public void setClickListener(ClickListener listener) {
        mClickListener = listener;
    }

    public void setLongListener(LongListener listener) {
        mLongListener = listener;
    }

    public interface ClickListener<DATA> {
        void run(Context mContext, BaseRecyclerAdapter.Holder holder, DATA data, int position);
    }

    public interface LongListener<DATA> {
        void run(Context mContext, BaseRecyclerAdapter.Holder holder, DATA data, int position);
    }
}
