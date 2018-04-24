package com.rt.sm.activity.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.rt.sm.R;
import com.rt.sm.activity.MainActivity;
import com.rt.sm.activity.member.AddMemberInfoActivity;
import com.rt.sm.bean.Data;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.dialog.LoadingDialog;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.BaseObserver;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.internet.RxSchedulers;
import com.rt.sm.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by haohw on 2018/1/2.
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

public class LoginActivity extends BaseActivity {

    private RxPermissions rxPermissions;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.tv_getcode)
    TextView tv_getcode;

    @BindView(R.id.iv_code)
    EditText iv_code;

    private int count=60;

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login)
    void login(){
        String phone=et_phone.getText().toString();
        String code=iv_code.getText().toString();
        if(TextUtils.isEmpty(phone)){
            ToastUtils.showShortMsg("手机号不能为空");
        }else if (TextUtils.isEmpty(code)){
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
//                                    memberBean.nowLogin=1;//表示当前登录用户
                                    DBHelper.getDaoSession().getMemberBeanDao().insert(memberBean);
//                                    changeActivity(AddMemberInfoActivity.class);
//                                    finish();
                                    //refreshSession();

                                    if(resultData.data.isFirstLogin==1){
                                        changeActivity(AddMemberInfoActivity.class);
                                    }else {
                                        changeActivity(MainActivity.class);
                                    }

                                    finish();
                                }

                            }else {
                                ToastUtils.showShortMsg(resultData.message.info);
                            }
                        }
                    });
        }

    }

    @OnClick(R.id.tv_getcode)
    void getcode(){
        String phone=et_phone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            ToastUtils.showShortMsg("手机号不能为空");
        }else {
            tv_getcode.setEnabled(false);
            RetrofitUtils
                    .getInstance(context)
                    .api
                    .sendlogincode(phone)
                    .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                    .subscribe(new BaseObserver<MemberBean>(context,true) {
                        @Override
                        public void onHandlerSuccess(ResultData<MemberBean> resultData) {

                            if(resultData.status.equals("success")){
                                handler.sendEmptyMessageDelayed(0,1000);
                            }else {
                                tv_getcode.setEnabled(true);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            tv_getcode.setEnabled(true);
                        }
                    });
        }


    }



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            --count;
            if(count>=0){
                tv_getcode.setText(count+"s");
                handler.sendEmptyMessageDelayed(0,1000);
            }else {
                tv_getcode.setText("获取验证码");
                tv_getcode.setEnabled(true);
            }

        }
    };




}
