package android.biginner.com.tool_aar.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 空视图 RecyclerView
 * 可以调用 setEmptyView()添加空视图
 * <p>
 * 在 RecyclerView 界面布局中 添加要添加的空视图
 * 建议使用 ViewStub   android:layout="@layout/*****"
 * (ViewStub 可以实现 按需加载 )
 */
public class RecyclerEmptyView extends RecyclerView {
    public RecyclerEmptyView(Context context) {
        super(context);
    }

    public RecyclerEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    private View emptyView;
    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {//设置空view原理都一样，没有数据显示空view，有数据隐藏空view
            chickEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            chickEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            chickEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            chickEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            chickEmpty();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            chickEmpty();
        }
    };


    public void setEmptyView(View view) {
        emptyView = view;
        chickEmpty();
    }

    private void chickEmpty() {
        if (emptyView != null && getAdapter() != null) {
            emptyView.setVisibility(getAdapter().getItemCount() == 0 ? VISIBLE : GONE);
            setVisibility(getAdapter().getItemCount() == 0 ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        chickEmpty();
    }
}
