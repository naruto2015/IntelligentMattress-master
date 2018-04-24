package com.rt.sm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.rt.sm.R;
import com.rt.sm.activity.PublicWebViewActivity;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.internet.RetrofitUtils;

import java.util.List;

/**
 * Created by gaodesong on 18/1/21.
 */

public class NotifyAdapter extends BaseAdapter<MemberBean>{


    private Context context;

    public NotifyAdapter(Context context, List<MemberBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context=context;

    }

    @Override
    public void onBind(final BaseHolder holder, MemberBean memberBean, int position) {

        holder.setOnClickListener(R.id.item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, PublicWebViewActivity.class);
                intent.putExtra("url", RetrofitUtils.NOTIFI_DETAIL);
                intent.putExtra("title","通知详情");
                context.startActivity(intent);

            }
        });

    }


}
