package android.biginner.com.tool_aar.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
//未重新设置
/**
 * 创建人 : 黄卫志
 * 创建时间 : 2018/6/4/004.
 */
public class BasePagerAdapter extends PagerAdapter {
    private List<View> mList=null;
    private List<String> mListTitle=null;

    public BasePagerAdapter(List<View> mList, List<String> mListTitle) {
        this.mList = mList;
        this.mListTitle = mListTitle;
    }

    @Override
    public int getCount() {
        return mList==null||mList.isEmpty()?0:mList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        container.removeView(mList.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mListTitle.get(position);
    }
}
