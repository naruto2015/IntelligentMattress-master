package com.rt.sm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.rt.sm.R;
import com.rt.sm.adapter.NotifyAdapter;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private NotifyAdapter adapter=null;

    private List<MemberBean> memberBeans;

    @Override
    protected int bindLayout() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        memberBeans=new ArrayList<>();
        for(int i=0;i<5;i++){
            memberBeans.add(new MemberBean());
        }

        adapter=new NotifyAdapter(this,memberBeans,R.layout.notify_item);
        recyclerView.setAdapter(adapter);




    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }



}
