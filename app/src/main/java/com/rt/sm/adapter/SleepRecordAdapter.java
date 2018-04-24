package com.rt.sm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rt.sm.R;
import com.rt.sm.bean.MemberBean;

import java.util.List;

/**
 * Created by gaodesong on 18/1/22.
 */

public class SleepRecordAdapter extends RecyclerView.Adapter<SleepRecordAdapter.MyViewHolder>{


    private List<MemberBean> memberBeans=null;

    private Context context;

    private LayoutInflater mInflater;


    public SleepRecordAdapter(List<MemberBean> memberBeans, Context context) {
        this.memberBeans = memberBeans;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==1){
            return new MyViewHolder(mInflater.inflate(R.layout.item_sleep_record_parent,parent,false));
        }else if (viewType==2){
            return new MyViewHolder(mInflater.inflate(R.layout.sleep_detail,parent,false));
        }else if(viewType==3){
            return new MyViewHolder(mInflater.inflate(R.layout.item_sleep_record_child,parent,false));
        }

        return new MyViewHolder(mInflater.inflate(R.layout.item_sleep_record_child,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        if(getItemViewType(position) != 1){
            holder.sleepLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MemberBean bean=memberBeans.get(position);
                   onItemClickListener.OnitemCliclListener(position,bean);

                }
            });
        }

    }


    @Override
    public int getItemViewType(int position) {
        return memberBeans.get(position).layoutType;
    }

    @Override
    public int getItemCount() {
        return memberBeans.size();
    }

    private MyOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(MyOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface MyOnItemClickListener{

        void OnitemCliclListener(int position,MemberBean memberBean);


    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout sleepLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            sleepLayout=itemView.findViewById(R.id.sleep_layout);


        }




    }
}
