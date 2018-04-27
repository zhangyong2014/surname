package com.forward.surname.base;

/**
 * Created by zy on 2017/11/2.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by yuandl on 2016-10-13.
 * 万能适配器
 */

public abstract class BaseAdapter0<T> extends android.widget.BaseAdapter {
    public Context context;
    private List<T> datas;
    private int layoutId;

    public BaseAdapter0(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterHolder viewHolder = AdapterHolder.get(convertView, parent, layoutId, position);
        T t = getItem(position);
        convertView(viewHolder, t);
        return viewHolder.getConvertView();
    }

    /**
     * 局部更新数据，调用一次getView()方法；Google推荐的做法
     *
     * @param listView 要更新的listview
     * @param position 要更新的位置
     */
    public void notifyDataSetChanged(ListView listView, int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }
    }

    /**
     * 需要去实现的对item中的view的设置操作
     * @param adapterHolder
     * @param t
     */
    protected abstract void convertView(AdapterHolder adapterHolder, T t);

}