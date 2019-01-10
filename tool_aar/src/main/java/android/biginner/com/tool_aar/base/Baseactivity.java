package android.biginner.com.tool_aar.base;

import android.app.Activity;
import android.biginner.com.tool_aar.R;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class Baseactivity extends AppCompatActivity {

    protected Context mBaseContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseContext = this;
        setContentView(R.layout.base_activity);

        View decorView = getWindow().getDecorView();
        LinearLayout context = (LinearLayout) ((ViewGroup) decorView.findViewById(android.R.id.content)).getChildAt(0);
        context.addView(getLayoutInflater().inflate(getLayoutId(), context, false));

        if (hideBar())
            findViewById(R.id.layout_bar).setVisibility(View.GONE);

        setText(R.id.tv_title, setTitle());
        findViewById(R.id.ibtn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackListener();
            }
        });

        this.initViews(savedInstanceState);
        this.initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destory();
    }

    protected abstract int getLayoutId();


    protected abstract String setTitle();

    protected abstract void initViews(Bundle savedInstanceState);


    protected abstract void initData();

    protected abstract void destory();

    protected void setBackListener() {
        finish();
    }

    protected boolean hideBar() {
        return false;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        /**
         *  androidmanifest.xml  中activity中添加属性 android:configChanges="orientation|keyboardHidden
         *  解决  Activity 切换导致的 onCreate 重复执行
         */
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            //land
        } else if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT) {
            //port
        }
    }

    protected <V extends View> V findView(@IdRes int id) {
        return (V) this.findViewById(id);
    }


    protected void setText(@IdRes int id, CharSequence sequence) {
        TextView textView = (TextView) findViewById(id);
        if (textView != null)
            textView.setText(sequence);
    }

    protected void setImage(@IdRes int id, @DrawableRes int image) {
        ImageView imageView = (ImageView) findViewById(id);
        if (imageView != null)
            imageView.setImageResource(image);
    }

    /**
     * 跳转 页面
     *
     * @param clazz
     */
    //单纯跳转页面
    protected void startActivity(Class<?> clazz) {
        this.startActivity(clazz, null, 0);
    }

    //跳转 传参
    protected void startActivity(Class<?> clazz, Bundle bundle) {
        this.startActivity(clazz, bundle, 0);
    }

    //跳转 返回
    protected void startActivity(Class<?> clazz, int requestCode) {
        this.startActivity(clazz, null, requestCode);
    }

    //跳转 传参 返回
    protected void startActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode == 0) {
            this.startActivity(intent);
        } else {
            this.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 接受 返回值
     *
     * @param requestCode 跳转页面标识
     * @param resultCode  返回码
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            backResult(data, requestCode);
        }
    }

    protected void backResult(Intent data, int requestCode) {
        if (data == null) {
            Toast.makeText(this, "返回信息为空", Toast.LENGTH_SHORT).show();
        }
    }


}
