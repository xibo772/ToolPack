package android.biginner.com.tool_aar.baseMore.recycler;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 多布局 RecyclerView 适配器
 * <p>
 * (单一布局不实用 太复杂,也可以使用,)
 * <p>
 * 使用时，不需要对 Adapter 进行设置，所有操作放在外部；
 * <p>
 * BaseValue 数据类型 ：所有 数据Bean类必须实现接口 BaseValue
 * BaseGroupHolder 复用类  ：所有复用器均需继承自BaseHolder
 * BaseFactory 工厂类 ：每一个RecyclerView 都有一个对应的 BaseFactory（继承 BaseFactory）
 * <p>
 * 有几种布局样式就创建几类(Bean , BaseGroupHolder : 一一对应),
 * BaseFactory 只需创建一个就可以
 */
public class BaseRecyclerMoreAdapter extends RecyclerView.Adapter<BaseHolder> {
    private Context mContext;
    private BaseFactory factory;
    private List<BaseValue> mListData;
    private String TAG = getClass().getName();

    /**
     * 构造方法 ----适用于数据变动
     *
     * @param mContext
     * @param factory
     */
    public BaseRecyclerMoreAdapter(Context mContext, BaseFactory factory) {
        this.mContext = mContext;
        this.factory = factory;
    }

    /**
     * 构造方法 -----适用于数据初始化之后不变动
     *
     * @param mContext
     * @param factory
     * @param data
     */
    public BaseRecyclerMoreAdapter(Context mContext, BaseFactory factory, List<BaseValue> data) {
        this.mContext = mContext;
        this.factory = factory;
        this.mListData = data;
    }

    /**
     * 设置数据源
     *
     * @param data
     */
    public void setDataList(List<BaseValue> data) {
        this.mListData = data;
        notifyDataSetChanged();
    }
////////////////////////////// 对部分数据进行操作 👇 ///////////////////////////////////////////////

    /**
     * 在数据末尾添加一条数据
     *
     * @param bean
     */
    public void addData(BaseValue bean) {
        if (mListData == null)
            mListData = new ArrayList<>();

        mListData.add(bean);
        notifyDataSetChanged();
    }

    /**
     * 在index 位置添加一条数据
     *
     * @param index
     * @param bean
     */
    public void addData(int index, BaseValue bean) {
        if (mListData == null)
            mListData = new ArrayList<>();

        if (index < 0 || index > mListData.size()) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }

        mListData.add(index, bean);
        notifyDataSetChanged();
    }

    /**
     * 在数据末尾添加若干条数据
     *
     * @param list
     */
    public void addDatas(List<BaseValue> list) {
        if (mListData == null)
            mListData = new ArrayList<>();

        mListData.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 从index 开始添加若干条数据
     *
     * @param index
     * @param list
     */
    public void addDatas(@IntRange() int index, List<BaseValue> list) {
        if (mListData == null)
            mListData = new ArrayList<>();

        if (index < 0 || index > mListData.size()) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }

        mListData.addAll(index, list);
        notifyDataSetChanged();
    }

    /**
     * 修改数据中的某一条数据
     *
     * @param index
     * @param baseBean
     */
    public void setData(@IntRange int index, BaseValue baseBean) {
        if (mListData == null)
            mListData = new ArrayList<>();

        if (index < 0 || index > mListData.size()) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }

        mListData.set(index, baseBean);
        notifyDataSetChanged();
    }

    /**
     * 移除 某个位置的数据
     *
     * @param index
     */
    public void removeData(int index) {
        if (mListData == null || mListData.isEmpty()) {
            return;
        }

        if (index < 0 || index >= mListData.size()) {
            Log.e(TAG, "index = " + index + " 数组越界");
            index = index < 0 ? 0 : mListData.size();
        }

        mListData.remove(index);
        notifyDataSetChanged();
    }

    /**
     * 移除 某条数据
     *
     * @param baseBean
     */
    public void removeData(BaseValue baseBean) {
        if (mListData == null || mListData.isEmpty()) {
            return;
        }
        mListData.remove(baseBean);
        notifyDataSetChanged();
    }
////////////////////////////// 对部分数据进行操作 👆 ///////////////////////////////////////////////

    /**
     * 获取当前 item 的类型
     * 交给 BaseFactory 处理
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mListData == null ? 0 : mListData.get(position).getLayoutId(factory);
    }

    /**
     * 创建 viewHolder
     */
    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        return factory.creatViewHolder(mContext, v, viewType);
    }

    /**
     * 绑定 ViewHolder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.bindViewData(mContext, mListData.get(position), position);
    }

    /**
     * 当前数据条目数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    /**
     * 动态设置 布局样式为 GridLayoutManager时，每类布局列数
     * recyclerView设置GridLayoutManager 时,SpanCount 设置为几类布局列数的最小公倍数
     * 例如：
     * 布局A：2列
     * 布局B：3列
     * 布局C：2列
     * 布局D：1列
     * 则SpanCount 设置为 6
     * 对应的Bean类中设置为
     * A：3（6/2）
     * B：2（6/3)
     * C：3（6/2）
     * D：6（6/1）
     * <p>
     * 其实此处就是把 RecyclerView当成了一个表格，
     * 设置的是每一个 Item 所占的列数
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            ((GridLayoutManager) lm).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return mListData == null ? 1 : mListData.get(position).getSpanCount(factory);
                }
            });
        }
    }
////////////////////////////////////使用示例 👇 ////////////////////////////////////////////////////

////////////// BaseFactory 示例 👇 //////////////
    /*
    public class BaseFactory implements BaseFactory {
    private final int layout1 = R.layout.layout_1;//布局1
    private final int layout2 = R.layout.layout_2;//布局2
    private final int layout3 = R.layout.layout_3;//布局3

    @Override
    public int type(BaseValue type) {
        if (type instanceof Bean_1) {
            return layout1;
        } else if (type instanceof Bean_2) {
            return layout2;
        }  if (type instanceof Bean_3) {
            return layout3;
        } else {
            return 0;
        }
    }

    @Override
    public int spanCount(BaseValue type) {
        if (type instanceof Bean_1) {
            return 3;
        } else if (type instanceof Bean_2) {
            return 2;
        }  if (type instanceof Bean_3) {
            return 3;
        } else {
            return 6;
        }
    }

    @Override
    public BaseGroupHolder creatViewHolder(Context context, View v, int viewType) {
        if (viewType == layout1) {
            return new Holder_1(context, v);
        } else if (viewType == layout2) {
            return new Holder_2(context, v);
        } else if (viewType == layout3) {
            return new Holder_3(context, v);
        } else {
            return null;
        }
    }
}
     */
////////////// BaseFactory 示例 👆 //////////////

////////////// Bean 示例 👇 //////////////
    /*
    public class Bean_1 implements BaseValue {
    private String name;

    public MineBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLayoutId(BaseFactory factory) {
        return factory.type(this);
    }

    @Override
    public int getSpanCount(BaseFactory factory) {
        return factory.spanCount(this);
    }
}
     */
////////////// Bean 示例 👆 //////////////

////////////// BaseGroupHolder 示例 👇 //////////////
    /*
    public class Holder_1 extends BaseGroupHolder<Bean_1> {
    public Holder_1(View itemView) {
        super(itemView);
    }

    @Override
    public void bindViewData(Bean_1 modle, int position) {
        TextView textName = initView(R.id.tv_name, TextView.class);
        textName.setText(modle.getName());
    }
    }
     */
////////////// BaseGroupHolder 示例 👆 //////////////
}
