package com.rt.sm.fragment;


import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
import com.rt.sm.activity.AboutActivity;
import com.rt.sm.activity.ModelChoiceActivity;
import com.rt.sm.activity.NotificationActivity;
import com.rt.sm.activity.login.LoginActivity;
import com.rt.sm.activity.mattress.AddMattressInfoActivity;
import com.rt.sm.activity.mattress.MattessListActivity;
import com.rt.sm.activity.mattress.MattressPrepareActivity;
import com.rt.sm.activity.member.AddMemberInfoActivity;
import com.rt.sm.activity.member.MemberListActivity;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.common.BaseFragment;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.utils.GlideCircleTransform;
import com.rt.sm.view.CircleImageView;

import org.greenrobot.eventbus.EventBus;

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

public class MineFragment extends BaseFragment {


    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.img_head)
    CircleImageView img_head;


    @Override
    public void onInvisible() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_5;
    }

    @Override
    public void initView(View view) {

        ButterKnife.bind(this,view);
        setUserInfo();

    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    private void setUserInfo(){
        List<MemberBean> memberBeans=DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        MemberBean memberBean=memberBeans.get(0);

        String user_head= RetrofitUtils.HEAD+memberBean.getSession_token()+"&imagename="+memberBean.avatar;

        Glide.with(context)
                .load(user_head)
                .centerCrop() .error(R.drawable.no_head)
                .placeholder(R.drawable.no_head)
                .transform(new GlideCircleTransform(context))
                .into(img_head);
        phone.setText(memberBean.getPhone());
        name.setText(memberBean.getUsername());
    }



    @OnClick(R.id.mattress_manager)
    public void mattressmanager(){

       changeActivity(MattessListActivity.class);

    }

    @OnClick(R.id.member_manager)
    public void membermanager(){

       changeActivity(MemberListActivity.class);

    }

    @OnClick(R.id.notification)
    public void notification(){

        changeActivity(NotificationActivity.class);

    }


    @OnClick(R.id.about)
    public void about(){

        changeActivity(AboutActivity.class);

    }


    @OnClick(R.id.model_choice)
    public void modelchoice(){

        changeActivity(ModelChoiceActivity.class);

    }


//    @OnClick(R.id.rl_ble)
//    public void bleSearch(){
//
//        changeActivity(BleSearchActivity.class);
//
//    }

    @OnClick(R.id.logout)
    public void logout(){

        DBHelper.getDaoSession().getMemberBeanDao().deleteAll();
        changeActivity(LoginActivity.class);
        getActivity().finish();

    }

    //img_head

//    @OnClick(R.id.img_head)
//    public void img_head(){
//
//        changeActivity(AddMemberInfoActivity.class);
//
//
//    }


}