package com.rt.sm.activity.mattress;

import android.os.SystemClock;
import android.util.Log;
import android.widget.EditText;

import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
import com.rt.sm.bean.Data;
import com.rt.sm.bean.MattressBean;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.BaseObserver;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.internet.RxSchedulers;
import com.rt.sm.utils.SharedPreferenceHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMattressInfoActivity extends BaseActivity {



    @BindView(R.id.mattressType)
    EditText mattressType;

    @BindView(R.id.mattressSize)
    EditText mattressSize;

    @BindView(R.id.mattressName)
    EditText mattressName;



    @Override
    protected int bindLayout() {
        return R.layout.activity_add_mattress_info;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
    }


    @OnClick(R.id.back)
    void back(){
        finish();
    }


    @OnClick(R.id.addmattress_next)
    void addmattress_next(){

        List<MemberBean> memberBeans = DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        MattressBean mattressBean=new MattressBean();
        mattressBean.model=mattressType.getText().toString();
        mattressBean.size=mattressSize.getText().toString();
        mattressBean.mattresname=mattressName.getText().toString();
        mattressBean.deviceserialno = SharedPreferenceHelper.getAddress();

        RetrofitUtils.getInstance(context)
                .api
                .addMattres(memberBeans.get(0).getSession_token(),mattressBean)
                .compose(RxSchedulers.<ResultData<MattressBean>>compose())
                .subscribe(new BaseObserver<MattressBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MattressBean> resultData) {
                        //Log.e("tag",resultData.data.mattressBean+"");
                        if(resultData.status.equals("success")){
                             changeActivity(MattessListActivity.class);
                             finish();
                        }else {

                        }


                    }
                });


    }



}
