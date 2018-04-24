package com.rt.sm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;



import java.util.List;

/**
 * Created by gaodesong on 17/11/27.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    private Context context;

    private List<T> data;

    private int layoutId;


    public BaseAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }


    public void setData(List<T> data) {
        this.data = data;
    }
    public List<T> getData() {
        return this.data;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return BaseHolder.getHolder(context,parent,layoutId);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {

        onBind(holder,data.get(position),position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    //抽象方法，让子类继承实现
    public abstract void onBind(BaseHolder holder, T t, int position);



}
