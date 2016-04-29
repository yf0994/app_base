package com.category.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Author:fengyin
 * Date: 16-3-18    09:31
 * Email:594601408@qq.com
 * LastUpdateTime: 16-3-18
 * LastUpdateBy:594601408@qq.com
 */
public abstract class ListViewBaseAdapter<T, V> extends BaseAdapter {
    private List<T> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private V mHolder;
    private int mResId;

    public ListViewBaseAdapter(Context context, List<T> list, int resId){
        mList = list;
        mContext = context;
        mResId = resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(mResId, null);
            mHolder = initView(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (V) convertView.getTag();
        }
        initData(position, mHolder, mList.get(position));
        return convertView;
    }

    protected abstract V initView(View convertView);

    protected abstract void initData(int position, V mHolder, T t);


    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public void addListItem(T t){
        mList.add(t);
        updateListView();
    }

    public void addList(List<T> list){
        mList.addAll(list);
        updateListView();
    }

    public void clearList(){
        mList.clear();
        updateListView();
    }


    private void updateListView(){
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }
}
