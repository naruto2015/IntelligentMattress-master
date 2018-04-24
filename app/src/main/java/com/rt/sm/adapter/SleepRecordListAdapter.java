package com.rt.sm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.rt.sm.R;
import com.rt.sm.bean.Fragment2Bean;

import java.util.List;

/**
 * Created by haohw on 2018/1/11.
 * <p>
 * ----------Dragon be here!----------/
 * 　　　┏┓　　 ┏┓
 * 　　┏┛┻━━━┛┻┓━━━
 * 　　┃　　　　　 ┃
 * 　　┃　　　━　  ┃
 * 　　┃　┳┛　┗┳
 * 　　┃　　　　　 ┃
 * 　　┃　　　┻　  ┃
 * 　　┃　　　　   ┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 */

public class SleepRecordListAdapter extends BaseExpandableListAdapter {
    private Context c;
    private List<Fragment2Bean> list;

    public SleepRecordListAdapter(Context c, List<Fragment2Bean> list) {
        this.c = c;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).getList().size();
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return list.get(i).getList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ParentHolder parentHolder = null;
        if (view == null) {
            parentHolder = new ParentHolder();
            view = LayoutInflater.from(c).inflate(R.layout.item_sleep_record_parent, viewGroup, false);
            view.setTag(parentHolder);
        } else {
            parentHolder = (ParentHolder) view.getTag();
        }
        return view;
    }

    class ParentHolder {

    }

    class ChildHolder {

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder childHolder = null;
        if (view == null) {

            childHolder = new ChildHolder();
            Fragment2Bean.ListBean item = (Fragment2Bean.ListBean) getChild(i,i1);

            if(item.childtype == 1){
                view = LayoutInflater.from(c).inflate(R.layout.sleep_detail, viewGroup, false);
            }else {
                view = LayoutInflater.from(c).inflate(R.layout.item_sleep_record_child, viewGroup, false);
            }

            view.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) view.getTag();
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
