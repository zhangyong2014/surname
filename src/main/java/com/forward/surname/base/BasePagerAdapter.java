package com.forward.surname.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zy on 2017/7/19.
 */
public class BasePagerAdapter extends PagerAdapter{
    private List<View> mListViews;

    public BasePagerAdapter(List<View> mListViews) {
        this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
    }



    public void setData(List<View> mListViews){
        this.mListViews = mListViews;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) 	{
        if(position<=mListViews.size()-1)
        container.removeView(mListViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=mListViews.get(position);
        ViewGroup parent = (ViewGroup) view.getParent();
        if(parent!=null)
            parent.removeView(view);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return  mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;//官方提示这样写
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
