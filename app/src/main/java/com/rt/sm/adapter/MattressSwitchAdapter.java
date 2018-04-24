package com.rt.sm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.sm.R;
import com.rt.sm.activity.mattress.MattressDetailActivity;
import com.rt.sm.bean.MattressBean;

import java.util.List;

/**
 * Created by gaodesong on 18/2/26.
 */

public class MattressSwitchAdapter extends BaseAdapter<MattressBean>{


    private Context context;

    public MattressSwitchAdapter(Context context, List<MattressBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context=context;
    }

    @Override
    public void onBind(BaseHolder holder, final MattressBean mattressBean, final int position) {

        TextView mattressInfo=holder.getView(R.id.mattressInfo);
        mattressInfo.setText(mattressBean.mattresname+" (型号:"+mattressBean.model+")");

        TextView nowMattress=holder.getView(R.id.nowMattress);
        if (mattressBean.currentchosen==1){
            nowMattress.setVisibility(View.VISIBLE);
            mattressInfo.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            nowMattress.setVisibility(View.GONE);
            mattressInfo.setTextColor(context.getResources().getColor(R.color.colorTv));
        }

        RelativeLayout switchlayout=holder.getView(R.id.switchlayout);

        switchlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemCLickListener(position);
            }
        });


    }


    public OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{

        void OnItemCLickListener(int position);

    }





}
