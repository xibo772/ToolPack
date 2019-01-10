package android.biginner.com.tool_aar.base;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExpandableListView 可实现展开单个或者多个的效果
 * collapseGroup() 收起
 * expandGroup() 展开,
 * 单个父布局
 * <p>
 * setGroupIndicator(null) 去掉自带展开箭头
 */
public abstract class BaseExpandAdapter<G, C> extends BaseExpandableListAdapter {
    private Context mContext = null;
    private List<G> mListGroup = null;
    private Map<Integer, List<C>> mMapChild = null;


    /**
     * 构造函数
     *
     * @param mContext
     */
    public BaseExpandAdapter(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * 构造函数
     *
     * @param mContext
     * @param mListGroup group数据源
     * @param mMapChild  child数据源
     */
    public BaseExpandAdapter(Context mContext, List<G> mListGroup, Map<Integer, List<C>> mMapChild) {
        this.mContext = mContext;
        this.mListGroup = mListGroup;
        this.mMapChild = mMapChild;
    }

    /**
     * 获取 Group 样式布局
     *
     * @return
     */
    public abstract int getGroupLayoutId();

    /**
     * 获取 Child 样式布局
     *
     * @return
     */
    public abstract int getChildLayoutId();

    /**
     * 主数据源设置
     */
    public void setDataGroup(List<G> listGroup) {
        this.mListGroup = listGroup;
        notifyAll();
    }

    /**
     * 提供两种子数据源的设置方法
     */
    /*List 方式*/
    public void setDataChild(List<List<C>> listChild) {
        if (listChild != null && !listChild.isEmpty()) {
            mMapChild = new HashMap<>();

            for (int i = 0; i < listChild.size(); i++) {
                mMapChild.put(i, listChild.get(i));
            }
        }
        notifyAll();
    }

    /*Map 方式*/
    public void setDataChild(Map<Integer, List<C>> map) {
        mMapChild = map;
        notifyAll();
    }

    /**
     * 主数据 子数据 同时设置
     */
    public void setData(List<G> listGroup, List<List<C>> listChild) {
        this.mListGroup = listGroup;
        if (listChild != null && !listChild.isEmpty()) {
            mMapChild = new HashMap<>();

            for (int i = 0; i < listChild.size(); i++) {
                mMapChild.put(i, listChild.get(i));
            }
        }
    }

    public void setData(List<G> listGroup, Map<Integer, List<C>> map) {
        this.mListGroup = listGroup;
        mMapChild = map;
    }



    /**
     * 获取父项的数量
     */
    @Override
    public int getGroupCount() {
        return (mListGroup == null || mListGroup.isEmpty())
                ? 0 : mListGroup.size();
    }

    /**
     * 获取某个父项的子项数量
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        //子数据Map 不为空
        //子数据Map大小  不能 > 父项ID
        //子数据List 不为空
        return (mMapChild == null || mMapChild.isEmpty()

                || mMapChild.size() < groupPosition

                || mMapChild.get(groupPosition) == null
                || mMapChild.get(groupPosition).isEmpty())

                ? 0 : mMapChild.get(groupPosition).size();
    }

    /**
     * 获取某个父项
     */
    @Override
    public G getGroup(int groupPosition) {
        return (mListGroup == null || mListGroup.isEmpty())
                ? null : mListGroup.get(groupPosition);
    }

    /**
     * 获取某个父项的某个子项
     */
    @Override
    public C getChild(int groupPosition, int childPosition) {
        return (mMapChild == null || mMapChild.isEmpty()

                || mMapChild.size() < groupPosition

                || mMapChild.get(groupPosition) == null
                || mMapChild.get(groupPosition).isEmpty())

                ? null : mMapChild.get(groupPosition).get(childPosition);
    }

    /**
     * 获取某个父项的ID
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取某个父项的某个子项ID
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获得父项显示的view
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(mContext).inflate(getGroupLayoutId(), parent, false);

        Holder holder = new Holder(inflate, mContext);
        convertGroup(holder, isExpanded, groupPosition, mListGroup.get(groupPosition));

        return inflate;
    }

    /**
     * 父布局替代方法
     *
     * @param holder        复用器
     * @param isExpanded    是否展开了
     * @param groupPosition 父Item ID
     * @param g             父Item 数据
     */
    protected abstract void convertGroup(Holder holder, boolean isExpanded, int groupPosition, G g);

    /**
     * 获得子项显示的view
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(mContext)
                .inflate(getChildLayoutId(), parent, false);

        Holder holder = new Holder(inflate, mContext);
        convertChild(holder, groupPosition, childPosition, mMapChild.get(groupPosition).get(childPosition));

        return inflate;
    }

    /**
     * 子布局替代方法
     *
     * @param holder        复用器
     * @param groupPosition 父Item  ID
     * @param childPosition 子Item   ID
     * @param c             子Item 数据
     */
    protected abstract void convertChild(Holder holder, int groupPosition, int childPosition, C c);

    /**
     * 子项是否可选中，如果需要设置子项的点击事件，需要返回true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    protected class Holder {
        private SparseArray<View> mArray;
        public View itemView;
        public Context context;

        public Holder(View itemView, Context mContext) {
            this.itemView = itemView;
            this.context = mContext;
            mArray = new SparseArray<View>();
        }

        /**
         * 控件设置
         */
        public <V extends View> void setView(int id, CallBack<V> callBack) {
            View view = mArray.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mArray.put(id, view);
            }

            if (callBack != null && view != null) {
                callBack.run((V) view);
            }
        }
    }

    /**
     * 回调函数
     */
    public interface CallBack<V> {
        void run( V v);
    }

}
////////////////////////////////////////// 示例 👇 ///////////////////////////////////////////////
/*
    class Adapter extends BaseExpandAdapter<String, String> {

        public Adapter(Context mContext) {
            super(mContext);
        }

        @Override
        public int getGroupLayoutId() {
            return R.layout.layout_item;
        }

        @Override
        public int getChildLayoutId() {
            return R.layout.layout_item;
        }


        @Override
        protected void convertGroup(BaseExpandAdapter<String, String>.Holder holder, boolean isExpanded,
         int groupPosition, final String s) {
            holder.setView(R.id.tv, new CallBack<TextView>() {
                @Override
                public void run(TextView textView) {
                    textView.setText(s);
                }
            });

        }

        @Override
        protected void convertChild(BaseExpandAdapter<String, String>.Holder holder, int groupPosition, int childPosition, final String s) {
            holder.setView(R.id.tv, new CallBack<TextView>() {
                @Override
                public void run(TextView textView) {
                    textView.setText(s);
                }
            });
        }
    }
 */
//////////////////////////////////////////// 示例 👆 ///////////////////////////////////////////////
