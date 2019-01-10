package android.biginner.com.tool_aar.baseMore.recycler;

import android.content.Context;
import android.view.View;

/**
 * recyclerView多布局的工厂类
 * 布局 与 Holder建立关联
 * <p>
 * 一个 RecyclerView 或 Adapter 只需一个工厂类即可
 * 根据 BaseValue 不同进行不同的操作
 * <p>
 * 例如：
 */

public interface BaseFactory {
    /**
     * 根据数据的不同，返回不同的布局，作为不同的不同布局类型的 标识
     *
     * @param type
     * @return
     */
    int type(BaseValue type);

    /**
     * 根据数据的不同，返回不同布局的每个 Item 的跨度（即所占份数）
     * @param bean
     * @return
     */
    int spanCount(BaseValue bean);

    /**
     * 根据不同的布局标识，返回不同的 复用器 BaseGroupHolder
     * @param context
     * @param v
     * @param viewType
     * @return
     */
    BaseHolder creatViewHolder(Context context, View v, int viewType);
}
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