package android.biginner.com.tool_aar.custom;

import android.biginner.com.tool_aar.R;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.PopupWindow;


/**
 * 省市区 三级联动
 * <p>
 * 底部弹出
 * <p>
 * 创建人 : 黄卫志
 * 创建时间 : 2018/7/28/028.
 */

public class CityPopuWindow implements View.OnClickListener, NumberPicker.OnValueChangeListener {
    private Context context;
    private NumberPicker npPro;
    private NumberPicker npCity;
    private NumberPicker npDistance;
    private PopupWindow mPopupWindow;
    private String[] province;
    private String[] city;
    private String[] distance;
    private OnListener listener;
    private boolean showDistance;

    public CityPopuWindow(Context context, String[] province, OnListener listener, boolean showDistance) {
        this.context = context;
        this.listener = listener;
        this.showDistance = showDistance;
        this.province = province;
    }

    public static class Builder {
        private Context context;
        private OnListener listener;
        private boolean showDistance;
        private String[] province;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setProvince(String[] province) {
            this.province = province;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setListener(OnListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setShowDistance(boolean showDistance) {
            this.showDistance = showDistance;
            return this;
        }

        public CityPopuWindow build() {
            if (province == null) {
                province = new String[]{"河北省" + "山西省" + "吉林省" + "辽宁省"
                        + "陕西省" + "甘肃省" + "青海省" + "山东省" + "福建省"
                        + "浙江省" + "台湾省" + "河南省" + "湖北省" + "湖南省"
                        + "江西省" + "江苏省" + "安徽省" + "广东省" + "海南省"
                        + "四川省" + "贵州省" + "云南省" + "黑龙江省"
                        + "北京市" + "上海市" + "天津市" + "重庆市"
                        + "内蒙古自治区" + "新疆维吾尔自治区" + "宁夏回族自治区" + "广西壮族自治区" + "西藏自治区"
                        + "香港特别行政区" + "澳门特别行政区"
                };
            }
            return new CityPopuWindow(context, province, listener, showDistance);
        }
    }

    public void show() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.popu_select_city, null);
        //初始化控件
        initView(inflate);

        //初始化设置popuWindow
        initPopu(inflate);


        if (province != null) {
            npPro.setDisplayedValues(province);
            npPro.setMinValue(0);
            npPro.setMaxValue(province.length - 1);
            npPro.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            setChangeCity(0);
        }


    }

    private void initPopu(View inflate) {
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mPopupWindow.setHeight(MyApplication.screenHeight / 3);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);//点击其他位置消失
        mPopupWindow.showAtLocation(inflate.getRootView(), Gravity.BOTTOM, 0, 0);
    }

    private void initView(View inflate) {
        npPro = (NumberPicker) inflate.findViewById(R.id.np_pro);
        npCity = (NumberPicker) inflate.findViewById(R.id.np_city);
        npDistance = (NumberPicker) inflate.findViewById(R.id.np_distance);
        npPro.setOnValueChangedListener(this);
        npCity.setOnValueChangedListener(this);
        npDistance.setOnValueChangedListener(this);
        (inflate.findViewById(R.id.btn_cancel)).setOnClickListener(this);
        (inflate.findViewById(R.id.btn_post)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_post) {
            if (listener != null)
                listener.getSelect(
                        province == null ? "" : province[npPro.getValue()],
                        city == null ? "" : city[npCity.getValue()],
                        distance == null ? "" : distance[npDistance.getValue()],
                        npPro.getValue(), npCity.getValue(), npDistance.getValue());

            dismiss();

        } else if (i == R.id.btn_cancel) {
            dismiss();

        } else {
        }
    }

    private void dismiss() {
        try {
            mPopupWindow.dismiss();
            context = null;
            npPro = null;
            npCity = null;
            npDistance = null;
            mPopupWindow = null;
            province = null;
            city = null;
            distance = null;
            listener = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        int i = picker.getId();
        if (i == R.id.np_pro) {
            setChangeCity(picker.getValue());
        } else if (i == R.id.np_city) {
            setChangeDistance(picker.getValue());
        } else if (i == R.id.np_distance) {
        }
    }

    private void setChangeCity(int index) {
        if (listener == null) {
            return;
        }
        city = listener.changeProvince(province, index);
        if (city == null) {
            return;
        }


        npCity.setDisplayedValues(null);
        npCity.setMinValue(0);
        npCity.setMaxValue(city.length - 1);
        npCity.setDisplayedValues(city);
        npCity.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        setChangeDistance(0);
    }

    private void setChangeDistance(int index) {
        if (listener == null) {
            return;
        }
        distance = listener.changeCity(province, npPro.getValue(), city, index);
        if (distance == null) {
            return;
        }

        npDistance.setDisplayedValues(null);
        npDistance.setMinValue(0);
        npDistance.setMaxValue(distance.length - 1);
        npDistance.setDisplayedValues(distance);
        npDistance.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    interface OnListener {
        //改变省份回调
        String[] changeProvince(String[] province, int value);

        //改变城市回调
        String[] changeCity(String[] province, int proValue, String[] city, int cityValue);

        //点击确定之后的回调，当只有两级时，strDistance 为 "",iDistance为-1；
        void getSelect(String strProvince, String strCity, String strDistance, int iProvince, int iCity, int iDistance);
    }

}
