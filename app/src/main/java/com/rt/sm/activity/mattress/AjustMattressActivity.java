package com.rt.sm.activity.mattress;



import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rt.sm.R;
import com.rt.sm.adapter.MattressAdapter;
import com.rt.sm.adapter.MattressSwitchAdapter;
import com.rt.sm.bean.DataSynEvent;
import com.rt.sm.bean.MattressBean;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.BaseObserver;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.internet.RxSchedulers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AjustMattressActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MattressSwitchAdapter adapter;

    private List<MattressBean> mattressBeans;

    private String sessiontoken;

    @Override
    protected int bindLayout() {
        return R.layout.activity_ajust_mattress;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mattressBeans=new ArrayList<>();
        adapter=new MattressSwitchAdapter(context,mattressBeans,R.layout.switch_mattress_item);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MattressSwitchAdapter.OnItemClickListener() {
            @Override
            public void OnItemCLickListener(int position) {
                updateDefaultMattres(position);

            }
        });

        initData();

    }

    private void updateDefaultMattres(int position) {
        RetrofitUtils.getInstance(context)
                .api
                .updateDefaultMattres(sessiontoken,mattressBeans.get(position).mattresno)
                .compose(RxSchedulers.<ResultData<MattressBean>>compose())
                .subscribe(new BaseObserver<MattressBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MattressBean> resultData) {
                        if(resultData.status.equals("success")){
                           initData();
                        }

                    }
                });
    }


    private void initData(){


        List<MemberBean> memberBeans = DBHelper.getDaoSession().getMemberBeanDao().loadAll();
        sessiontoken=memberBeans.get(0).getSession_token();

        RetrofitUtils.getInstance(context)
                .api
                .getMattresList(sessiontoken)
                .compose(RxSchedulers.<ResultData<List<MattressBean>>>compose())
                .subscribe(new BaseObserver<List<MattressBean>>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<List<MattressBean>> resultData) {
                        if(resultData.status.equals("success")){
                            mattressBeans=resultData.data;
                            adapter.setData(mattressBeans);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });

    }



    @OnClick(R.id.btn_login)
    public void addMatress(){
        changeActivity(MattressPrepareActivity.class);
        finish();
    }

    @OnClick(R.id.close)
    void close(){
        finish();
    }


}
