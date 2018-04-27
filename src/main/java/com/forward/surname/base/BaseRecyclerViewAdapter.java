package com.forward.surname.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by zy on 2017/11/19.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    private Context context;
    protected List<T> datas;
    private int layoutId;
    public  BaseRecyclerViewAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convertView(holder.itemView, position);
    }



    /**
     * 局部更新数据，调用一次getView()方法；Google推荐的做法
     *
     * @param recyclerView 要更新的recyclerView
     * @param position 要更新的位置
     */
    public void notifyDataSetChanged(RecyclerView recyclerView, int position) {
        LinearLayoutManager  layoutManager= (LinearLayoutManager)recyclerView.getLayoutManager();

        /**第一个可见的位置**/
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = recyclerView.getChildAt(position - firstVisiblePosition);
            convertView(view, position);
        }
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    /**
     * 需要去实现的对item中的view的设置操作
     * @param item
     * @param postion
     */
    protected abstract void convertView(View item, int postion);

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }
    }
}
