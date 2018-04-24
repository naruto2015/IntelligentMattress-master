package com.rt.sm.activity.member;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
import com.rt.sm.adapter.MattressAdapter;
import com.rt.sm.adapter.MemberAdapter;
import com.rt.sm.bean.MattressBean;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.BaseObserver;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.internet.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberListActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<MemberBean> memberBeans=null;

    private MemberAdapter adapter=null;

    String session_token;

    @Override
    protected int bindLayout() {
        return R.layout.activity_member_list;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        memberBeans=new ArrayList<>();
        adapter=new MemberAdapter(context,memberBeans,R.layout.member_item);
        recyclerView.setAdapter(adapter);
        initData();


    }

    private void initData() {

        List<MemberBean> memberBeanList = DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        session_token=memberBeanList.get(0).getSession_token();



    }




    @OnClick(R.id.img_add)
    public void addMember(){
        changeActivity(AddMemberActivity.class);
    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();

        RetrofitUtils.getInstance(context)
                .api
                .getHouseholdList(session_token)
                .compose(RxSchedulers.<ResultData<List<MemberBean>>>compose())
                .subscribe(new BaseObserver<List<MemberBean>>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<List<MemberBean>> resultData) {

                        if(resultData.status.equals("success")){
                            memberBeans=resultData.data;
                            adapter.setData(memberBeans);
                            adapter.notifyDataSetChanged();
                        }



                    }
                });

    }



}
