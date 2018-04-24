package com.rt.sm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rt.sm.common.BaseFragment;

import java.util.List;

/**
 * Created by haohongwei on 2017/12/25.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> list;

    public FragmentAdapter(List<BaseFragment> list, FragmentManager fm) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
