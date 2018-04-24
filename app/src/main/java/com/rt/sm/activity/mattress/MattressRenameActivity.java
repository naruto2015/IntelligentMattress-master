package com.rt.sm.activity.mattress;

import android.widget.TextView;

import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
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

public class MattressRenameActivity extends BaseActivity {


    @BindView(R.id.mattressName)
    TextView mattressName;

    private String deviceserialno,mattresno;

    @Override
    protected int bindLayout() {
        return R.layout.activity_mattress_rename;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        deviceserialno=getIntent().getExtras().getString("deviceserialno");
        mattresno=getIntent().getExtras().getString("mattresno");

        initData();
    }

    private void initData()
    {


    }


    @OnClick(R.id.save)
    void save(){

        List<MemberBean> memberBeans = DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        MattressBean mattressBean=new MattressBean();
        mattressBean.mattresname=mattressName.getText().toString();
        mattressBean.deviceserialno=deviceserialno;
        mattressBean.mattresno=mattresno;
        RetrofitUtils.getInstance(context)
                .api
                .updateMattres( memberBeans.get(0).getSession_token(),mattressBean)
                .compose(RxSchedulers.<ResultData<MattressBean>>compose())
                .subscribe(new BaseObserver<MattressBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MattressBean> resultData) {
                        if(resultData.status.equals("success")){
                            ToastUtils.showShortMsg("修改成功");
                            finish();
                        }else {
                            ToastUtils.showShortMsg("修改失败");
                        }
                    }
                });


    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }


}
