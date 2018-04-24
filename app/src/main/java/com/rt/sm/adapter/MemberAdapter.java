package com.rt.sm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greendao.gen.MemberBeanDao;
import com.rt.sm.R;
import com.rt.sm.activity.member.MemberDetailActivity;
import com.rt.sm.activity.member.MemberSwitchActivity;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.greendao.DBHelper;
import com.rt.sm.internet.RetrofitUtils;
import com.rt.sm.utils.GlideCircleTransform;
import com.rt.sm.view.CircleImageView;

import java.util.List;

/**
 * Created by gaodesong on 18/1/21.
 */

public class MemberAdapter extends BaseAdapter<MemberBean>{


    private Context context;

    private String householdacc;

    private String sesssionToken;

    private String phone;

    public MemberAdapter(Context context, List<MemberBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context=context;
        List<MemberBean> memberBeans= DBHelper.getDaoSession().getMemberBeanDao().loadAll();

        sesssionToken=memberBeans.get(0).getSession_token();
        phone=memberBeans.get(0).getPhone();

    }

    @Override
    public void onBind(final BaseHolder holder, final MemberBean memberBean, int position) {

        TextView uname = holder.getView(R.id.uname);
        uname.setText(memberBean.householdname);

        CircleImageView user_head=holder.getView(R.id.user_head);
        TextView tv_switch=holder.getView(R.id.tv_switch);

        String head_url= RetrofitUtils.HEAD+sesssionToken+"&imagename="+memberBean.avatar;

        Glide.with(context)
                .load(head_url)
                .centerCrop() .error(R.drawable.no_head)
                .placeholder(R.drawable.no_head)
                .transform(new GlideCircleTransform(context))
                .into(user_head);


        householdacc=memberBean.householdacc;

        if(memberBean.ismanage==1){
            tv_switch.setVisibility(View.VISIBLE);
        }else {
            tv_switch.setVisibility(View.GONE);
        }

        holder.setOnClickListener(R.id.item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MemberDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("birthday",memberBean.birthday+"");
                bundle.putString("avatar",memberBean.avatar);
                bundle.putString("bodyheight",memberBean.bodyheight+"");
                bundle.putString("bodyweight",memberBean.bodyweight+"");
                bundle.putInt("sex",memberBean.sex);
                bundle.putString("householdname",memberBean.householdname);
                bundle.putString("bloodtype",memberBean.bloodtype+"");
                bundle.putString("householdacc",memberBean.householdacc);
                bundle.putString("useracc",memberBean.useracc);
                intent.putExtras(bundle);
                //intent.putExtra("householdacc",householdacc);
                context.startActivity(intent);

            }
        });

//        holder.setOnClickListener(R.id.tv_switch, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                context.startActivity(new Intent(context, MemberSwitchActivity.class));
//
//            }
//        });
    }


}
