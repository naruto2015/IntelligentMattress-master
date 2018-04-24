package com.rt.sm.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rt.sm.activity.mattress.MattressPrepareActivity;

import java.util.List;

/**
 * 作者：Haohw on 2017/12/25
 */

public abstract class BaseFragment extends Fragment {
    public Context context;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        //2. 设置监听
        initListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = View.inflate(getActivity(), getLayoutResId(), null);
        //1. 查找控件
        initView(view);
        return view;
    }

    //ftamgnet懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.d("TAG", "fragment->setUserVisibleHint");
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    //
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        Log.e("sfdfsdfdd", "lazyLoad: ");
        //Log.d("TAG", getClass().getName() + "->initData()");
//        if(getActivity()!= SearchActivity.activity) {
        //设置数据
        initData();
//        }
        isFirst = false;
    }

    //fragment被设置为不可见时调用
    public abstract void onInvisible();

    /**
     * 获取当前fragment对应的布局id
     *
     * @return
     */
    public abstract int getLayoutResId();

    /**
     * 初始化view:
     * 查找控件
     *
     * @param view
     */
    public abstract void initView(View view);

    /**
     * 初始化监听
     */
    public abstract void initListener();

    /**
     * 初始化数据
     * 给控件设置内容
     */
    public abstract void initData();

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
//        initData();
    }


    /**
     * 跳转界面
     */
    public void changeActivity(Class<?> clz) {
        Intent intent = new Intent(context, clz);
        startActivity(intent);

    }


    /**
     * 跳转界面
     */
    public void changeActivityWidthParam(Class<?> clz, String p1,String title) {
        Intent intent = new Intent(context, clz);
        intent.putExtra("url",p1);
        intent.putExtra("title",title);
        startActivity(intent);

    }



}
