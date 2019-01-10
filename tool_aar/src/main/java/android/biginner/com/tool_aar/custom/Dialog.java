package android.biginner.com.tool_aar.custom;

import android.biginner.com.tool_aar.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


/**
 * 自定义弹窗
 */
public class Dialog {
    private Context mContext;
    private View mView;

    private boolean cancel;

    private int mWidth;
    private int mHeight;
    private AlertDialog dialog;

    private Dialog(Context mContext) {
        this.mContext = mContext;
    }

    private void show() {
        //创建弹窗
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //设置 自定义布局
        dialog = builder.setView(mView)
                .create();
        //设置 点击外部是否取消
        dialog.setCanceledOnTouchOutside(cancel);
        //显示弹窗
        dialog.show();
        //设置 弹窗大小(宽高)----show之后设置才有效
        if (mHeight != 0 && mWidth != 0) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.height = mHeight;
            params.width = mWidth;
            dialog.getWindow().setAttributes(params);
        } else if (mHeight != 0) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.height = mHeight;
            dialog.getWindow().setAttributes(params);
        } else if (mWidth != 0) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = mWidth;
            dialog.getWindow().setAttributes(params);
        }
        //设置 背景透明
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 弹窗消失
     */
    public void dismiss() {
        mContext = null;
        mView = null;
        dialog.dismiss();
    }

    /**
     * 自己传入布局样式
     */
    public static class Builder {
        private Context mContext;
        private View mView;
        private Dialog dialog;
        private int mWidth;
        private int mHeight;
        private boolean cancel;

        public Builder(Context context) {
            mContext = context;
            if (dialog == null) {
                dialog = new Dialog(mContext);
            }
        }

        /**
         * 设置布局样式
         * @param resID
         * @param callBack  设置回调,初始化布局控件以及相应的事件处理
         * @return
         */
        public Builder setLayoutID(@LayoutRes int resID, CallBack callBack) {
            mView = LayoutInflater.from(mContext).inflate(resID, null);
            if (callBack != null) {
                callBack.run(mView, dialog);
            }
            return this;
        }

        /**
         * 设置弹窗的大小尺寸
         * @param width
         * @param height
         * @return
         */
        public Builder setSize(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
            return this;
        }

        /**
         * 设置点击弹窗外部是否消失
          * @param cancel
         * @return
         */
        public Builder setCanceledOnTouchOutside(boolean cancel) {
            this.cancel = cancel;
            return this;
        }

        /**
         * 展示弹窗
         * @return
         */
        public Dialog show() {
            dialog.mView = mView;
            dialog.mWidth = mWidth;
            dialog.mHeight = mHeight;
            dialog.cancel = cancel;
            dialog.show();
            mContext = null;
            mView = null;
            return dialog;
        }
    }

    /**
     * 统一的样式布局,
     * 不需要传入布局,
     * 直接修改内容即可
     * 样式布局:  dialog_base.xml
     */
    public static class Builder2 {

        private Context mContext;
        private View mView;
        private Dialog dialog;
        private int mWidth;
        private int mHeight;
        private boolean cancel;
        private TextView mTv_content;
        private TextView mTv_title;
        private Button mBtnLeft;
        private Button mBtnRight;

        public Builder2(Context context) {
            mContext = context;
            if (dialog == null) {
                dialog = new Dialog(mContext);
            }
            initView();
        }

        /**
         * 初始化控件
         */
        private void initView() {
            mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_base, null);
            //顶部标题
            mTv_title = (TextView) mView.findViewById(R.id.tv_dialog_title);
            //内容部分
            mTv_content = (TextView) mView.findViewById(R.id.tv_dialog_content);
            //左按钮
            mBtnLeft = (Button) mView.findViewById(R.id.btn_dialog_left);
            //右按钮
            mBtnRight = (Button) mView.findViewById(R.id.btn_dialog_right);
        }

        /**
         * 设置弹窗内容
         * @param sequence   内容
         * @param dpsize   文字大小 (dp)
         * @param color    文字颜色
         * @return
         */
        public Builder2 setContent(CharSequence sequence, int dpsize, @ColorInt int color){
            mTv_content.setText(sequence);
            mTv_content.setTextColor(color);
            mTv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP,dpsize);
            return this;
        }

        /**
         * 设置弹窗内容
         * @param sequence
         * @return
         */
        public Builder2 setContent(CharSequence sequence){
            mTv_content.setText(sequence);
            return this;
        }

        /**
         * 设置弹窗标题
         * @param sequence 标题
         * @param dpsize  标题文字大小(dp)
         * @param color   标题文字颜色
         * @return
         */
        public Builder2 setTitle(CharSequence sequence, int dpsize, @ColorInt int color){
            mTv_title.setVisibility(View.VISIBLE);
            mTv_title.setText(sequence);
            mTv_title.setTextColor(color);
            mTv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,dpsize);
            return this;
        }
        /**
         * 设置弹窗标题
         * @param sequence 标题
         * @return
         */
        public Builder2 setTitle(CharSequence sequence){
            mTv_title.setVisibility(View.VISIBLE);
            mTv_title.setText(sequence);
            return this;
        }

        /**
         * 设置左按钮
         * @param sequence
         * @param listener
         * @return
         */
        public Builder2 setButtonLeft(CharSequence sequence, final ClickListener listener){
            mBtnLeft.setText(sequence);
            mBtnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onClick(v);
                    }
                }
            });
            return this;
        }

        /**
         * 设置右侧按钮
         * @param sequence
         * @param listener
         * @return
         */
        public Builder2 setButtonRight(CharSequence sequence, final ClickListener listener){
            mBtnRight.setText(sequence);
            mBtnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (listener != null) {
                        listener.onClick(v);
                    }
                }
            });
            return this;
        }

        /**
         * 设置 弹窗尺寸大小
         * @param width
         * @param height
         * @return
         */
        public Builder2 setSize(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
            return this;
        }

        /**
         * 设置弹窗外部点击是否消失
         * @param cancel
         * @return
         */
        public Builder2 setCanceledOnTouchOutside(boolean cancel) {
            this.cancel = cancel;
            return this;
        }

        /**
         * 展示弹窗
         * @return
         */
        public Dialog show() {
            dialog.mView = mView;
            dialog.mWidth = mWidth;
            dialog.mHeight = mHeight;
            dialog.cancel = cancel;
            dialog.show();
            mContext = null;
            mView = null;
            return dialog;
        }
    }
    public interface ClickListener{
        void  onClick(View view);
    }

    public interface CallBack {
        void run(View view, Dialog dialog);
    }
}
