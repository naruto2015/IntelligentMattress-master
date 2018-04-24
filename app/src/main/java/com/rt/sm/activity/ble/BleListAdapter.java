package com.rt.sm.activity.ble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rt.sm.R;

import java.util.List;

/**
 * 作者:Created by haohw on 2018/1/24.
 * 邮箱:303729942@qq.com
 * Blog:http://blog.csdn.net/qq_31660173
 */

public class BleListAdapter extends BaseAdapter {
    private List<BleBean> list;
    private Context c;

    public BleListAdapter(List<BleBean> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(c).inflate(R.layout.item_ble_list, viewGroup, false);
        TextView name = view.findViewById(R.id.name);
        TextView mac = view.findViewById(R.id.mac);
        name.setText("Device: " + list.get(i).getName());
        mac.setText("Mac: " + list.get(i).getMac());
        return view;
    }
}
