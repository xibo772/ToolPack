package android.biginner.com.tool_aar.custom;

import android.biginner.com.tool_aar.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * 下拉刷新 上拉加载更多
 * <p>
 * AdapterView   ScrollView  RecyclerView
 * <p>
 * 使用时,内部只能包含一个控件
 * 父、子控件不相互影响，只在回调接口存在关联
 * Activity或者Fragment 实现接口回调
 */

public class PullLayout extends ViewGroup {

    private View mHeaderView;//头部布局
    private View mFooterView;//尾部布局
    private int mHeaderHeight;//头布局高度
    private int mFooterHeight;//尾部局高度

    private Status mStatus = Status.NORMAL;
    private int mlastMoveY;//最后点击位置
    private int mLastYIntercept;
    private TextView mHeaderText;
    private ProgressBar mHeaderProgressBar;
    private ImageView mHeaderArrow;
    private TextView mFooterText;
    private ProgressBar mFooterProgressBar;
    private int mLayoutContentHeight;
    private OnFinishedListener onFinishedListener;


    public PullLayout(Context context) {
        this(context, null);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PullLayout);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addHeader();
        addFooter();
    }

    /**
     * 添加头部布局
     */
    private void addHeader() {
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.pull_header, null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mHeaderView, 0, params);

        mHeaderText = (TextView) findViewById(R.id.header_text);
        mHeaderProgressBar = (ProgressBar) findViewById(R.id.header_progressbar);
        mHeaderArrow = (ImageView) findViewById(R.id.header_arrow);
    }

    /**
     * 添加尾部布局
     */
    private void addFooter() {
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.pull_footer, null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mFooterView, params);
        mFooterText = (TextView) findViewById(R.id.footer_text);
        mFooterProgressBar = (ProgressBar) findViewById(R.id.footer_progressbar);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLayoutContentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeaderView) {
                child.layout(0, 0 - child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
                mHeaderHeight = child.getHeight();
            } else if (child == mFooterView) {
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                mFooterHeight = child.getHeight();
            } else {
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                if (i < getChildCount()) {
                    if (child instanceof ScrollView) {
                        mLayoutContentHeight += getMeasuredHeight();
                        continue;
                    }
                    mLayoutContentHeight += child.getMeasuredHeight();
                }
            }
        }
    }


    /**
     * ACTION_DOWN 不需要拦截
     * ACTION_UP 不需要拦截
     * <p>
     * 当事件为 ACTION_MOVE 时，
     * 如果是向下滑动，判断第一个child是否滑倒最上面，如果是，则更新状态为 TRY_REFRESH；
     * 如果是向上滑动，则判断最后一个child是否滑动最底部，如果是，则更新状态为TRY_LOADMORE。然后返回 intercept = true
     * <p>
     * 其他情况不进行拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int y = (int) ev.getY();

        //当状态为刷新或者加载中的时候不进行拦截
        if (mStatus == Status.REFRESHING || mStatus == Status.LOADING) {
            return false;
        }

        switch (ev.getAction()) {
            //ACTION_DOWN 不进行拦截
            case MotionEvent.ACTION_DOWN: {
                // 拦截时需要记录点击位置，不然下一次滑动会出错
                mlastMoveY = y;
                intercept = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {


                if (y > mLastYIntercept) {//向下滑动
                    View child = getChildAt(1);
                    intercept = getRefreshIntercept(child);

                    if (intercept) {
                        updateStatus(mStatus.TRY_REFRESH);
                    }
                } else if (y < mLastYIntercept) { //向上滑动
                    int childCount = getChildCount();
                    View child = getChildAt(1);
                    intercept = getLoadMoreIntercept(child);

                    if (intercept) {
                        updateStatus(mStatus.TRY_LOADMORE);
                    }
                } else {
                    intercept = false;
                }
                break;
            }
            //ACTION_UP 不进行拦截
            case MotionEvent.ACTION_UP: {
                intercept = false;
                break;
            }
        }

        mLastYIntercept = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        if (mStatus == Status.REFRESHING || mStatus == Status.LOADING) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mlastMoveY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mlastMoveY - y;
                // 一直在下拉
                if (getScrollY() <= 0 && dy <= 0) {
                    if (mStatus == Status.TRY_LOADMORE) {
                        scrollBy(0, dy / 30);
                    } else {
                        scrollBy(0, dy / 3);
                    }
                } else if (getScrollY() >= 0 && dy >= 0) {// 一直在上拉
                    if (mStatus == Status.TRY_REFRESH) {
                        scrollBy(0, dy / 30);
                    } else {
                        scrollBy(0, dy / 3);
                    }
                } else {
                    scrollBy(0, dy / 3);
                }

                beforeRefreshing(dy);
                beforeLoadMore();

                break;
            case MotionEvent.ACTION_UP:
                if (getScrollY() <= -mHeaderHeight) { // 下拉刷新，并且到达有效长度
                    releaseWithStatusRefresh();
                    if (onFinishedListener != null) {
                        onFinishedListener.refresh();
//                        refreshFinished();
                    }
                } else if (getScrollY() >= mFooterHeight) { // 上拉加载更多，达到有效长度
                    releaseWithStatusLoadMore();
                    if (onFinishedListener != null) {
                        onFinishedListener.loadMore();
//                        loadMoreFinished();
                    }
                } else {
                    releaseWithStatusTryRefresh();
                    releaseWithStatusTryLoadMore();
                }
                break;
        }

        mlastMoveY = y;
        return super.onTouchEvent(event);
    }


    /**
     * 设置刷新 加载的回调
     *
     * @param onFinishedListener
     */
    public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
    }

    /**
     * 刷新 加载的回调
     * <p>
     * 可以调用此方法设置
     * 也可以   布局.class  实现 OnFinishedListener接口
     */
    public interface OnFinishedListener {
        /**
         * 刷新回调,此方法中需要调用 refreshFinished()方法,隐藏头部布局
         */
       void refresh();

        /**
         * 加载更多回调,此方法中需要调用 loadMoreFinished()方法,隐藏头部布局
         */
        void loadMore();
    }


    /////////////////////////////////////////////////////判断是否进行拦截////////////////////////////////////////

    /**
     * 判断
     * 刷新 是否进行拦截
     *
     * @param child
     * @return
     */
    private boolean getRefreshIntercept(View child) {
        boolean intercept = false;

        if (child instanceof AdapterView) {
            intercept = adapterViewRefreshIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = scrollViewRefreshIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = recyclerViewRefreshIntercept(child);
        }
        return intercept;
    }

    /**
     * 判断
     * 加载更多 是否进行拦截
     *
     * @param child
     * @return
     */
    private boolean getLoadMoreIntercept(View child) {
        boolean intercept = false;

        if (child instanceof AdapterView) {
            intercept = adapterViewLoadMoreIntercept(child);
        } else if (child instanceof ScrollView) {
            intercept = scrollViewLoadMoreIntercept(child);
        } else if (child instanceof RecyclerView) {
            intercept = recyclerViewLoadMoreIntercept(child);
        }
        return intercept;
    }


    /**
     * 判断
     * AdapterView 下拉刷新是否进行拦截
     * <p>
     * 当屏幕中AdapterView 的第一个可见Item 是首Item,且 首Item的上端为 0   进行拦截
     *
     * @param child
     * @return
     */
    private boolean adapterViewRefreshIntercept(View child) {
        boolean intercept = true;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getFirstVisiblePosition() != 0
                || adapterChild.getChildAt(0).getTop() != 0) {

            intercept = false;
        }
        return intercept;
    }

    /**
     * 判断
     * AdapterView 加载更多  是否进行拦截
     * <p>
     * 当屏幕中AdapterView 最后一个可见item 是末尾Item
     * 且末尾Item距离屏幕底部 不小于控件测量高度   进行拦截
     *
     * @param child
     * @return
     */
    private boolean adapterViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        AdapterView adapterChild = (AdapterView) child;
        if (adapterChild.getLastVisiblePosition() == adapterChild.getCount() - 1 &&
                (adapterChild.getChildAt(adapterChild.getChildCount() - 1).getBottom() >= getMeasuredHeight())) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判断
     * ScrollView 刷新是否拦截
     * <p>
     * 当ScrollView 的左上角相对于 本控件左上角在Y轴的偏移量 <= 0 时 进行拦截
     *
     * @param child
     * @return
     */
    private boolean scrollViewRefreshIntercept(View child) {
        boolean intercept = false;
        if (child.getScrollY() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判断
     * ScrollView加载更多是否拦截
     * <p>
     * 当ScrollView 的左上角相对于 本控件左上角在Y轴的偏移量
     * >=
     * ScrollView 的第一个子控件的高度与ScrollView的高度差
     * 时,进行拦截
     *
     * @param child
     * @return
     */
    private boolean scrollViewLoadMoreIntercept(View child) {
        boolean intercept = false;
        ScrollView scrollView = (ScrollView) child;
        View scrollChild = scrollView.getChildAt(0);

        if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判断
     * RecyclerView刷新是否拦截
     * <p>
     * 当RecyclerView 当前滑过的距离 <= 0时  进行拦截
     * <p>
     * computeVerticalScrollExtent()是当前屏幕显示的区域高度
     * computeVerticalScrollOffset() 是当前屏幕之前滑过的距离
     * computeVerticalScrollRange()是整个View控件的高度
     *
     * @param child
     * @return
     */
    private boolean recyclerViewRefreshIntercept(View child) {
        boolean intercept = false;

        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollOffset() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * 判断
     * RecyclerView加载更多是否拦截
     * <p>
     * 当RecyclerView 当前显示的高度 与 当前滑过的距离之和
     * >=
     * 整个RecyclerView的高度 时 进行拦截
     *
     * @param child
     * @return
     */
    private boolean recyclerViewLoadMoreIntercept(View child) {
        boolean intercept = false;

        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange()) {
            intercept = true;
        }

        return intercept;
    }
    /////////////////////////////////////////////////////判断是否进行拦截////////////////////////////////////////

    /**
     * 滑动状态
     */
    enum Status {
        TRY_REFRESH,//刷新状态
        TRY_LOADMORE,//加载更多状态
        REFRESHING,//刷新中
        LOADING,//加载中

        NORMAL  //正常状态
    }

    /**
     * 更新 滑动状态
     *
     * @param status
     */
    private void updateStatus(Status status) {
        mStatus = status;
    }

    /////////////////////////////////////////////////////修改头 尾布局的状态////////////////////////////////////////

    /**
     * 滑动时
     *
     * @param dy
     */
    public void beforeRefreshing(float dy) {
        //计算旋转角度
        int scrollY = Math.abs(getScrollY());
        scrollY = scrollY > mHeaderHeight ? mHeaderHeight : scrollY;
        float angle = (float) (scrollY * 1.0 / mHeaderHeight * 180);
        mHeaderArrow.setRotation(angle);


        if (getScrollY() <= -mHeaderHeight) {
            mHeaderText.setText("松开刷新");
        } else {
            mHeaderText.setText("下拉刷新");
        }
    }

    public void beforeLoadMore() {
        if (getScrollY() >= mHeaderHeight) {
            mFooterText.setText("松开加载更多");
        } else {
            mFooterText.setText("上拉加载更多");
        }
    }

    /**
     * 刷新结束
     */
    public void refreshFinished() {
        scrollTo(0, 0);
        mHeaderText.setText("下拉刷新");
        mHeaderProgressBar.setVisibility(GONE);
        mHeaderArrow.setVisibility(VISIBLE);
        updateStatus(Status.NORMAL);
    }

    /**
     * 加载更多结束
     */
    public void loadMoreFinished() {
        mFooterText.setText("上拉加载");
        mFooterProgressBar.setVisibility(GONE);
        scrollTo(0, 0);
        updateStatus(Status.NORMAL);
    }

    /**
     * 下拉刷新  未达到有效长度
     */
    private void releaseWithStatusTryRefresh() {
        scrollBy(0, -getScrollY());
        mHeaderText.setText("下拉刷新");
        updateStatus(Status.NORMAL);
    }

    /**
     * 上拉加载更多
     */
    private void releaseWithStatusTryLoadMore() {
        scrollBy(0, -getScrollY());
        mFooterText.setText("上拉加载更多");
        updateStatus(Status.NORMAL);
    }

    /**
     * 下拉刷新 达到有效长度
     */
    private void releaseWithStatusRefresh() {
        scrollTo(0, -mHeaderHeight);
        mHeaderProgressBar.setVisibility(VISIBLE);
        mHeaderArrow.setVisibility(GONE);
        mHeaderText.setText("正在刷新");
        updateStatus(Status.REFRESHING);
    }

    /**
     * 上拉加载更多  达到有效长度
     */
    private void releaseWithStatusLoadMore() {
        scrollTo(0, mFooterHeight);
        mFooterText.setText("正在加载");
        mFooterProgressBar.setVisibility(VISIBLE);
        updateStatus(Status.LOADING);
    }
    /////////////////////////////////////////////////////修改头 尾布局的状态////////////////////////////////////////

}