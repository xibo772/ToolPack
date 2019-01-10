package android.biginner.com.tool_aar.baseMore.expand;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpandableListView 多布局适配器
 */
public class BaseExpandMoreAdapter extends BaseExpandableListAdapter {
    private List<BaseValue> listGroup;//Group 数据
    private List<List<BaseValue>> listChild;//child 数据
    private BaseFactory factory;//工厂类
    private Context context;

    /**
     * 构造函数
     *
     * @param factory
     * @param context
     */
    public BaseExpandMoreAdapter(BaseFactory factory, Context context) {
        this.factory = factory;
        this.context = context;
    }

    /**
     * 设置数据
     *
     * @param listGroup
     * @param listChild
     */
    public void setData(List<BaseValue> listGroup, List<List<BaseValue>> listChild) {
        this.listGroup = listGroup;
        this.listChild = listChild;
    }

    /**
     * 添加数据
     *
     * @param value
     * @param list
     */
    public void addGroupData(BaseValue value, List<BaseValue> list) {
        if (listGroup == null) {
            listGroup = new ArrayList<>();
        }
        if (listChild == null) {
            listChild = new ArrayList<>();
        }
        listGroup.add(value);
        listChild.add(list);
    }
//
//    /**
//     * 在某个位置添加 group 数据, child 数据不可以为空
//     *
//     * @param groupPosition
//     * @param value
//     * @param list
//     */
//    public void addGroupData(int groupPosition, BaseValue value, List<BaseValue> list) {
//        //如果 group 数据为空,重新创建集合
//        if (isEmptyGroup()) {
//            listGroup = new ArrayList<>();
//            listChild = new ArrayList<>();
//        }
//
//        //如果 group 数据越界,则添加到最后
//        if (isIndexOutOfBounds(groupPosition)) {
//            Log.e(this.getClass().getName(), "addGroupData: ", new IndexOutOfBoundsException());
//            if (groupPosition < 0) {
//                groupPosition = 0;
//            } else {
//                groupPosition = listGroup.size();
//            }
//        }
//        listGroup.add(groupPosition, value);
//
//        //如果 groupPosition <= 子数据集合大小 ,说明数据正常
//        //反之,就是数据不正常,(listGroup 和 listChild 的大小不同)
//        if (groupPosition <= listChild.size()) {
//            listChild.add(groupPosition, list);
//        } else {
//            listGroup.remove(groupPosition);
//            Log.e(this.getClass().getName(), "addGroupData: Child", new IndexOutOfBoundsException());
//        }
//    }

    /**
     * 获取 GroupView 类型
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getGroupType(int groupPosition) {
        return isEmptyGroup()
                ? 0
                : listGroup.get(groupPosition).getLayoutId(factory);
    }

    /**
     * 获取 ChildView 类型
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return isEmptyChild(childPosition)
                ? 0
                : listChild.get(groupPosition).get(childPosition).getLayoutId(factory);

    }

    /**
     * 获取 GroupView 的总数量
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return isEmptyGroup() ? 0 : listGroup.size();
    }

    /**
     * 获取 第 groupPosition 条Group的 ChildView的总数量
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return isEmptyChild(groupPosition) ? 0 : listChild.get(groupPosition).size();
    }

    /**
     * 获取 GroupView 的数据
     *
     * @param groupPosition
     * @return
     */
    @Override
    public BaseValue getGroup(int groupPosition) {
        return isEmptyGroup() ? null : listGroup.get(groupPosition);
    }

    /**
     * 获取 ChildView 的数据
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public BaseValue getChild(int groupPosition, int childPosition) {
        return isEmptyChild(groupPosition)
                ? null : listChild.get(childPosition).get(childPosition);
    }

    /**
     * 获取 GroupView 的 ID
     *
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取 ChildView 的 ID
     *
     * @param groupPosition
     * @param childPosition
     * @return
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
     * 获取 GroupView
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(getGroupType(groupPosition), null);
        BaseGroupHolder holder = factory.creatGroupHolder(context, convertView, getGroupType(groupPosition),isExpanded);
        holder.bindViewData(context, getGroup(groupPosition), groupPosition);
        return convertView;
    }

    /**
     * 获取 ChildView
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(getChildType(groupPosition, childPosition), null);
        BaseChildHolder holder = factory.creatChildHolder(context, convertView, getChildType(groupPosition, childPosition),isLastChild);
        holder.bindViewData(context, getChild(groupPosition, childPosition), groupPosition, childPosition);
        return convertView;
    }

    /**
     * 子项是否可选中，如果需要设置子项的点击事件，需要返回true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 判断 Group 是否越界
     *
     * @param groupPosition
     * @return
     */
    private boolean isIndexOutOfBounds(int groupPosition) {
        if (isEmptyGroup()) {
            //group 数据源为空(null)
            return true;
        } else if (groupPosition > listGroup.size() || groupPosition < 0) {
            //position 大于Group 数据总数  越界
            //position < 0  越界
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断 child 是否越界
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    private boolean isIndexOutOfBounds(int groupPosition, int childPosition) {
        if (isIndexOutOfBounds(groupPosition)) {
            //groupPosition 数组越界
            return true;
        } else if (isEmptyChild(childPosition)) {
            //child 数据为空(null/isEmpty)
            return true;
        } else if (childPosition < 0 || childPosition > listChild.get(groupPosition).size()) {
            //position 大于 child 单数据总数  越界
            //position < 0  越界
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断 Group 数据是否为空
     *
     * @return
     */
    private boolean isEmptyGroup() {
        if (listGroup == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断 Child 数据是否为空
     *
     * @param groupPosition
     * @return
     */
    private boolean isEmptyChild(int groupPosition) {
        if (isEmptyGroup()) {
            return true;
        } else if (listChild == null) {
            return true;
        } else if (listChild.get(groupPosition) == null) {
            return true;
        } else {
            return false;
        }
    }
}
