package com.rt.sm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.rt.sm.R;
import com.rt.sm.activity.mattress.MattressDetailActivity;
import com.rt.sm.activity.member.MemberDetailActivity;
import com.rt.sm.bean.MattressBean;

import java.util.List;

/**
 * Created by gaodesong on 18/2/26.
 */

public class MattressAdapter extends BaseAdapter<MattressBean>{


    private Context context;

    public MattressAdapter(Context context, List<MattressBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context=context;
    }

    @Override
    public void onBind(BaseHolder holder, final MattressBean mattressBean, int position) {

        TextView mattressName=holder.getView(R.id.mattressName);
        mattressName.setText(mattressBean.mattresname);

        TextView mattressType=holder.getView(R.id.mattressType);
        mattressType.setText(mattressBean.type);

        holder.setOnClickListener(R.id.item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, MattressDetailActivity.class);
                intent.putExtra("mattresno",mattressBean.mattresno);
                intent.putExtra("deviceserialno",mattressBean.deviceserialno);
                context.startActivity(intent);

            }
        });


    }




}
