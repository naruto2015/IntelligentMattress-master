package com.rt.sm.activity.mattress;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
import com.rt.sm.adapter.MattressAdapter;
import com.rt.sm.adapter.NotifyAdapter;
import com.rt.sm.bean.Data;
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

/**
 * Created by haohw on 2018/1/7.
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

public class MattessListActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MattressAdapter adapter;

    private List<MattressBean> mattressBeans;


    @Override
    protected int bindLayout() {
        return R.layout.activity_mattess_list;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mattressBeans=new ArrayList<>();
        adapter=new MattressAdapter(context,mattressBeans,R.layout.mattress_item);
        recyclerView.setAdapter(adapter);



    }

    private void initData(){


        List<MemberBean> memberBeans = DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        RetrofitUtils.getInstance(context)
                .api
                .getMattresList(memberBeans.get(0).getSession_token())
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


    @Override
    protected void onResume() {
        super.onResume();

        initData();

    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.addMattress)
    void addMattress(){
        changeActivity(MattressPrepareActivity.class);
        finish();
    }


}
