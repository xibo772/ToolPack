package android.biginner.com.tool_aar.baseMore.expand;

import android.content.Context;
import android.view.View;

/**
 * 作者：created by huangweizhi.ex on 2018/9/10/010
 * 邮箱：huangweizhi.ex@hx-partners.com
 * 描述：该类用作
 */
public interface BaseFactory {
    int type(BaseValue value);
    BaseGroupHolder creatGroupHolder(Context context, View v, int viewType, boolean isExpanded);
    BaseChildHolder creatChildHolder(Context context, View v, int viewType, boolean isLastChild);
}
