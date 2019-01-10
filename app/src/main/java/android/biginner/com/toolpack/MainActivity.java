package android.biginner.com.toolpack;

import android.annotation.SuppressLint;
import android.biginner.com.tool_aar.base.BaseRecyclerAdapter;
import android.biginner.com.tool_aar.base.Baseactivity;
import android.biginner.com.tool_aar.custom.PullLayout;
import android.biginner.com.tool_aar.network.HttpService;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Baseactivity {


    private RecyclerView tab_title;
    private ViewPager vp;
    private Adapter adapter;
    private ArrayList<View> list;
    private ArrayList<String> listTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tab_title = findView(R.id.tab_title);
        vp = findView(R.id.vp);
        tab_title.setLayoutManager(new LinearLayoutManager(mBaseContext,LinearLayoutManager.HORIZONTAL, false));


    }

    class Adapter extends BaseRecyclerAdapter<String>{
        public Adapter(Context mContext) {
            super(mContext);
        }

        @Override
        protected int getLayoutID() {
            return  R.layout.item_title;
        }
        public int select=0;

        public void setSelect(int select) {
            this.select = select;
            notifyDataSetChanged();
        }
        @Override
        protected void convert(Holder holder, String s, int position) {
            if (select==position){
                holder.initView(R.id.tv_title,TextView.class).setTextColor(Color.RED);
            }else
            {
                holder.initView(R.id.tv_title,TextView.class).setTextColor(Color.BLACK);
            }
            holder.setText(R.id.tv_title, s);
            int itemCount = getItemCount();
            holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(1080/itemCount,ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vp.getCurrentItem() != position) {
                        vp.setCurrentItem(position);
                        setSelect(position);
                    }
                }
            });
        }
    }
    @Override
    protected void initData() {
        adapter = new Adapter(mBaseContext);
        tab_title.setAdapter(adapter);


        list = new ArrayList<>();
        listTitle = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < 3; i++) {
            View view = inflater.inflate(R.layout.item_titlt, null);
            ((TextView)view.findViewById(R.id.tv_title)).setText("i = "+i);
            list.add(view);
            listTitle.add("i"+i);
        }
        adapter.setDataList(listTitle);

        vp.setAdapter(new MyPagerAdapter(list));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.setSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void destory() {

    }


    public class MyPagerAdapter extends PagerAdapter {

        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }


}
