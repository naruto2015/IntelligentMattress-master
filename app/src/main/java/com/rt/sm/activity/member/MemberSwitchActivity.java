package com.rt.sm.activity.member;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
import com.rt.sm.adapter.MemberSwitchAdapter;
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

public class MemberSwitchActivity extends BaseActivity {



    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<MemberBean> memberBeans=null;

    private MemberSwitchAdapter adapter=null;

    private String sessionToken;


    @Override
    protected int bindLayout() {
        return R.layout.activity_member_switch;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        List<MemberBean> memberBeanList = DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        sessionToken=memberBeanList.get(0).getSession_token();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        memberBeans=new ArrayList<>();
        adapter=new MemberSwitchAdapter(context,memberBeans,R.layout.member_switch_item);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemOnClicker(new MemberSwitchAdapter.OnItemOnClicker() {
            @Override
            public void OnItemClickListner(int position) {

                for(int i=0;i<memberBeans.size();i++){

                    if(position==i){
                        memberBeans.get(i).ismanage=1;
                    }else {
                        memberBeans.get(i).ismanage=0;
                    }

                }
                adapter.setData(memberBeans);
                adapter.notifyDataSetChanged();

                setSleeper(position);





            }
        });


        initData();



    }


    private void setSleeper(int position){

        MemberBean memberBean=new MemberBean();
        memberBean.mattresno=getIntent().getStringExtra("mattresno");
        memberBean.type=2+"";
        memberBean.side=getIntent().getStringExtra("side");
        memberBean.avatar=memberBeans.get(position).avatar;
        memberBean.householdacc=memberBeans.get(position).householdacc;
        memberBean.householdname=memberBeans.get(position).householdname;

        RetrofitUtils.getInstance(context)
                .api
                .updateMattresHousehold(sessionToken,memberBean)
                .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                .subscribe(new BaseObserver<MemberBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MemberBean> resultData) {
                        if(resultData.status.equals("success")){

                        }


                    }
                });

    }



    private void initData() {

        RetrofitUtils.getInstance(context)
                .api
                .getHouseholdList(sessionToken)
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

    @OnClick(R.id.close)
    void back(){
        finish();
    }


}
