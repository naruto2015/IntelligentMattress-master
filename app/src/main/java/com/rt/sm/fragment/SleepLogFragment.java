package com.rt.sm.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rt.sm.R;
import com.rt.sm.activity.PublicWebViewActivity;
import com.rt.sm.adapter.SleepRecordAdapter;
import com.rt.sm.bean.MemberBean;
import com.rt.sm.common.BaseFragment;
import com.rt.sm.internet.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
public class SleepLogFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SleepRecordAdapter adapter=null;

    private List<MemberBean> memberBeans=null;


    @Override
    public void onInvisible() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_2;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        memberBeans=new ArrayList<>();
        for(int i=0;i<10;i++){
            MemberBean memberBean=new MemberBean();
            if(i==0){
                memberBean.layoutType=1;
            }else if (i==1){
                memberBean.layoutType=2;
            }else if(i==4){
                memberBean.layoutType=1;
            }

            memberBeans.add(memberBean);

        }

        adapter=new SleepRecordAdapter(memberBeans,context);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SleepRecordAdapter.MyOnItemClickListener() {
            @Override
            public void OnitemCliclListener(int position, MemberBean memberBean) {

                changeActivityWidthParam(PublicWebViewActivity.class, RetrofitUtils.LOG_DETAIL,"日志详情");

            }
        });

        initNetData();

    }

    /**
     * 加载数据
     */
    private void initNetData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}