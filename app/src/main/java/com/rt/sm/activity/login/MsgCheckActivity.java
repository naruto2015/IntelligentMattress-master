package com.rt.sm.activity.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.rt.sm.R;
import com.rt.sm.activity.member.AddMemberInfoActivity;
import com.rt.sm.bean.Data;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.BaseObserver;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.internet.RxSchedulers;
import com.rt.sm.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MsgCheckActivity extends BaseActivity {


    @BindView(R.id.et_code)
    EditText et_code;

    String phone;

    @Override
    protected int bindLayout() {
        return R.layout.activity_msg_check;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        initData();


    }

    private void initData() {

        Bundle bundle=getIntent().getExtras();
        phone=bundle.getString("phone");
        RetrofitUtils.getInstance(context)
                .api
                .sendlogincode(phone)
                .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                .subscribe(new BaseObserver<MemberBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MemberBean> resultData) {

                        if(resultData.status.equals("success")){


                        }

                    }
                });
    }


    @OnClick(R.id.btn_login)
    void login() {
        String code=et_code.getText().toString();
        if(TextUtils.isEmpty(code)){
            ToastUtils.showShortMsg("验证码不能为空");
        }else {

            RetrofitUtils.getInstance(context)
                    .api
                    .login(phone,code)
                    .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                    .subscribe(new BaseObserver<MemberBean>(context,true) {
                        @Override
                        public void onHandlerSuccess(ResultData<MemberBean> resultData) {
                            if(resultData.status.equals("success")){

                                MemberBean memberBean=resultData.data;
                                if (memberBean!=null) {
                                    memberBean.nowLogin=1;
                                    DBHelper.getDaoSession().getMemberBeanDao().insert(memberBean);
                                    changeActivity(AddMemberInfoActivity.class);
                                    finish();
                                }



                            }
                        }
                    });


        }


    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }






}
