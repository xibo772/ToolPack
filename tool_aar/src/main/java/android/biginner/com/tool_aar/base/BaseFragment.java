package android.biginner.com.tool_aar.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * android 3.0
 */
public abstract class BaseFragment extends Fragment {
    private boolean isFirstLoad = true;//初始化变量
    private View rootView;//根View+

    /**
     * 设置 Fragment 可见/不可见 时调用；
     * 可在 此方法中调用 getUserVisibleHint() 获取Fragment 状态是否可见
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad && isVisibleToUser) {
            onUploadData();//数据加载操作
            isFirstLoad = false;//改变变量的值
        }
    }

    /**
     * Fragment和Activity建立关联的时候调用
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 创建Fragment的时候调用
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateBaseFragment(savedInstanceState);
    }

    /**
     * Fragment加载布局时调用
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //如果根View ==null 说明为初始化过
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            //初始化控件
            onInitView(rootView, savedInstanceState);
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        //如果Fragment可见，进行数据加载
        if (getUserVisibleHint()) {
            //数据加载
            onUploadData();
            isFirstLoad = false;
        }
        return rootView;
    }

    /**
     * 当Activity中的onCreate方法执行完后调用
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Fragment中的布局被移除时调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = false;//视图销毁将变量置为false
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Fragment和Activity解除关联的时候调用
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }
///////////////////////////////////////////////////////原生方法 👆//////////////////////////////////


////////////////////////////////////自定义方法 👇 //////////////////////////////////////////////////

    /**
     * 常见 Fragment 调用
     * on fragment create.
     *
     * @param savedInstanceState
     */
    protected abstract void onCreateBaseFragment(Bundle savedInstanceState);

    /**
     * 设置添加 Fragment 布局
     *
     * @return return a fragment layout id.
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     * abstract class, called after fragment view created
     *
     * @param rootView
     * @param savedInstanceState
     */
    protected abstract void onInitView(View rootView, Bundle savedInstanceState);

    /**
     * 数据加载
     */
    protected abstract void onUploadData();


    /////////////////////////////////////简化初始化控件 👇 /////////////////////////////////////////
    //简化初始化控件
    protected <V extends View> V findView(@IdRes int id, Class<V> vClass) {
        if (rootView == null)
            return null;
        else
            return (V) rootView.findViewById(id);
    }

    //简化初始化控件，并带有简单的操作，如返回按钮（不影响其他控件）,
    //提供返回值是为了防止异常需求，如进入界面展示几秒钟之后才允许返回上一层界面
    protected <V extends View> V initView(@IdRes int id, Class<V> vClass, Back back) {
        if (rootView == null)
            return null;
        V v = (V) rootView.findViewById(id);
        if (v == null || back == null)
            return null;
        back.run(v);
        return v;
    }

    //初始化控件回调接口
    interface Back<V extends View> {
        void run(V v);
    }

    //简化初始化控件---设置TextView 类控件
    protected TextView setText(@IdRes int id, CharSequence sequence) {
        if (rootView == null)
            return null;
        TextView textView = (TextView) rootView.findViewById(id);
        if (textView == null)
            return null;
        textView.setText(sequence);
        return textView;
    }

    //简化初始化控件----设置 ImageView 类控件
    protected ImageView setImage(@IdRes int id, @DrawableRes int resId) {
        if (rootView == null)
            return null;
        ImageView imageView = (ImageView) rootView.findViewById(id);
        if (imageView == null)
            return null;
        imageView.setImageResource(resId);
        return imageView;
    }


}
