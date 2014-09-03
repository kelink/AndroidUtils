package com.app.common.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter基类，使用方法如下：
 * public class TestFoodListAdapter extends SimpleBaseAdapter<String> {
 * 
 *     public TestFoodListAdapter(Context context, List<String> datalist) {
 *         super(context, datalist);
 *     }
 * 
 *     @Override
 *     public int getItemResource() {
 *         return R.layout.listitem_test;
 *     }
 * 
 *     @Override
 *     public View getItemView(int position, View convertView, ViewHolder holder) {
 *         TextView text = holder.getView(R.id.text);
 *         text.setText(getItem(position));
 *         return convertView;
 *     }
 * }
 * 
 * 
 * */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {
    public SimpleBaseAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList == null ? new ArrayList<T>() : new ArrayList<T>(dataList);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= mDataList.size()){
        	return null;
        }
        
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 该方法需要子类实现，需要返回item布局的resource id
     * 
     * @return
     */
    public abstract int getItemResource();

    /**
     * 使用该getItemView方法替换原来的getView方法，需要子类实现
     * 
     * @param position
     * @param convertView
     * @param parent
     * @param holder
     * @return
     */
    public abstract View getItemView(int position, View convertView, ViewHolder holder);

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(mContext, getItemResource(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return getItemView(position, convertView, holder);
    }

    public class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;
		
        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }

    public void addAll(List<T> elem) {
    	mDataList.addAll(elem);
        notifyDataSetChanged();
    }
	
    public void remove(T elem) {
    	mDataList.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
    	mDataList.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
    	mDataList.clear();
    	mDataList.addAll(elem);
        notifyDataSetChanged();
    }
    
    protected Context mContext = null;
    protected List<T> mDataList = null;
}
