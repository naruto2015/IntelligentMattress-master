package com.rt.sm.activity.mattress;

import android.os.Bundle;
import android.widget.TextView;

import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
import com.rt.sm.adapter.MattressAdapter;
import com.rt.sm.bean.Data;
import com.rt.sm.bean.MattressBean;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.BaseObserver;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.internet.RxSchedulers;
import com.rt.sm.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MattressDetailActivity extends BaseActivity {


    @BindView(R.id.mattressType)
    TextView mattressType;

    @BindView(R.id.mattressSize)
    TextView mattressSize;

    @BindView(R.id.mattressName)
    TextView mattressName;


    String sesssionToken;

    private String  mattresno,deviceserialno;

    @Override
    protected int bindLayout() {
        return R.layout.activity_mattress_detail;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        initData();
    }

    private void initData()
    {

        mattresno = getIntent().getStringExtra("mattresno");
        deviceserialno = getIntent().getStringExtra("deviceserialno");
        List<MemberBean> memberBeans = DBHelper.getDaoSession().getMemberBeanDao().loadAll();
        sesssionToken=memberBeans.get(0).getSession_token();

    }


    @OnClick(R.id.mattress_manager)
    void mattressEditName(){

        Bundle bundle=new Bundle();
        bundle.putString("deviceserialno",deviceserialno);
        bundle.putString("mattresno",mattresno);
        changeActivity(MattressRenameActivity.class,bundle);

    }


    @OnClick(R.id.icon_delete)
    void icon_delete(){

        RetrofitUtils.getInstance(context)
                .api
                .deleteMattres(sesssionToken,mattresno)
                .compose(RxSchedulers.<ResultData<MattressBean>>compose())
                .subscribe(new BaseObserver<MattressBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MattressBean> resultData) {
                        if(resultData.status.equals("success")){
                            finish();
                        }else {
                            ToastUtils.showShortMsg("删除失败");
                        }
                    }
                });
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
                .getOne(sesssionToken,mattresno)
                .compose(RxSchedulers.<ResultData<MattressBean>>compose())
                .subscribe(new BaseObserver<MattressBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MattressBean> resultData) {
                        if(resultData.status.equals("success")){
                            mattressType.setText("床垫型号:"+resultData.data.model);
                            mattressSize.setText("床垫尺寸:"+resultData.data.size);
                            mattressName.setText(resultData.data.mattresname);
                        }
                    }
                });
    }
}
