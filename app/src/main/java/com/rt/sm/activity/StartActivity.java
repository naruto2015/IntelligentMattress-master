package com.rt.sm.activity;

import android.os.Handler;
import android.text.TextUtils;

import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
import com.rt.sm.activity.MainActivity;
import com.rt.sm.activity.mattress.MattessListActivity;
import com.rt.sm.bean.MattressBean;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.bean.ResultData;
import com.rt.sm.common.BaseActivity;
import com.rt.sm.activity.login.LoginActivity;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.BaseObserver;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.internet.RxSchedulers;
import com.rt.sm.utils.ToastUtils;
import com.rt.sm.view.CircleWaveView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends BaseActivity {


    List<MemberBean> memberBeans;

    @Override
    protected int bindLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkToken();
            }
        },2000);




    }


    private void checkToken(){

        memberBeans=DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        if(memberBeans !=null && memberBeans.size()>0){
            String session=memberBeans.get(0).getSession_token();
            if(!TextUtils.isEmpty(session)){
                RetrofitUtils.getInstance(context)
                        .api
                        .checktoken(session)
                        .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                        .subscribe(new BaseObserver<MemberBean>(context,false) {
                            @Override
                            public void onHandlerSuccess(ResultData<MemberBean> resultData) {
                                //Log.e("tag",resultData.data.mattressBean+"");
                                if(resultData.status.equals("success")){
                                    if(resultData.data.valid){
                                        changeActivity(MainActivity.class);
                                    }else {
                                        DBHelper.getDaoSession().getMemberBeanDao().deleteAll();
                                        changeActivity(LoginActivity.class);
                                    }
                                    finish();

                                }else {
                                    DBHelper.getDaoSession().getMemberBeanDao().deleteAll();
                                    changeActivity(LoginActivity.class);
                                    finish();
                                }


                            }
                        });
            }


        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    changeActivity(LoginActivity.class);
                    finish();
                }
            },2000);


        }
    }



    public void refreshSession(){

        List<MemberBean> memberBeans = DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        String session=memberBeans.get(0).getSession_token();
        RetrofitUtils.getInstance(context)
                .api
                .checktoken(session)
                .compose(RxSchedulers.<ResultData<MemberBean>>compose())
                .subscribe(new BaseObserver<MemberBean>(context,true) {
                    @Override
                    public void onHandlerSuccess(ResultData<MemberBean> resultData) {
                        if(resultData.status.equals("success")){


                        }else {
                            ToastUtils.showShortMsg(resultData.message.info);
                        }
                    }
                });
    }



    public boolean judgeSesstion(){

        boolean flag=false;

        //long now=;
        List<MemberBean> memberBeans = DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        String logintime=memberBeans.get(0).logintime;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date loginDate=sdf.parse(logintime);
            Date date=new Date();
            long distance=date.getTime()-loginDate.getTime();
            if (distance>=7*24*60*60){
                flag=false;
            }else {
                flag=true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            flag=false;
        }


        return flag;
    }


}
